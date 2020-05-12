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
    const requestURL = `${requestUrl}`;
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

  static createMultiple = async body => {
    const requestURL = `${requestUrl}/multiple`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(body),
    };
    const result = await requestNoParse(requestURL, options);
    return result;
  };

  static updateMultiple = async body => {
    const requestURL = `${requestUrl}/multiple`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(body),
    };
    const result = await requestNoParse(requestURL, options);
    return result;
  };

  static deleteMultiple = async body => {
    const requestURL = `${requestUrl}/multiple`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'DELETE',
      body: JSON.stringify(body),
    };
    const result = await requestNoParse(requestURL, options);
    return result;
  };
}

export const defaultShiftConfigPrefix = 'web.shift.defaultShift';
export const shiftResourceColorConfigPrefix = 'web.shift.resourceColor';
