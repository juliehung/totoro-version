import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';

const LOCATION = `authenticate`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Authenticate {
  // GET
  static get = async () => {
    const result = await requestNoParse(requestUrl).then(response => response.text());
    if (result && result.length !== 0) {
      return result;
    }
    throw new Error('validate fail');
  };

  // POST
  static post = async account => {
    const body = { ...account, rememberMe: true };
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(body),
    };
    const result = await request(requestUrl, options);
    return result;
  };
}
