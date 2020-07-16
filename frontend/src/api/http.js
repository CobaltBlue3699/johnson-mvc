import axios from 'axios'; // axios
import { MsgUtils, download } from '../utils'
import store from '../store';

const httpCode = {        // http status
  400: 'Bad Request.',
  401: 'Unauthorized',
  403: 'Forbidden',
  404: 'Resource not found.',
  500: 'Server error.',
  502: 'Bad gateway'
}

const instance = axios.create({
	baseURL: 'https://127.0.0.1/',
	timeout: 10000,
	headers: {}
})

instance.defaults.headers.post['Content-Type'] = 'application/json'

// 發起request有loading動畫 (將來有特殊企業邏輯可以寫在這)
instance.interceptors.request.use(config => {
	// do the loading animation
	store.commit('loader/START_LOADING')
  return config
}, error=> {
  return Promise.reject(error)
})

/** response 預處理  **/
instance.interceptors.response.use(response => {
	if(response.headers['omni-action']) {
		switch (response.headers['omni-action']) {
			case 'download':
				download(response.data, 'test.jpg');
				break;
			default:
				break;
		}
		return;
	}

	store.commit('loader/STOP_LOADING')
  if (response.data.status === 0) {
    return Promise.resolve(response.data.data)
  } else {
		MsgUtils.showErrorMsg(`${response.data.message}(${ response.data.status})`);
    return Promise.reject(response.data.status);
  }
}, error => {
	store.commit('loader/STOP_LOADING')
  if (error.response) {     
		// 如果為開發模式則顯示錯誤提示訊息
		if(store.state.strict) {
			const tips = error.response.status in httpCode ? httpCode[error.response.status] : error.response.data.message
			MsgUtils.showErrorMsg(tips, 3000)
		}
    return Promise.reject(error)
  } else {
		MsgUtils.showErrorMsg('request timeout, please try to reflash the page.')
    return Promise.reject(new Error('timeout'))
  }
})

/**
 * 封裝axios.get請求
 * @param {String} url [請求url地址]
 * @param {Object} params [請求攜帶引數]
 */
export const get = (url, params, config = {}) => {
  return new Promise((resolve, reject) => {
    instance({
      method: 'get',
      url,
      params,
      ...config
    }).then(response => {
      resolve(response)
    }).catch(error => {
      reject(error)
    })
  })
}

/**
 * 封裝axios.post請求
 * @param {String} url [請求url地址]
 * @param {Objeect} data [請求攜帶引數]
 * @param {Objeect} config [響應資料格式]
 */
export const post = (url, data, config = {}) => {
  return new Promise((resolve, reject) => {
    instance({
      method: 'post',
      url,
      data,
      ...config
    }).then(response => {
      resolve(response)
    }).catch(error => {
      reject(error)
    })
  })
}

/**
 * 封裝axios.all併發請求
 * @param {Array} queryList [請求配置項, eg:[{method:'get', url:'api/data'}]]
 */
export const all = (queryList) => {
	const query = queryList.map((request) => {
			if (request.method === 'get') {
					return get(request.url, request.params)
			} else {
					return post(request.url, request.data, request.config)
			}
	});
	return new Promise((resolve,reject)=>{
			axios.all(query).then(accpt=>{
					resolve(accpt)
			}).catch(error=>{
					reject(error);
			})
	})
}