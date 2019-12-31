import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';

const LOCATION = `appointments`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class Appointment {
  constructor(id, patientId, doctorId, startTime, endTime, tag, note) {
    this.id = id;
    this.patientId = patientId;
    this.doctorId = doctorId;
    this.startTime = startTime;
    this.endTime = endTime;
    this.tag = tag;
    this.note = note;
  }

  static getBetween = async range => {
    let requestURL = `${requestUrl}/between`;
    const query = '?beginDate=' + range.start.toISOString() + '&endDate=' + range.end.toISOString();
    requestURL += query;
    const result = await request(requestURL);
    return result;
  };

  static getAppointmentsByPatientId = async id => {
    let requestURL = `${requestUrl}?patientId.equals=${id}`;
    const result = await request(requestURL);
    return result;
  };

  static editAppointment = async app => {
    let requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(app),
    };
    const result = await request(requestURL, options);
    return result;
  };

  static create = async app => {
    let requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(app),
    };
    const result = await request(requestURL, options);
    return result;
  };

  static delete = async id => {
    let requestURL = `${requestUrl}/${id}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'DELETE',
    };
    const result = await requestNoParse(requestURL, options);
    return result;
  };
}
