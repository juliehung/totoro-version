import { take, call, put } from 'redux-saga/effects';
import { GET_DEFAULT_SHIFT_START } from '../constant';
import { getDefaultShiftSuccess } from '../actions';
import Configuration, { defaultShiftConfigPrefix } from '../../../models/configuration';

export function* getDefaultShift() {
  while (true) {
    try {
      yield take(GET_DEFAULT_SHIFT_START);
      const result = yield call(Configuration.get, { 'configKey.contains': defaultShiftConfigPrefix });
      yield put(getDefaultShiftSuccess(result));
    } catch (err) {
      console.log(err);
    }
  }
}
