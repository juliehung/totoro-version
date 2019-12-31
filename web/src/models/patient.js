import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `patients`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Patient {
  // GET
  static getById = async id => {
    let requestURL = `${requestUrl}/${id}`;
    const result = await request(requestURL);
    return result;
  };

  static search = async searchText => {
    let requestURL = `${requestUrl}?search.contains=${searchText}`;
    const result = await request(requestURL);
    return result;
  };

  // POST
  static create = async patient => {
    let requestURL = `${requestUrl}`;
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
}
