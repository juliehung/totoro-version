import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `nhi-medical-records`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class NhiExtendPaitent {
  static getById = async (id, page, size) => {
    const params = {
      'nhiExtendPatientId.equals': id,
      page: page,
      size: size,
    };
    const requestURL = combineUrlAndQueryData(requestUrl, params);
    const result = await request(requestURL);
    return result;
  };
}
