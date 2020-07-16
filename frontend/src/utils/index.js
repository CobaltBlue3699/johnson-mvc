export { MsgUtils } from './toast.js'

/**
 * Get the first item that pass the test
 * by second argument function
 *
 * @param {Array} list
 * @param {Function} f
 * @return {*}
 */
export function find(list, f) {
	return list.filter(f)[0]
}

/**
 * Deep copy the given object considering circular structure.
 * This function caches all nested objects and its copies.
 * If it detects circular structure, use cached copy to avoid infinite loop.
 *
 * @param {*} obj
 * @param {Array<Object>} cache
 * @return {*}
 */
export function deepCopy(obj, cache = []) {
	// just return if obj is immutable value
	if (obj === null || typeof obj !== 'object') {
		return obj
	}

	// if obj is hit, it is in circular structure
	const hit = find(cache, c => c.original === obj)
	if (hit) {
		return hit.copy
	}

	const copy = Array.isArray(obj) ? [] : {}
	// put the copy into cache at first
	// because we want to refer it in recursive deepCopy
	cache.push({
		original: obj,
		copy
	})

	Object.keys(obj).forEach(key => {
		copy[key] = deepCopy(obj[key], cache)
	})

	return copy
}

/**
 * forEach for object
 */
export function forEachValue(obj, fn) {
	Object.keys(obj).forEach(key => fn(obj[key], key))
}

export function isObject(obj) {
	return obj !== null && typeof obj === 'object'
}

export function isPromise(val) {
	return val && typeof val.then === 'function'
}

export function assert(condition, msg) {
	if (!condition) throw new Error(`[vuex] ${msg}`)
}

export function partial(fn, arg) {
	return function() {
		return fn(arg)
	}
}

export function download(data, filename, mime, bom) {
	var blobData = (typeof bom !== 'undefined') ? [bom, data] : [data]
	var blob = new Blob(blobData, {type: mime || 'application/octet-stream'});
	if (typeof window.navigator.msSaveBlob !== 'undefined') {
		// IE workaround for "HTML7007: One or more blob URLs were
		// revoked by closing the blob for which they were created.
		// These URLs will no longer resolve as the data backing
		// the URL has been freed."
		window.navigator.msSaveBlob(blob, filename);
	}
	else {
		var blobURL = (window.URL && window.URL.createObjectURL) ? window.URL.createObjectURL(blob) : window.webkitURL.createObjectURL(blob);
		var tempLink = document.createElement('a');
		tempLink.style.display = 'none';
		tempLink.href = blobURL;
		tempLink.setAttribute('download', filename);

		// Safari thinks _blank anchor are pop ups. We only want to set _blank
		// target if the browser does not support the HTML5 download attribute.
		// This allows you to download files in desktop safari if pop up blocking
		// is enabled.
		if (typeof tempLink.download === 'undefined') {
				tempLink.setAttribute('target', '_blank');
		}

		document.body.appendChild(tempLink);
		tempLink.click();

		// Fixes "webkit blob resource error 1"
		setTimeout(function() {
			document.body.removeChild(tempLink);
			window.URL.revokeObjectURL(blobURL);
		}, 0)
	}
}