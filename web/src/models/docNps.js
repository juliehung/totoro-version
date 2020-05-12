import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `doc-nps`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class DocNps {
  // get by pid
  static getByPid = async pid => {
    const requestURL = `${apiUrl}/business/${LOCATION}?patientId=${pid}`;
    const result = await request(requestURL);
    return result;
  };

  // get by id
  static getById = async id => {
    const requestURL = `${requestUrl}/${id}`;
    const result = await request(requestURL);
    return result;
  };

  // POST
  static post = async doc => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(doc),
    };
    const result = await request(requestURL, options);
    return result;
  };
}
