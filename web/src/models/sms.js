import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import moment from 'moment';

const tempUrl = 'http://35.189.178.64:8080/api/';
const LOCATION = `messages/sms/send`;
const requestUrl = `${tempUrl}${LOCATION}`;

export default class Sms {
  // POST
  static send = async appointment => {
    var obj = {
        "clinic": "rakumi",
        "content": `預約時間：${moment(appointment.expectedArrivalTime).format('MM/DD HH:mm')}`,
        "phone": "+886928834194",
        }
    let requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(obj),
    };
    const result = await request(requestURL, options);
    return result;
  };
}
