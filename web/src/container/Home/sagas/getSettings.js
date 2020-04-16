import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_SETTINGS_START } from '../constant';
import { getSettingsSuccess } from '../actions';
import Settings from '../../../models/settings';

export function* getSettings() {
  try {
    const result = yield call(Settings.getById);
    yield put(getSettingsSuccess(result));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetSettings() {
  yield takeLatest(GET_SETTINGS_START, getSettings);
}
