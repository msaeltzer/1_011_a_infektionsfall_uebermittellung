// ui/ ux
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

// vue 
import Vue from 'vue';
import App from './App.vue';

// routing
import VueRouter from 'vue-router';
import { routes }  from './router/index'

// state management
import store from './store';

// config
Vue.use(Antd);
Vue.use(VueRouter);
Vue.config.productionTip = false;
const router = new VueRouter({
  mode: 'history',
  routes,
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');