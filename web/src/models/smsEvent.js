import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';

const LOCATION = 'messages/sms/events';
const REMAINING_LOCATION = 'messages/sms';
const requestUrl = `${apiUrl}/${LOCATION}`;
const remaining_requestUrl = `${apiUrl}/${REMAINING_LOCATION}`;

export default class SmsEvent {
  static get = async () => {
    return await request(requestUrl)
  }

  // POST/UPDATE
  static post = async event => {
    const processedEvent = {
      id: event.id,
      clinic: event.clinicName,
      title: event.title,
      metadata: {
        template: event.metadata.template,
        selectedAppointments: event.metadata.selectedAppointments
      },
      sms: event.sms.map((mes, i) => {
        var newM = {
          phone: mes.phone,
          content: mes.content,
          metadata: {
            patientId: mes.metadata.patientId,
            patientName: mes.metadata.patientName,
            appointmentDate: event.metadata.selectedAppointments[i].expectedArrivalTime
          },
        }

        return newM
      })
    }

    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: processedEvent.id == null ? 'POST' : 'PUT',
      body: JSON.stringify(processedEvent),
    };
    const result = await request(requestUrl, options);
    return result;
  };

  // delete
  static delete = async id => {
    const options = {
      method: 'DELETE'
    };
    const result = await requestNoParse(`${requestUrl}/${id}`, options);
    return result.status === 200;
  };

  // send
  static execute = async id => {
    const options = {
      method: 'POST'
    };
    const result = await requestNoParse(`${requestUrl}/${id}/execute`, options);
    return result;
  };

  // get remaining
  static getRemaining = async id => {
    const options = {
      method: 'GET'
    };

    const result = await request(remaining_requestUrl, options);
    return result
  };
}
