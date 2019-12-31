import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `disposals`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Disposal {
  static getByDate = async date => {
    let requestURL = `${requestUrl}?createdDate.greaterOrEqualThan=${date
      .clone()
      .startOf('day')
      .toISOString()}&createdDate.lessThan=${date
      .clone()
      .add(1, 'day')
      .startOf('day')
      .toISOString()}`;
    const result = await request(requestURL);
    return result;
  };
}
