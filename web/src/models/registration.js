import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

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
    const params = { web: true, beginDate: range.start.toISOString(), endDate: range.end.toISOString() };
    requestURL = combineUrlAndQueryData(requestURL, params);
    return await request(requestURL);
  };
}
