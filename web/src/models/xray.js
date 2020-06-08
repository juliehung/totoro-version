import request from '../utils/request';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const apiUrl = 'http://localhost:60087';
const LOCATION = `xray`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Xray {
  static get = async data => {
    const requestURL = combineUrlAndQueryData(requestUrl, data);
    const result = await request(requestURL);
    return result;
  };

  static greeting = async () => {
    const requestURL = `${requestUrl}/greeting`;
    const result = await request(requestURL);
    return result;
  };

  static shutdown = async () => {
    const requestURL = `${requestUrl}/shutdown`;
    const result = await request(requestURL);
    return result;
  };
}
