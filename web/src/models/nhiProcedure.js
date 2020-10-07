import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `nhi-procedures`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class NhiProcedure {
  static get = async () => {
    const result = await request(requestUrl);
    return result;
  };
}
