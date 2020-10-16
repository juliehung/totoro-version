import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `patients`;
const requestUrl = `${apiUrl}/${LOCATION}`;

const businessRequestUrl = `${apiUrl}/business/${LOCATION}`;

export default class Patient {
  // GET
  static getById = async id => {
    const requestURL = `${requestUrl}/${id}`;
    const result = await request(requestURL);
    return result;
  };

  static search = async searchText => {
    const requestURL = `${requestUrl}?search.contains=${searchText}`;
    const result = await request(requestURL);
    return result;
  };

  static searchByName = async searchText => {
    const requestURL = `${businessRequestUrl}/name?search=${searchText}&size=50`;
    const result = await request(requestURL);
    return result;
  };

  static searchByBirth = async searchText => {
    const requestURL = `${businessRequestUrl}/birth?search=${searchText}&format=ROC&size=50`;
    const result = await request(requestURL);
    return result;
  };

  static searchByPhone = async searchText => {
    const requestURL = `${businessRequestUrl}/phone?search=${searchText}&size=50`;
    const result = await request(requestURL);
    return result;
  };

  static searchByMedicalId = async searchText => {
    const requestURL = `${businessRequestUrl}/medical-id?search=${searchText}&size=50`;
    const result = await request(requestURL);
    return result;
  };

  static searchByNationalId = async searchText => {
    const requestURL = `${businessRequestUrl}/national-id?search=${searchText}&size=50`;
    const result = await request(requestURL);
    return result;
  };

  // POST
  static create = async patient => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'POST',
      body: JSON.stringify(patient),
    };
    const result = await request(requestURL, options);
    return result;
  };

  // parseUWPBase64Token
  static put = async patient => {
    const requestURL = `${requestUrl}`;
    const options = {
      headers: {
        'content-type': 'application/json',
      },
      method: 'PUT',
      body: JSON.stringify(patient),
    };
    const result = await request(requestURL, options);
    return result;
  };
}
