import { get } from './http'

export function getUserInfo (params, cb = () => {}) {
	return get('/user', params).then((data) => cb(data))
}

export function getAllUser (params, cb = () => {}) {
	return get('/user/all', params).then((data) => cb(data))
}

export function download () {
	// params;
	// location.href = '/download';
	return get('/download');
}
