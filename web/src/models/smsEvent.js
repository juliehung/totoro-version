import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';

const LOCATION = 'messages/sms/events';
const REMAINING_LOCATION = 'messages/sms';
const requestUrl = `${apiUrl}/${LOCATION}`;
const remaining_requestUrl = `${apiUrl}/${REMAINING_LOCATION}`;

export default class SmsEvent {
  static get = async (page, size) => {
    let total = 0;
    const data = await requestNoParse(`${requestUrl}?page=${page}&size=${size}`).then(res => {
      total = res.headers.get('x-total-count');
      return res.json();
    });
    return { data, total };
  };

  // POST/UPDATE
  static post = async event => {
    const processedEvent = {
      id: event.id,
      clinic: event.clinicName,
      title: event.title,
      status: event.status,
      metadata: {
        template: event.metadata.template,
        selectedAppointments: event.metadata.selectedAppointments?.map(a => {
          const app = { ...a, doctor: { id: a.doctor.id, user: { firstName: a.doctor.user.firstName } } };
          return app;
        }),
      },
      sms: event.sms,
    };

    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: processedEvent.id == null ? 'POST' : 'PUT',
      body: JSON.stringify(processedEvent),
    };
    return await request(requestUrl, options);
  };

  // delete
  static delete = async id => {
    const options = {
      method: 'DELETE',
    };
    const result = await requestNoParse(`${requestUrl}/${id}`, options);
    return result.status === 200;
  };

  // send
  static execute = async id => {
    const options = {
      method: 'POST',
    };
    return await requestNoParse(`${requestUrl}/${id}/execute`, options);
  };

  // get remaining
  static getRemaining = async () => {
    const options = {
      method: 'GET',
    };

    return await request(remaining_requestUrl, options);
  };
}
