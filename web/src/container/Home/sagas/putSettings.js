import { PUT_SETTINGS_START } from '../constant';
import { call, put, take } from 'redux-saga/effects';
import Settings from '../../../models/settings';
import { putSettingsSuccess } from '../actions';

export function* putSettings() {
  while (true) {
    try {
      const { settings } = yield take(PUT_SETTINGS_START);
      yield call(Settings.put, settings);
      yield put(putSettingsSuccess(settings));
    } catch (error) {
      console.log(error);
    }
  }
}
