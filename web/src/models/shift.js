import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `user-shifts`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Shift {
  static get = async data => {
    const requestURL = combineUrlAndQueryData(requestUrl, data);
    const result = await request(requestURL);
    return result;
  };

  static post = async body => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(body),
    };
    const result = await request(requestURL, options);
    return result;
  };

  static put = async body => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(body),
    };
    const result = await request(requestURL, options);
    return result;
  };

  static delete = async id => {
    const requestURL = `${requestUrl}/${id}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'DELETE',
    };
    const result = await requestNoParse(requestURL, options);
    return result;
  };
}
