import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `nhi-extend-patients`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class NhiExtendPaitent {
  static getById = async id => {
    const requestURL = `${requestUrl}/${id}`;
    const result = await request(requestURL);
    return result;
  };
}
