import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [{
		path: '/',
		name: 'Login',
		component: () => import( /* webpackCh	unkName: "about" */ '../views/Login.vue')
	},
	{
		path: '/Menu',
		name: 'Menu',
		component: () => import( /* webpackCh	unkName: "about" */ '../views/Menu.vue'),
		children: [
		] // 存放子路由的
	},

	//  {
	//    path: '/about',
	//    name: 'About',
	//    // route level code-splitting
	//    // this generates a separate chunk (about.[hash].js) for this route
	//    // which is lazy-loaded when the route is visited.
	//    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
	//  },
	  {
	   path: '/Goods',
	   name: '准能',
	   // route level code-splitting
	   // this generates a separate chunk (about.[hash].js) for this route
	   // which is lazy-loaded when the route is visited.
	   component: () => import(/* webpackChunkName: "about" */ '../views/Goods.vue')
	 }
	// {
	//   path: '/peruser',
	//   name: 'PerUser',
	//   // route level code-splitting
	//   // this generates a separate chunk (about.[hash].js) for this route
	//   // which is lazy-loaded when the route is visited.
	//   component: () => import(/* webpackChunkName: "about" */ '../views/PerUser.vue')
	// }
	//  {
	//    path: '/Order',
	//    name: 'Order',
	//    // route level code-splitting
	//    // this generates a separate chunk (about.[hash].js) for this route
	//    // which is lazy-loaded when the route is visited.
	//    component: () => import(/* webpackChunkName: "about" */ '../views/Order.vue')
	//  },
	//  {
	//    path: '/Rolemanage',
	//    name: 'Rolemanage',
	//    // route level code-splitting
	//    // this generates a separate chunk (about.[hash].js) for this route
	//    // which is lazy-loaded when the route is visited.
	//    component: () => import(/* webpackChunkName: "about" */ '../views/Rolemanage.vue')
	//  },
	//  {
	//    path: '/Plan',
	//    name: 'Plan',
	//    // route level code-splitting
	//    // this generates a separate chunk (about.[hash].js) for this route
	//    // which is lazy-loaded when the route is visited.
	//    component: () => import(/* webpackChunkName: "about" */ '../views/Plan.vue')
	//  },
	//  {
	//    path: '/Menu',
	//    name: 'Menu',
	//    component: () => import('../views/Menu.vue')
	//  },
	//  {
	//    path: '/Login',
	//    name: 'Login',
	//    component: () => import('../views/Login.vue')
	// }
]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
