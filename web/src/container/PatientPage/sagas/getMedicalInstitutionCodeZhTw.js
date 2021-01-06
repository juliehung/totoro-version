import { take, call, put, delay } from 'redux-saga/effects';
import { GET_MEDICAL_INSTITUTION_CODE_ZHTW } from '../constant';
import { getMedicalInstitutionCodeZhTwSuccess } from '../actions';
import MedicalHospitalRecords from '../../../models/hospitals';

export function* getMedicalInstitutionCodeZhTw() {
  while (true) {
    try {
      const { nhiAccumulatedMedicalRecords } = yield take(GET_MEDICAL_INSTITUTION_CODE_ZHTW);
      yield delay(300);
      const nhiAccumulatedMedicalTwRecodes = yield call(
        MedicalHospitalRecords.getByMedicalHospitals,
        nhiAccumulatedMedicalRecords,
      );
      yield put(getMedicalInstitutionCodeZhTwSuccess(nhiAccumulatedMedicalTwRecodes));
    } catch (error) {
      // ignore
      console.log('error', error);
    }
  }
}
