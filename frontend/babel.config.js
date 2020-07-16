const presets = [
	[
		"@babel/env",
		{
			targets: {
				ie: "11",
				edge: "17",
				firefox: "60",
				chrome: "67",
				safari: "11.1"
			},
			useBuiltIns: "entry"
		}
	]
];

const plugins = [
	[
		"@babel/plugin-transform-runtime",
		{
			corejs: {
				version: 3,
				proposals: true
			},
			useESModules: true
		}
	]
];

module.exports = { presets, plugins };
