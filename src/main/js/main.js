
import App from './app.vue'
import router from './_helpers/router.js'
import Vue from 'vue'
import VueRouter from 'vue-router'
import VeeValidate from 'vee-validate';
import Vuex from 'vuex'
import {store} from './_store/index.js';

Vue.use(VueRouter)
Vue.use(Vuex)
Vue.use(VeeValidate);


/*import { configureFakeBackend } from './_helpers/fake-backend.ts';
configureFakeBackend();
*/

new Vue({
    el: '#app',
    render: h => h(App),
    router,
    store
})
