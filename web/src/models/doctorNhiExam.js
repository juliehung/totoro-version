import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `business/nhi/statistics/doctor-nhi-exam`;

export default class DoctorNhiExam {
  static get = async params => {
    const requestUrl = combineUrlAndQueryData(`${apiUrl}/${LOCATION}`, params);
    return await request(requestUrl);
  };
}
