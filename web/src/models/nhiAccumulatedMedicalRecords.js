import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = 'nhi-accumulated-medical-records';

export default class NhiAccumulatedMedicalRecords {
  static getByPatientId = async pid => {
    const requestUrl = combineUrlAndQueryData(`${apiUrl}/${LOCATION}`, { 'patientId.equals': pid });
    return await request(requestUrl);
  };
}
