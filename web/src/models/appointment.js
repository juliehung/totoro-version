import request from '../utils/request';
import requestNoParse from '../utils/requestNoParse';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

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

  static getBetween = async (range, signal) => {
    let requestURL = `${requestUrl}/between`;
    const params = { beginDate: range.start.toISOString(), endDate: range.end.toISOString() };
    requestURL = combineUrlAndQueryData(requestURL, params);
    const options = { signal };
    const result = await request(requestURL, options);
    return result;
  };

  static getAppointmentsByPatientId = async id => {
    const requestURL = `${requestUrl}?patientId.equals=${id}`;
    const result = await request(requestURL);
    return result;
  };

  static editAppointment = async app => {
    const requestURL = `${requestUrl}`;
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
    const requestURL = `${requestUrl}`;
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
    const requestURL = `${requestUrl}/${id}`;
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
