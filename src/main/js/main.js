
import App from './app.vue'
import router from './_helpers/router.js'
import Vue from 'vue'
import VueRouter from 'vue-router'
import VeeValidate from 'vee-validate';
import Vuex from 'vuex'
import {store} from './_store/index.js';
import Socket from "./socket"

Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(VeeValidate);


//  Socket.send("wot ze fuk")
//let count = 0
//
//setInterval(() => {
//  Socket.send(`Message Number ${++count}`)
//}, 1000)


new Vue({
    el: '#app',
    render: h => h(App),
    router,
    store
})
