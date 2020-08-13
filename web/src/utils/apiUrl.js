let host = 'localhost';

let apiProtocol = 'http';
let apiPort = 8082;
const apiPrefix = '/api';

let mqttProtocol = 'ws';
let mqttPort = 8081;
const mqttPrefix = '/mqtt';

let clinic = '';

if (window) {
  const currentLocation = window.location;
  host = currentLocation.hostname;
  apiPort = currentLocation.port;

  clinic = currentLocation.pathname.split('/')[1];
  // SaaS
  if (clinic !== '') {
    clinic = '/' + clinic;
    apiProtocol = 'https';
    mqttProtocol = 'wss';
    mqttPort = apiPort; // 443
  }
}

if (process.env.NODE_ENV !== 'production') {
  // Use dev.dentall.site
  // host = 'dev.dentall.site';
  // apiPort = 8082;

  // Use dentall.pw/rakumi
  clinic = '/cp';
  host = 'dentall.pw';
  apiPort = 443;
  apiProtocol = 'https';
  mqttProtocol = 'wss';
  mqttPort = apiPort;
}

export const mqttUrl = `${mqttProtocol}://${host}:${mqttPort}${clinic}${mqttPrefix}`;
const apiUrl = `${apiProtocol}://${host}:${apiPort}${clinic}${apiPrefix}`;

export default apiUrl;
