import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `calendars`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Calendar {
  static create = async calEvt => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(calEvt),
    };
    const result = await request(requestURL, options);
    return result;
  };

  static getBetween = async (range, signal) => {
    const params = {
      'end.greaterOrEqualThan': range.start.toISOString(),
      'start.lessOrEqualThan': range.end.toISOString(),
    };
    const requestURL = combineUrlAndQueryData(requestUrl, params);
    const option = { signal };
    const result = await request(requestURL, option);
    return result;
  };

  static edit = async calEvt => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(calEvt),
    };
    const result = await request(requestURL, options);
    return result;
  };

  static delete = async id => {
    const requestURL = `${requestUrl}/${id}`;
    const options = {
      method: 'DELETE',
    };
    const result = await requestNoParse(requestURL, options);
    return result;
  };
}
