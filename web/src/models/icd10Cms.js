import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `nhi-icd-10-cms`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class NhiIcd10Cms {
  static get = async () => {
    const result = await request(requestUrl);
    return result;
  };
}
