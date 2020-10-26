import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = `treatment-procedures`;
const requestUrl = `${apiUrl}/${LOCATION}`;

export default class TreatmentProcedure {
  static getRecently = async (patientId, params) => {
    let requestURL = `${requestUrl}/by/${patientId}/recently`;
    requestURL = combineUrlAndQueryData(requestURL, params);
    const result = await request(requestURL);
    return result;
  };
}
