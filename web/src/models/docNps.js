import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `doc-nps`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class DocNps {
  // POST
  static post = async doc => {
    let requestURL = `${requestUrl}`;
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
