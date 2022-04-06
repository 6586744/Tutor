<template>
<div>
  <h1>Realtime communication with WebRTC</h1>
  <video autoplay playsinline></video>
  <br>
  <button  v-on:click="startRecord">开始录音</button>
</div>
</template>

<script>
export default {
  name: "Video",
  data() {
    return {
	mediaStreamConstraints: {
  	   video: {
	      width: {
      	      min: 1280
    	      },
    	      height: {
      	      min: 720
    	      }
	   },
	   audio: true,
     	},
	localVideo:'',
	localStream:'',
    }
  },

  methods: {
	// Handles success by adding the MediaStream to the video element.
	gotLocalMediaStream(mediaStream) {
  	  this.localStream = mediaStream;
  	  this.localVideo.srcObject = mediaStream;
	},

	// Handles error by logging a message to the console with the error message.
	handleLocalMediaStreamError(error) {
  	  console.log('navigator.getUserMedia error: ', error);
	},
	startRecord(){
	  this.localVideo = document.querySelector('video')
     	  // Initializes media stream.
	  navigator.mediaDevices.getUserMedia(this.mediaStreamConstraints)
  	  .then(this.gotLocalMediaStream).catch(this.handleLocalMediaStreamError);
  	}

	
  },
  

}
</script>

<style scoped>
  video {
	width: 50%;
  }
  button {
	margin: auto;
	margin-top: 200px;
	display: block;
  }
</style>
