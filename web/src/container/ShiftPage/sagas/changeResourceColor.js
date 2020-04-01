import { take, select, call, put } from 'redux-saga/effects';
import { CHANGE_RESOURCE_COLOR_START } from '../constant';
import { changeResourceColorSuccess } from '../actions';
import Configuration, { shiftResourceColorConfigPrefix } from '../../../models/configuration';

const selectDefaultShift = state => state.shiftPageReducer.defaultShift.shift;

export function* changeResourceColor() {
  while (true) {
    try {
      const { id, color } = yield take(CHANGE_RESOURCE_COLOR_START);
      yield call(Configuration.createMultiple, {
        configurations: [{ configKey: `${shiftResourceColorConfigPrefix}.colorHex.${id}`, configValue: color }],
      });
      yield put(changeResourceColorSuccess());
    } catch (err) {
      console.log(err);
    }
  }
}
