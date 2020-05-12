import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `patients`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Patient {
  // GET
  static getById = async id => {
    const requestURL = `${requestUrl}/${id}`;
    const result = await request(requestURL);
    return result;
  };

  static search = async searchText => {
    const requestURL = `${requestUrl}?search.contains=${searchText}`;
    const result = await request(requestURL);
    return result;
  };

  // POST
  static create = async patient => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(patient),
    };
    const result = await request(requestURL, options);
    return result;
  };

  // parseUWPBase64Token
  static put = async patient => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(patient),
    };
    const result = await request(requestURL, options);
    return result;
  };
}
