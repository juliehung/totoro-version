import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `business/nhi/statistics/index/tooth-clean`;

export default class ToothClean {
  static get = async params => {
    const options = {
      headers: {
        'content-type': 'application/json',
      },
    };
    const requestUrl = combineUrlAndQueryData(`${apiUrl}/${LOCATION}`, params);
    return await request(requestUrl, options);
  };
}
