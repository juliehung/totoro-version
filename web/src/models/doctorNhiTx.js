import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `business/nhi/statistics/doctor-nhi-tx`;

export default class DoctorNhiTx {
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
