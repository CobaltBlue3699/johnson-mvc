import 'core-js/stable'
import 'regenerator-runtime/runtime'
import 'current-script-polyfill';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import 'font-awesome/css/font-awesome.min.css'
import 'izitoast/dist/css/iziToast.min.css'
import Vue from 'vue';
import App from './App.vue';
import store from './store';

const debug = process.env.NODE_ENV !== 'production';
Vue.config.productionTip = Vue.config.devTools = Vue.config.performance = debug;

export const app = new Vue({
	store,
  render: h => h(App)
}).$mount('#app')
