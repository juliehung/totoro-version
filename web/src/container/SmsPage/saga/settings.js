import { call, put, take } from 'redux-saga/effects';
import { GET_CLINIC_SETTINGS } from '../constant';
import { getClinicSettingsSuccess } from '../action';
import Settings from '../../../models/settings';

export function* getSettings() {
  try {
    yield take(GET_CLINIC_SETTINGS);
    const result = yield call(Settings.getById);
    yield put(getClinicSettingsSuccess(result));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}
