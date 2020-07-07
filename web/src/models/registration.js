import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `appointments`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Registration {
  constructor(id, startTime, endTime) {
    this.id = id;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  static getBetween = async range => {
    let requestURL = `${requestUrl}/with-relationship/between`;
    const query = '?beginDate=' + range.start.toISOString() + '&endDate=' + range.end.toISOString();
    requestURL += query;
    return await request(requestURL);
  };
}
