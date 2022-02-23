let baseUrl = "";
switch (process.env.NODE_ENV) {
  case 'dev':
    baseUrl = "http://localhost:8083/"  //开发环境url
    break
  case 'serve':
    baseUrl = "http://localhost:8083/"   //生产环境url.请求后端的url？？？
    break
}

export default baseUrl;
