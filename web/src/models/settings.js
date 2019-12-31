import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `settings`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Settings {
  static getById = async () => {
    const revision = 0;
    const build = 2;
    const minor = 0;
    const major = 0;
    const id = (major * 1000000 + minor * 10000 + build * 100 + revision);
    let requestURL = `${requestUrl}/${id}`;
    const result = await request(requestURL);
    return result;
  };
}
