import * as StateType from './state-types'
import * as MutationType from './mutation-types'

const state = {
	[StateType.LOADING]: 0
}

const mutations = {
	[MutationType.START_LOADING] (state) {
		state[StateType.LOADING] ++
	},
	[MutationType.STOP_LOADING] (state) {
		state[StateType.LOADING] --
	}
}

const getters = {
	loading: state => state[StateType.LOADING],
	isLoading: state => state[StateType.LOADING] !== 0
}

export default {
	namespaced: true,
  state,
  getters,
	mutations
}