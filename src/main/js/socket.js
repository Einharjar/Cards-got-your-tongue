import Vue from "vue"

//const socket = new WebSocket("wss://echo.websocket.org")
const socket = new WebSocket("ws://localhost:8080/casino/websocket")

const emitter = new Vue({
  methods:{
    send(message){
      if (1 === socket.readyState)
        socket.send(message)
        console.log(message);
    }
  }
})

socket.onmessage = function(msg){
  emitter.$emit("message", msg.data)
  console.log(msg);
}
socket.onerror = function(err){
  emitter.$emit("error", err)
}


export default emitter
