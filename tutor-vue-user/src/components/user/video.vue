<template>
  <div id="body">
    <div id="loginPage" class="container text-center">
      <!-- <div class="row">
        <div class="col-md-4 col-md-offset-4">
          <h2>线上视频通讯</h2>
          <label for="usernameInput" class="sr-only">Login</label>
          <input
            type="email"
            id="usernameInput"
            class="form-control formgroup"
            placeholder="Login"
            required=""
            autofocus=""
          />
          <button id="loginBtn" class="btn btn-lg btn-primary btnblock">
            Sign in
          </button>
          <button id="canvasBtn" @click="changeCanvasPage()">切换为白板</button>
        </div>
      </div> -->
    </div>

    <div id="callPage" class="call-page">
      <video id="localVideo" autoplay muted></video>
      <video id="remoteVideo" autoplay></video>
      <div class="row text-center">
        <div class="col-md-12">
          <!-- <input
            id="callToUsernameInput"
            type="text"
            placeholder="username to call"
          /> -->
          <button id="callBtn" class="btn-success btn">呼叫</button>
          <button id="hangUpBtn" class="btn-danger btn">挂断</button>
          <button id="canvasBtn" @click="changeCanvasPage()">
            切换为白板
          </button>
          <button id="display" v-if="displayBtn" @click="changeDisplayMedia()">
            屏幕共享
          </button>
          <button id="display" v-else @click="changeDeviceMedia()">
            摄像头
          </button>
        </div>
      </div>
    </div>

    <div id="canvasPage" v-show="canvasPage">
      <canvas ref="tutorial" width="1280" height="720"></canvas>
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
      stream: "",
      displayBtn: true,

      canvas: "",
      ctx: "",
      canvasBtn: "",
      canvasPage: false,
      isClickCanvas: "",
      isMoveCanvas: "",
      drawType: "",
      lineWidth: "",
      drawColor: "",
      x: "",
      y: "",
      width: "",
      height: "",
      last: "",
      imgData: "",
      index: "",
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

      var that = this;

      this.callBtn.addEventListener("click", function () {
        var callToUsername = that.$route.params.ortherName;

        if (callToUsername.length > 0) {
          that.connectedUser = callToUsername;

          // create an offer
          that.yourConn.createOffer(
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
      });

      this.hangUpBtn.addEventListener("click", function () {
        that.send({
          type: "leave",
        });
        that.handleLeave();
      });
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
      }
    },

    changeDisplayMedia() {
      this.displayBtn = false;
      const mediaStreamConstraints = {
        video: true,
        audio: true,
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
      this.stream = newStream;
      this.localVideo.srcObject = this.stream;
      this.yourConn.addStream(this.stream);
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
      this.connectedUser = null;
      this.remoteVideo.src = null;

      this.yourConn.close();
      this.yourConn.onicecandidate = null;
      this.yourConn.onaddstream = null;
      this.gotLocalMediaStream(this.stream);
    },

    changeCanvasPage() {
      console.log("use changeCanvasPage");
      this.loginPage.style.display = "none";
      this.callPage.style.display = "none";
      this.canvasPage = true;
      this.ctx = this.canvas.getContext("2d");
      this.bindMousemove = this.onmousemove.bind(this); // 解决 eventlistener 不能用 bind
      this.bindMousedown = this.onmousedown.bind(this);
      this.bindMouseup = this.onmouseup.bind(this);
    },

    // 鼠标按下
    onmousedown(e) {
      console.log("mouseDown");
      this.isClickCanvas = true; // 鼠标按下标识
      this.x = e.offsetX; // 获取鼠标按下的坐标
      this.y = e.offsetY;
      this.last = [this.x, this.y]; // 保存每次的坐标
      this.canvas.addEventListener("mousemove", this.bindMousemove); // 监听 鼠标移动事件
    },

    // 鼠标移动
    onmousemove(e) {
      this.isMoveCanvas = true; // 鼠标移动标识
      let endx = e.offsetX;
      let endy = e.offsetY;
      this.width = endx - this.x;
      this.height = endy - this.y;
      let now = [endx, endy]; // 当前移动到的坐标
      switch (this.drawType) {
        case "line":
          this.line(this.last, now, this.lineWidth, this.drawColor); // 绘制线条的方法
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
        }
      }
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

    gatherImage() {
      // 采集图像
      this.imgData = this.imgData.slice(0, this.index + 1);
      // 每次鼠标抬起时，将储存的imgdata截取至index处
      let imgData = this.ctx.getImageData(0, 0, this.width, this.height);
      this.imgData.push(imgData);
      this.index = this.imgData.length - 1; // 储存完后将 index 重置为 imgData 最后一位
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
  top: 15px;
  right: 15px;
}

#remoteVideo {
  width: 1280px;
  height: 720px;
}

#canvasPage {
  display: block;
  position: relative;
  display: block;
  margin: 0 auto;
  width: 1280px;
  height: 785px;
}

canvas {
  border: 1px solid black;
  background-color: white;
}
</style>
