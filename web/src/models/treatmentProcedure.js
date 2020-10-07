import request from '../utils/request';
import apiUrl from '../utils/apiUrl';

const LOCATION = `treatment-procedures`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class TreatmentProcedure {
  static getRecently = async patientId => {
    const requestURL = `${requestUrl}/by/${patientId}/recently`;
    const result = await request(requestURL);
    return result;
  };
}
