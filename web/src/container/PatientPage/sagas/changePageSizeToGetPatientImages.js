import { call, take, put } from 'redux-saga/effects';
import { CHANGE_PAGE_SIZE_TO_GET_PATIENT_IMAGES } from '../constant';
import Patient from '../../../models/patient';
import { changePageSizeToGetPatientImagesSuccess } from '../actions';

export function* changePageSizeToGetPatientImages() {
  while (true) {
    try {
      const { id, page, size } = yield take(CHANGE_PAGE_SIZE_TO_GET_PATIENT_IMAGES);
      const patientImages = yield call(Patient.getImagesById, id, page, size);
      yield put(changePageSizeToGetPatientImagesSuccess(patientImages, page, size));
    } catch (error) {
      // ignore
    }
  }
}
