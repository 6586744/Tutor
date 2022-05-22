<template>
  <div id="body">
    <div id="loginPage" class="container text-center">

    </div>

    <div id="callPage" class="call-page">
      <el-button @click="backHistory()" style="float: right" icon="el-icon-back"
        >返回</el-button
      >
      <video id="localVideo" autoplay></video>
      <video id="remoteVideo" autoplay></video>
      <div class="row text-center">
        <div class="col-md-12">
          <!-- <input
            id="callToUsernameInput"
            type="text"
            placeholder="username to call"
          /> -->
          <el-button
            id="callBtn"
            v-if="isCall == false"
            class="btn-success btn"
            @click="call()"
          >
            呼叫
          </el-button>
          <el-button
            id="hangUpBtn"
            v-if="isCall"
            class="btn-danger btn"
            @click="hangUp()"
          >
            挂断
          </el-button>
          <el-button id="display" v-if="displayBtn" @click="changeDisplayMedia()">
            屏幕共享
          </el-button>
          <el-button id="camera" v-else @click="changeDeviceMedia()">
            摄像头
          </el-button>
          <el-button id="canvasBtn" v-if="isCall" @click="changeCanvasPage()">
            切换为白板
          </el-button>
        </div>
      </div>
    </div>

    <div id="canvasPage" v-show="canvasPage">
      <canvas ref="tutorial" width="1280" height="720"></canvas>
      <div class="row text-center">
        <div class="col-md-12">
          <el-button id="lineBtn" @click="useLine()">画笔</el-button>
          <el-button id="eraserBtn" @click="useEraser()">擦头</el-button>
          <el-button id="cancelBtn" @click="cancel()">撤回</el-button>
          <el-button id="goBtn" @click="go()">前进</el-button>
          <el-button id="clearBtn" @click="clearAll()">清屏</el-button>
          <el-button id="media" @click="changeMedia()">切回视频画面</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Video",
  data() {
    return {
      name: "",
      connectedUser: "",
      conn: "",
      loginPage: "",
      usernameInput: "",
      loginBtn: "",
      callPage: "",
      callToUsernameInput: "",
      callBtn: "",
      hangUpBtn: "",
      localVideo: "",
      remoteVideo: "",
      yourConn: "",
      sendChannel: "",
      stream: "",
      displayBtn: true,
      isCall: false,

      canvas: "",
      ctx: "",
      canvasBtn: "",
      canvasPage: false,
      isClickCanvas: "",
      isMoveCanvas: "",
      drawType: "line",
      lineWidth: "4",
      drawColor: "black",
      x: "",
      y: "",
      width: "",
      height: "",
      last: "",
      ortherLast: "",
      originImg: "",
      imgData: [],
      index: 0,
      bindMousedown: "",
      bindMousemove: "",
      bindMouseup: "",
    };
  },

  mounted() {
    this.initWebSocket();
    this.init();
  },

  methods: {
    initWebSocket() {
      var that = this;
      this.conn = new WebSocket("ws://192.168.0.110:9090");
      this.conn.onopen = function () {
        console.log("Connected to the signaling server");
        that.login();
      };

      this.conn.onmessage = function (msg) {
        console.log("Got message", msg.data);

        var data = JSON.parse(msg.data);

        switch (data.type) {
          case "login":
            that.handleLogin(data.success);
            break;
          //when somebody wants to call us
          case "offer":
            that.handleOffer(data.offer, data.name);
            break;
          case "answer":
            that.handleAnswer(data.answer);
            break;
          //when a remote peer sends an ice candidate to us
          case "candidate":
            that.handleCandidate(data.candidate);
            break;
          case "leave":
            that.handleLeave();
            break;
          case "wait":
            that.handleWait(data.success);
            break;
          default:
            break;
        }
      };
      this.conn.onerror = function (err) {
        console.log("Got error", err);
      };
    },

    init() {
      this.loginPage = document.querySelector("#loginPage");
      // this.usernameInput = document.querySelector("#usernameInput");
      // this.loginBtn = document.querySelector("#loginBtn");

      this.callPage = document.querySelector("#callPage");
      // this.callToUsernameInput = document.querySelector("#callToUsernameInput");
      this.callBtn = document.querySelector("#callBtn");

      this.hangUpBtn = document.querySelector("#hangUpBtn");

      this.localVideo = document.querySelector("#localVideo");
      this.remoteVideo = document.querySelector("#remoteVideo");

      this.callPage.style.display = "none";

      this.canvas = this.$refs.tutorial;
    },

    call() {
      var callToUsername = this.$route.params.ortherName;
      this.send({
        type: "connect",
        name: callToUsername,
      });
    },

    connect() {
      var callToUsername = this.$route.params.ortherName;
      this.sendChannel = this.yourConn.createDataChannel("sendDataChannel");
      this.sendChannel.onmessage = this.receiveData;
      this.isCall = true;

      if (callToUsername.length > 0) {
        this.connectedUser = callToUsername;
        var that = this;
        // create an offer
        this.yourConn.createOffer(
          function (offer) {
            that.send({
              type: "offer",
              offer: offer,
            });

            that.yourConn.setLocalDescription(offer);
          },
          function (error) {
            alert("Error when creating an offer");
          }
        );
      }
    },

    handleWait(success) {
      if (success == false) {
        this.$message("对方还未进入到线上教学！");
      } else {
        this.connect();
      }
    },

    hangUp() {
      this.send({
        type: "leave",
      });
      this.handleLeave();
    },

    backHistory() {
      var role = this.$store.state.user.u_name.u_role;
      if (role == "1") {
        this.$router.push({
          path: "/userOrder",
        });
      } else if (role == "2") {
        this.$router.push({
          path: "/teacherOrder",
        });
      }
      location.reload();
    },

    send(message) {
      if (this.connectedUser) {
        message.name = this.connectedUser;
      }
      this.conn.send(JSON.stringify(message));
    },

    login() {
      this.name = this.$route.params.name;

      if (this.name.length > 0) {
        this.send({
          type: "login",
          name: this.name,
        });
      }
    },

    handleLogin(success) {
      if (success === false) {
        alert("Ooops...try a different username");
      } else {
        this.loginPage.style.display = "none";
        this.callPage.style.display = "block";

        const mediaStreamConstraints = {
          video: {
            width: {
              min: 1280,
            },
            height: {
              min: 720,
            },
          },
          audio: true,
        };
        navigator.mediaDevices
          .getUserMedia(mediaStreamConstraints)
          .then(this.gotLocalMediaStream)
          .catch(this.handleLocalMediaStreamError);

        this.isFirstCanvas = false;
        this.ctx = this.canvas.getContext("2d");
        this.bindMousemove = this.onmousemove.bind(this);
        this.bindMousedown = this.onmousedown.bind(this);
        this.bindMouseup = this.onmouseup.bind(this);
        this.canvas.addEventListener("mousedown", this.bindMousedown);
        this.canvas.addEventListener("mouseup", this.bindMouseup);
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.originImg = this.ctx.getImageData(0, 0, this.width, this.height);
        this.gatherImage();
      }
    },

    changeDisplayMedia() {
      this.displayBtn = false;
      const mediaStreamConstraints = {
        video: {
          cursor: "always"
        },
        audio: true
      };

      navigator.mediaDevices
        .getDisplayMedia(mediaStreamConstraints)
        .then(this.refreshStream)
        .catch(this.handleLocalMediaStreamError);
    },

    changeDeviceMedia() {
      this.displayBtn = true;
      const mediaStreamConstraints = {
        video: {
          width: {
            min: 1280,
          },
          height: {
            min: 720,
          },
        },
        audio: true,
      };
      navigator.mediaDevices
        .getUserMedia(mediaStreamConstraints)
        .then(this.refreshStream)
        .catch(this.handleLocalMediaStreamError);
    },

    refreshStream(newStream) {
      var that = this;
      this.yourConn.removeStream(this.stream);
      this.stream = newStream;
      this.localVideo.srcObject = this.stream;
      this.yourConn.addStream(this.stream);
      if (this.isCall == true) {
        this.yourConn.createOffer(
          function (offer) {
            that.send({
              type: "offer",
              offer: offer,
            });

            that.yourConn.setLocalDescription(offer);
          },
          function (error) {
            alert("Error when creating an offer");
          }
        );
      }
    },

    gotLocalMediaStream(myStream) {
      this.stream = myStream;

      //displaying local video stream on the page
      this.localVideo.srcObject = this.stream;

      //using Google public stun server
      var configuration = {
        iceServers: [{ url: "stun:stun2.1.google.com:19302" }],
      };

      //Starting a peer connection
      this.yourConn = new RTCPeerConnection(configuration);

      // setup stream listening
      this.yourConn.addStream(this.stream);

      var that = this;

      //when a remote user adds stream to the peer connection, we display it
      this.yourConn.onaddstream = function (e) {
        that.remoteVideo.srcObject = e.stream;
      };

      this.yourConn.ondatachannel = function (e) {
        that.sendChannel = e.channel;
        that.sendChannel.onmessage = that.receiveData;
      };

      // Setup ice handling
      this.yourConn.onicecandidate = function (event) {
        console.log("get IceCandidate");
        if (event.candidate) {
          that.send({
            type: "candidate",
            candidate: event.candidate,
          });
        }
      };
    },

    handleLocalMediaStreamError(error) {
      console.log(error);
    },

    handleOffer(offer, name) {
      this.isCall = true;
      this.connectedUser = name;
      this.yourConn.setRemoteDescription(new RTCSessionDescription(offer));

      var that = this;

      //create an answer to an offer
      this.yourConn.createAnswer(
        function (answer) {
          that.yourConn.setLocalDescription(answer);

          that.send({
            type: "answer",
            answer: answer,
          });
        },
        function (error) {
          alert("Error when creating an answer");
        }
      );
    },

    handleAnswer(answer) {
      this.yourConn.setRemoteDescription(new RTCSessionDescription(answer));
    },

    handleCandidate(candidate) {
      this.yourConn.addIceCandidate(new RTCIceCandidate(candidate));
    },

    handleLeave() {
      this.isCall = false;
      this.clearAll();
      this.connectedUser = null;
      this.remoteVideo.src = null;

      this.sendChannel.close();
      this.yourConn.close();
      this.yourConn.onicecandidate = null;
      this.yourConn.onaddstream = null;
      this.yourConn.ondatachannel = null;
      this.gotLocalMediaStream(this.stream);
    },

    changeCanvasPage() {
      this.loginPage.style.display = "none";
      this.callPage.style.display = "none";
      this.canvasPage = true;
    },

    changeMedia() {
      this.canvasPage = false;
      this.callPage.style.display = "block";
    },

    sendData(data) {
      this.sendChannel.send(JSON.stringify(data));
    },

    receiveData(event) {
      console.log(event.data);
      var data = JSON.parse(event.data);
      switch (data.drawType) {
        case "line":
          this.ortherLine(
            this.ortherLast,
            data.now,
            data.lineWidth,
            data.drawColor
          ); // 绘制线条的方法
          break;
        case "eraser":
          this.eraser(
            data.endx,
            data.endy,
            data.width,
            data.height,
            data.lineWidth
          );
          break;
        case "clear":
          this.handleClear();
          break;
        case "first":
          this.ortherLast = data.last;
          break;
        case "last":
          this.index = 0;
          this.imgData = [];
          this.gatherImage();
          break;
        case "cancel":
          this.sendChannel.onmessage = this.receiveImgData();
          break;
        default:
          break;
      }
    },

    // 鼠标按下
    onmousedown(e) {
      console.log("mouseDown");
      this.isClickCanvas = true; // 鼠标按下标识
      this.x = e.offsetX; // 获取鼠标按下的坐标
      this.y = e.offsetY;
      this.last = [this.x, this.y]; // 保存每次的坐标
      this.canvas.addEventListener("mousemove", this.bindMousemove); // 监听 鼠标移动事件
      this.sendData({
        drawType: "first",
        last: this.last,
      });
    },

    // 鼠标移动
    onmousemove(e) {
      this.isMoveCanvas = true; // 鼠标移动标识
      let endx = e.offsetX;
      let endy = e.offsetY;
      let width = endx - this.x;
      let height = endy - this.y;
      let now = [endx, endy]; // 当前移动到的坐标
      switch (this.drawType) {
        case "line":
          this.line(this.last, now, this.lineWidth, this.drawColor); // 绘制线条的方法
          this.sendData({
            drawType: this.drawType,
            now: now,
            lineWidth: this.lineWidth,
            drawColor: this.drawColor,
          });
          break;
        case "eraser":
          this.eraser(endx, endy, this.width, this.height, this.lineWidth);
          this.sendData({
            drawType: "eraser",
            endx: endx,
            endy: endy,
            width: this.width,
            height: this.height,
            lineWidth: this.lineWidth,
          });
          break;
      }
    },

    // 鼠标抬起
    onmouseup() {
      if (this.isClickCanvas) {
        this.isClickCanvas = false;
        this.canvas.removeEventListener("mousemove", this.bindMousemove); // 移除鼠标移动事件
        if (this.isMoveCanvas) {
          // 鼠标没有移动不保存
          this.isMoveCanvas = false;
          this.gatherImage(); // 保存每次的图像
          this.sendData({
            drawType: "last",
          });
        }
      }
    },

    useLine() {
      this.drawType = "line";
    },

    useEraser() {
      this.drawType = "eraser";
    },

    line(last, now, lineWidth, drawColor) {
      // 绘制线性
      this.ctx.beginPath();
      this.ctx.lineCap = "round"; // 设定线条与线条间接合处的样式
      this.ctx.lineJoin = "round";
      this.ctx.lineWidth = lineWidth;
      this.ctx.strokeStyle = drawColor;
      this.ctx.moveTo(last[0], last[1]);
      this.ctx.lineTo(now[0], now[1]);
      this.ctx.closePath();
      this.ctx.stroke(); // 进行绘制
      this.last = now; // 更新上次的坐标
    },

    ortherLine(last, now, lineWidth, drawColor) {
      // 绘制线性
      this.ctx.beginPath();
      this.ctx.lineCap = "round"; // 设定线条与线条间接合处的样式
      this.ctx.lineJoin = "round";
      this.ctx.lineWidth = lineWidth;
      this.ctx.strokeStyle = drawColor;
      this.ctx.moveTo(last[0], last[1]);
      this.ctx.lineTo(now[0], now[1]);
      this.ctx.closePath();
      this.ctx.stroke(); // 进行绘制
      this.ortherLast = now; // 更新上次的坐标
    },

    gatherImage() {
      // 采集图像
      this.imgData = this.imgData.slice(0, this.index + 1);
      // 每次鼠标抬起时，将储存的imgdata截取至index处
      let imgData = this.ctx.getImageData(0, 0, this.width, this.height);
      this.imgData.push(imgData);
      this.index = this.imgData.length - 1; // 储存完后将 index 重置为 imgData 最后一位
    },

    cancel() {
      // 撤回
      if (--this.index < 0) {
        // 最多重置到 0 位
        this.index = 0;
        return;
      }
      this.ctx.putImageData(this.imgData[this.index], 0, 0); // 绘制
      this.sendImage();
    },

    go() {
      // 前进
      if (++this.index > this.imgData.length - 1) {
        // 最多前进到 length -1
        this.index = this.imgData.length - 1;
        return;
      }
      this.ctx.putImageData(this.imgData[this.index], 0, 0);
      this.sendImage();
    },

    eraser(endx, endy, width, height, lineWidth) {
      // 橡皮擦
      this.ctx.save(); // 缓存裁切前的
      this.ctx.beginPath();
      this.ctx.arc(endx, endy, lineWidth * 2, 0, 2 * Math.PI);
      this.ctx.closePath();
      this.ctx.clip(); // 裁切
      this.ctx.clearRect(0, 0, width, height);
      this.ctx.restore(); // 还原
    },

    clearAll() {
      this.sendData({
        drawType: "clear",
      });
      this.handleClear();
    },

    handleClear() {
      this.ctx.putImageData(this.originImg, 0, 0); // 绘制
      this.index = 0;
      this.imgData = [];
      this.gatherImage();
    },

    sendImage() {
      // Split data channel message in chunks of this byte length.
      var CHUNK_LEN = 64000;
      var img = this.imgData[this.index],
        len = img.data.byteLength,
        n = (len / CHUNK_LEN) | 0;

      console.log("Sending a total of " + len + " byte(s)");

      if (!this.sendChannel) {
        logError(
          "Connection has not been initiated. " +
            "Get two peers in the same room first"
        );
        return;
      } else if (this.sendChannel.readyState === "closed") {
        logError("Connection was lost. Peer closed the connection.");
        return;
      }

      this.sendData({
        drawType: "cancel",
      });
      this.sendChannel.send(len);

      for (var i = 0; i < n; i++) {
        var start = i * CHUNK_LEN,
          end = (i + 1) * CHUNK_LEN;
        console.log(start + " - " + (end - 1));
        this.sendChannel.send(img.data.subarray(start, end));
      }

      if (len % CHUNK_LEN) {
        console.log("last " + (len % CHUNK_LEN) + " byte(s)");
        this.sendChannel.send(img.data.subarray(n * CHUNK_LEN));
      }
    },

    receiveImgData() {
      var buf, count;
      var that = this;

      return function onmessage(event) {
        if (typeof event.data === "string") {
          buf = window.buf = new Uint8ClampedArray(parseInt(event.data));
          count = 0;
          console.log("Expecting a total of " + buf.byteLength + " bytes");
          return;
        }

        var data = new Uint8ClampedArray(event.data);
        buf.set(data, count);

        count += data.byteLength;
        console.log("count: " + count);

        if (count === buf.byteLength) {
          // we're done: all data chunks have been received
          console.log("Done. Rendering photo.");
          that.renderImg(buf);
        }
      };
    },

    renderImg(data) {
      var img = this.ctx.createImageData(this.width, this.height);
      img.data.set(data);
      this.ctx.putImageData(img, 0, 0);
      this.gatherImage();
      this.sendChannel.onmessage = this.receiveData;
    },
  },
};
</script>

<style scoped>
#body {
  background: #eee;
  padding: 3% 0;
}
video {
  background: black;
  border: 1px solid gray;
}

.call-page {
  position: relative;
  display: block;
  margin: 0 auto;
  width: 1280px;
  height: 785px;
}

#localVideo {
  width: 320px;
  height: 180px;
  position: absolute;
  top: 55px;
  right: 15px;
}

#remoteVideo {
  width: 1280px;
  height: 720px;
}

#canvasPage {
  display: block;
  position: relative;
  margin: 0 auto;
  width: 1280px;
  height: 785px;
}

canvas {
  border: 1px solid black;
  background-color: white;
}
</style>
