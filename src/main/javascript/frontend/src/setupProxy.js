const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://147.232.205.203:8080/',
      changeOrigin: true,
    })
  );
};