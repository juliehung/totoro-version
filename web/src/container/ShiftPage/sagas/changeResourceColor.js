import { take, call, put } from 'redux-saga/effects';
import { CHANGE_RESOURCE_COLOR_START } from '../constant';
import { changeResourceColorSuccess } from '../actions';
import Configuration, { shiftResourceColorConfigPrefix } from '../../../models/configuration';

export function* changeResourceColor() {
  while (true) {
    try {
      const { id, color } = yield take(CHANGE_RESOURCE_COLOR_START);

      const result = yield call(Configuration.get, {
        'configKey.contains': `${shiftResourceColorConfigPrefix}.colorHex.${id}`,
      });
      if (result.length) {
        yield call(Configuration.updateMultiple, {
          configurations: [{ configKey: `${shiftResourceColorConfigPrefix}.colorHex.${id}`, configValue: color }],
        });
      } else {
        yield call(Configuration.createMultiple, {
          configurations: [{ configKey: `${shiftResourceColorConfigPrefix}.colorHex.${id}`, configValue: color }],
        });
      }
      yield put(changeResourceColorSuccess(id, color));
    } catch (err) {
      console.log(err);
    }
  }
}
