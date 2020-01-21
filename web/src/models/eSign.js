import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `business/esigns`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class ESign {
  // GET by id
  static getById = async id => {
    let requestURL = `${requestUrl}?id.equals=${id}`;
    const result = await request(requestURL);
    return result;
  };

  // POST
  static create = async esign => {
    let requestURL = `${requestUrl}/string64`;
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
