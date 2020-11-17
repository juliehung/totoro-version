import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `validation`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class NhiPatientStatus {
  static getById = async (id, code) => {
    const requestURL = `${requestUrl}/${code}?patientId=${id}`;
    const result = await request(requestURL);
    return result;
  };
}
