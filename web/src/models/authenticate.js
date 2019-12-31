import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';

const LOCATION = `authenticate`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Authenticate {
  // GET
  static get = async () => {
    let requestURL = `${requestUrl}`;
    const result = await requestNoParse(requestURL).then(response => response.text());
    if (result && result.length !== 0) {
      return result;
    }
    throw new Error('validate fail');
  };

  // POST
  static post = async account => {
    let requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(account),
    };
    const result = await request(requestURL, options);
    return result;
  };
}
