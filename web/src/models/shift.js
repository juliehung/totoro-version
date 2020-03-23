import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';

const LOCATION = `user-shifts`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Shift {
  static get = async data => {
    let requestURL = requestUrl;
    if (data) {
      const searchParams = new URLSearchParams(data);
      const querystring = searchParams.toString();
      requestURL = requestURL + '?' + querystring;
    }
    const result = await request(requestURL);
    return result;
  };

  static post = async body => {
    let requestURL = `${requestUrl}`;
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

  static delete = async id => {
    let requestURL = `${requestUrl}/${id}`;
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
