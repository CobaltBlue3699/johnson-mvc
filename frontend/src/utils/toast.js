import iziToast from "../../node_modules/izitoast";

export const MsgUtils = {
	showSuccessMsg(message, timeout = false) {
		iziToast.success({
			theme: "",
			title: `[${ 'success' }]`, // i18n
			color: "green",
			timeout,
			position: 'topCenter',
			message
		});
	}, 
	showWarningMsg(message, timeout = false) {
		iziToast.warning({
			theme: "",
			title: `[${ 'warning' }]`,
			color: "yellow",
			timeout,
			position: 'topCenter',
			message
		});
	},
	showErrorMsg(message, timeout = false) {
		iziToast.error({
			theme: "",
			title: `[${ 'error' }]`,
			color: "red",
			timeout,
			position: 'topCenter',
			message
		});
	}
}
