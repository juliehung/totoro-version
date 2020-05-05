import { mqttUrl } from './apiUrl';
import mqtt from 'mqtt';

const appointmentCallbacks = {};
const patientCallbacks = {};
const messageCallbacks = {};

class MqttHelper {
  constructor() {
    const clientId = 'totoro-web' + Math.random().toString(16).substr(2, 8);
    const options = {
      keepalive: 10,
      clientId: clientId,
      protocolId: 'MQTT',
      protocolVersion: 4,
      clean: true,
      reconnectPeriod: 1000,
      connectTimeout: 30 * 1000,
      will: {
        topic: 'WillMsg',
        payload: 'Connection Closed abnormally..!',
        qos: 0,
        retain: false,
      },
    };

    this.client = mqtt.connect(mqttUrl, options);
    this.client.on('error', err => {
      console.log(err);
      this.client.end();
    });

    this.client.on('connect', () => {
      console.log('client connected:' + clientId);
    });

    this.client.on('close', () => {
      console.log(clientId + ' disconnected');
    });

    this.client.subscribe('totoro/appointment', { qos: 0 });
    this.client.subscribe('totoro/patient', { qos: 0 });
    this.client.subscribe('totoro/message', { qos: 0 });

    // This is a demo of receiving message
    // eslint-disable-next-line no-unused-vars
    this.client.on('message', (topic, message, packet) => {
      const payload = message.toString();

      console.log('Received Message:= ' + payload + '\nOn topic:= ' + topic);
      if (topic === 'totoro/appointment') {
        const keys = Object.keys(appointmentCallbacks);
        for (let i = 0; i < keys.length; i++) {
          appointmentCallbacks[keys[i]](payload);
        }
      } else if (topic === 'totoro/patient') {
        const keys = Object.keys(patientCallbacks);
        for (let i = 0; i < keys.length; i++) {
          patientCallbacks[keys[i]](payload);
        }
      } else if (topic === 'totoro/message') {
        const keys = Object.keys(messageCallbacks);
        for (let i = 0; i < keys.length; i++) {
          messageCallbacks[keys[i]](payload);
        }
      }
    });
  }

  subscribeAppointment = (name, callback) => {
    appointmentCallbacks[name] = callback;
  };

  unsubscribeAppointment = name => {
    delete appointmentCallbacks[name];
  };

  subscribePatient = (name, callback) => {
    patientCallbacks[name] = callback;
  };

  unsubscribePatient = name => {
    delete patientCallbacks[name];
  };

  subscribeMessage = (name, callback) => {
    messageCallbacks[name] = callback;
  };

  unsubscribeMessage = name => {
    delete messageCallbacks[name];
  };

  // Publish a message back to server and then go to other clients
  // This is a broadcast message
  publish = (topic, message) => {
    this.client.publish(topic, message, { qos: 0, retain: false });
  };
}

const MqttHelperInstance = new MqttHelper();
Object.freeze(MqttHelperInstance);

export default MqttHelperInstance;
