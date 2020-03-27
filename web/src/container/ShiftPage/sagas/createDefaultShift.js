import { take, select, call, put } from 'redux-saga/effects';
import { CREATE_DEFAULT_SHIFT_START } from '../constant';
import { createDefaultShiftSuccess } from '../actions';
import Configuration, { defaultShiftConfigPrefix } from '../../../models/configuration';
import { handleShiftForApi } from '../utils/handleShiftForApi';

const selectDefaultShift = state => state.shiftPageReducer.defaultShift.shift;

export function* createDefaultShift() {
  while (true) {
    try {
      yield take(CREATE_DEFAULT_SHIFT_START);
      const defaultShift = yield select(selectDefaultShift);
      const shift = defaultShift.find(s => s.isNew);
      const shiftForApi = handleShiftForApi(shift);
      console.log(shiftForApi);
      yield call(Configuration.post, {
        configKey: `${defaultShiftConfigPrefix}.${shiftForApi.id}.name`,
        configValue: shiftForApi.name,
      });
      yield call(Configuration.post, {
        configKey: `${defaultShiftConfigPrefix}.${shiftForApi.id}.time`,
        configValue: shiftForApi.time,
      });
      yield put(createDefaultShiftSuccess());
    } catch (err) {
      console.log(err);
    }
  }
}
