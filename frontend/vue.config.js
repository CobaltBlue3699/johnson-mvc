// vue.config.js
const webpack = require('webpack')

module.exports = {
  publicPath: '/dist',
	outputDir: '../web/dist',
	productionSourceMap: false,
	// 多核cpu啟用
	parallel: require('os').cpus().length > 1,
	configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: 'jquery',
				jQuery: 'jquery'
			})
    ]
  }
}