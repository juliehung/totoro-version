import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `disposals`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Disposal {
  static getByDate = async (date, signal) => {
    const requestURL = `${requestUrl}?createdDate.greaterOrEqualThan=${date
      .clone()
      .startOf('day')
      .toISOString()}&createdDate.lessThan=${date.clone().add(1, 'day').startOf('day').toISOString()}`;

    const options = { signal };
    const result = await request(requestURL, options);
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
