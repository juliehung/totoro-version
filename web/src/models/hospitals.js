import request from '../utils/request';
import apiUrl from '../utils/apiUrl';
import combineUrlAndQueryData from '../utils/combineUrlAndQueryData';

const LOCATION = 'hospitals';

export default class MedicalHospitalRecords {
  static getByMedicalHospitals = async cords => {
    const nhiAccumulatedMedicalTwRecodes = [];
    for (const { medicalInstitutionCode } of cords) {
      if (medicalInstitutionCode) {
        const requestUrl = combineUrlAndQueryData(`${apiUrl}/${LOCATION}`, { hospitalId: medicalInstitutionCode });
        await request(requestUrl).then(async ({ hospitalId, name }) => {
          await nhiAccumulatedMedicalTwRecodes.push({
            hospitalId,
            name,
          });
        });
      }
    }
    return Promise.resolve(nhiAccumulatedMedicalTwRecodes);
  };
}
