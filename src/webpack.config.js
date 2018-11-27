const VueLoaderPlugin = require('vue-loader/lib/plugin')


module.exports = {
    entry: './main/js/main.js',
    mode: 'development',
    module: {
        rules: [{
            loader: 'babel-loader',
            query: {
                presets: ['@babel/preset-env']
            },
            test: /\.js$/
        }, {
            loader: 'vue-loader',
            test: /\.vue$/
        }]
    },
    output: {
        filename: 'bundle.js',
        path: __dirname
    },
    plugins: [
        new VueLoaderPlugin()
    ],
    devServer: {
        historyApiFallback: true
    },

    /*global config object for the application using the externals property,
    use this to define different config variables for your development and production environments.*/
    externals: {
        config: JSON.stringify({
            apiUrl: 'http://localhost:4000'
        })
    }

}