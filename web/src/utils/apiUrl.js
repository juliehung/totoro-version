let host = 'localhost';

const apiProtocol = 'http';
let apiPort = 8082;
const apiPrefix = '/api';

const mqttProtocol = 'ws';
const mqttPort = 8081;
const mqttPrefix = '/mqtt';

if (window) {
  const currentLocation = window.location;
  host = currentLocation.hostname;
  apiPort = currentLocation.port;
}

if (process.env.NODE_ENV !== 'production') {
  host = 'dev.dentall.site';
  apiPort = 8084;
  // host = 'dev.dentall.site';
  // apiPort = 8082;
}

export const mqttUrl = `${mqttProtocol}://${host}:${mqttPort}${mqttPrefix}`;
const apiUrl = `${apiProtocol}://${host}:${apiPort}${apiPrefix}`;

export default apiUrl;
