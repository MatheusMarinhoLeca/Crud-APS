const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://localhost:8080', // updated target URL
    secure: false,
    logLevel: 'debug'
  },
  {
    "/qrCode": {
      "target": "https://sandbox.api.pagseguro.com",
      "secure": false,
      "changeOrigin": true,
      "pathRewrite": {
        "^/qrCode": "/orders"
      }
    }
  }
];

module.exports = PROXY_CONFIG;
