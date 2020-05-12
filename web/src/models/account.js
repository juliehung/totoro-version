import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `account`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Account {
  static get = async () => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
    };
    const result = await request(requestURL, options);
    return result;
  };
}
