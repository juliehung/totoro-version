import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';
import moment from 'moment';

const LOCATION = `business/nhi/statistics/index/od`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class OdIndexes {
  static get = async (begin, end) => {
    console.log('here');
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
    };
    return await request(
      combineUrlAndQueryData(requestURL, { begin: moment(begin).toISOString(), end: moment(end).toISOString() }),
      options,
    );
  };
}
