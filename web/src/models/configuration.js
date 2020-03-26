import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `configuration-maps`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Configuration {
  static get = async data => {
    const requestURL = combineUrlAndQueryData(requestUrl, data);
    const result = await request(requestURL);
    return result;
  };

  static post = async body => {
    let requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(body),
    };
    const result = await request(requestURL, options);
    return result;
  };

  static delete = async id => {
    let requestURL = `${requestUrl}/${id}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'DELETE',
    };
    const result = await requestNoParse(requestURL, options);
    return result;
  };
}

export const defaultShiftConfigPrefix = 'web.shift.defaultShift';
