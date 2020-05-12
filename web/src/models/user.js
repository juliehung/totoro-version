import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `users`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class User {
  // GET
  static getAll = async () => {
    const requestURL = `${requestUrl}?size=100`;
    const result = await request(requestURL);
    return result;
  };

  static getByLogin = async login => {
    const requestURL = `${requestUrl}/${login}`;
    const result = await request(requestURL);
    return result;
  };
}
