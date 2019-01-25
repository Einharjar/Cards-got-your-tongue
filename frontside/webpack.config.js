


module.exports = {
    entry: './main.jsx',
    mode: 'development',
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        loader: 'babel-loader',
		query: {
			presets: ['@babel/env', '@babel/react']
		}
      },
      {
        test: /\.css$/,
        exclude: /node_modules/,
        loader: 'css-loader',
      },
    ]
  },
  resolve: {
    extensions: ['*', '.js', '.jsx', '.css']
  },
    output: {
        filename: 'bundle.js',
        path: __dirname
    },
    plugins: [
    ],
    devServer: {
        historyApiFallback: true
    }

}