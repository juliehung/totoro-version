import { take, call, put } from 'redux-saga/effects';
import { GET_RESOURCE_COLOR_START } from '../constant';
import { getResourceColorSuccess } from '../actions';
import Configuration, { shiftResourceColorConfigPrefix } from '../../../models/configuration';

export function* getResourceColor() {
  while (true) {
    try {
      yield take(GET_RESOURCE_COLOR_START);
      const result = yield call(Configuration.get, { 'configKey.contains': shiftResourceColorConfigPrefix });
      yield put(getResourceColorSuccess(result));
    } catch (err) {
      console.log(err);
    }
  }
}
