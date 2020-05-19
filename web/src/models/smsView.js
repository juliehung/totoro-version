import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';

const LOCATION = 'messages/sms/views';
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class SmsView {
  static getById = async appointmentId => {
    return await requestNoParse(`${requestUrl}/${appointmentId}`)
                            .then(res => res.json())
                            .catch(() => null)
  };

  static post = async appointmentId => {
    const options = {
        method: 'POST',
      };
    return await request(`${requestUrl}/${appointmentId}`, options)
  };
}
