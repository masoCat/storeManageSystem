import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import axios from "axios";

axios.defaults.baseURL = 'http://localhost:8001';

Vue.prototype.$axios = axios
Vue.config.productionTip = false
// Vue.use(ElementUI);
Vue.use(ElementUI, {size: 'small'});
new Vue({
    render: h => h(App),
}).$mount('#app')
