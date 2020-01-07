import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `esigns`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class ESign {
  // POST
  static create = async esign => {
    let requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(esign),
    };
    const result = await request(requestURL, options);
    return result;
  };
}
