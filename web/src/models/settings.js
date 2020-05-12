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
    const id = major * 1000000 + minor * 10000 + build * 100 + revision;
    const requestURL = `${requestUrl}/${id}`;
    const result = await request(requestURL);
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
}
