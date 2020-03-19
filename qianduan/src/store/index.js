import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
	  
	  user:{username:'admin'}
	  
  },
  mutations: {
		login(state,obj){
			//console.log("+++++++++++++++"+state.user.username)
			
			state.user = obj;
			console.log("++++++++++++++++++++"+state.user.username)
			console.log("++++++++"+state.user)
		}
  },
  actions: {
  },
  modules: {
  }
})
