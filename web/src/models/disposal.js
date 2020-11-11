import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `disposals`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Disposal {
  static getByDate = async (date, signal) => {
    const params = {
      'createdDate.greaterOrEqualThan': date.clone().startOf('day').toISOString(),
      'createdDate.lessThan': date.clone().add(1, 'day').startOf('day').toISOString(),
    };
    const requestURL = combineUrlAndQueryData(requestUrl, params);
    const options = { signal };
    const result = await request(requestURL, options);
    return result;
  };

  static getByPatientId = async patientId => {
    const params = { 'patientId.equals': patientId, page: 0, size: 50 };
    const requestURL = combineUrlAndQueryData(requestUrl, params);
    const result = await request(requestURL);
    return result;
  };

  static put = async disposal => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(disposal),
    };
    const result = await request(requestURL, options);
    return result;
  };
}
