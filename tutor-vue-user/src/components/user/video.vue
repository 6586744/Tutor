<template>
  <div id="body">
    <div id="loginPage" class="container text-center">
      <div class="row">
        <div class="col-md-4 col-md-offset-4">
          <h2>线上视频通讯</h2>
          <label for="usernameInput" class="sr-only">Login</label>
          <input
            type="email"
            id="usernameInput"
            c
            lass="form-control formgroup"
            placeholder="Login"
            required=""
            autofocus=""
          />
          <button id="loginBtn" class="btn btn-lg btn-primary btnblock">
            Sign in
          </button>
        </div>
      </div>
    </div>

    <div id="callPage" class="call-page">
      <video id="localVideo" autoplay muted></video>
      <video id="remoteVideo" autoplay></video>
      <div class="row text-center">
        <div class="col-md-12">
          <input
            id="callToUsernameInput"
            type="text"
            placeholder="username to call"
          />
          <button id="callBtn" class="btn-success btn">Call</button>
          <button id="hangUpBtn" class="btn-danger btn">Hang Up</button>
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
      stream: "",
    };
  },

  mounted() {
    this.initWebSocket();
    this.init();
  },

  methods: {
    initWebSocket() {
      this.conn = new WebSocket("ws://192.168.0.110:9090");
      this.conn.onopen = function () {
        console.log("Connected to the signaling server");
      };

      var that = this;

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
      this.usernameInput = document.querySelector("#usernameInput");
      this.loginBtn = document.querySelector("#loginBtn");

      this.callPage = document.querySelector("#callPage");
      this.callToUsernameInput = document.querySelector("#callToUsernameInput");
      this.callBtn = document.querySelector("#callBtn");

      this.hangUpBtn = document.querySelector("#hangUpBtn");

      this.localVideo = document.querySelector("#localVideo");
      this.remoteVideo = document.querySelector("#remoteVideo");

      this.callPage.style.display = "none";

      var that = this;

      this.loginBtn.addEventListener("click", function (event) {
        this.name = usernameInput.value;

        if (this.name.length > 0) {
          that.send({
            type: "login",
            name: this.name,
          });
        }
      });

      this.callBtn.addEventListener("click", function () {
        var callToUsername = callToUsernameInput.value;

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

    handleLogin(success) {
      if (success === false) {
        alert("Ooops...try a different username");
      } else {
        this.loginPage.style.display = "none";
        this.callPage.style.display = "block";

        //getting local video stream
        navigator.mediaDevices.getUserMedia({ video: true, audio: true })
          .then(this.gotLocalMediaStream).catch(this.handleLocalMediaStreamError);
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
      this.yourConn = new webkitRTCPeerConnection(configuration);

      // setup stream listening
      this.yourConn.addStream(this.stream);

      var that = this;

      //when a remote user adds stream to the peer connection, we display it
      this.yourConn.onaddstream = function (e) {
        that.remoteVideo.srcObject = e.stream;
      };

      // Setup ice handling
      this.yourConn.onicecandidate = function (event) {
        console.log("get IceCandidate")
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
  },
};
</script>

<style scoped>
#body {
  background: #eee;
  padding: 5% 0;
}
video {
  background: black;
  border: 1px solid gray;
}

.call-page {
  position: relative;
  display: block;
  margin: 0 auto;
  width: 500px;
  height: 500px;
}

#localVideo {
  width: 150px;
  height: 150px;
  position: absolute;
  top: 15px;
  right: 15px;
}

#remoteVideo {
  width: 500px;
  height: 500px;
}
</style>
