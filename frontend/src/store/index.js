import Vue from 'vue';
import Vuex from 'vuex';
import createLogger from '../pulgins/logger'
import loader from './loader'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

// root state object.
// each Vuex instance is just a single state tree.
const state = {
	strict: debug,
}
// mutations are operations that actually mutates the state.
// each mutation handler gets the entire state tree as the
// first argument, followed by additional payload arguments.
// mutations must be synchronous and can be recorded by plugins
// for debugging purposes.
const mutations = {

}

// actions are functions that cause side effects and can involve
// asynchronous operations.
const actions = {

}

// getters are functions
const getters = {
  
}

// A Vuex instance is created by combining the state, mutations, actions,
// and getters.
export default new Vuex.Store({
  state,
  getters,
  actions,
	mutations,
	modules: {
		loader
	},
	strict: debug,
	plugins: debug ? [createLogger()] : []
})