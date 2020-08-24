import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `users`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class User {
  // GET
  static getAll = async () => {
    const params = { size: 100 };
    const requestURL = combineUrlAndQueryData(requestUrl, params);
    const result = await request(requestURL);
    return result;
  };

  static getByLogin = async login => {
    const requestURL = `${requestUrl}/${login}`;
    const result = await request(requestURL);
    return result;
  };
}
