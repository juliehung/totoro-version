import { call, take, put } from 'redux-saga/effects';
import Configuration, { xRayVendorPrefix } from '../../../models/configuration';
import { SET_XRAY_VENDOR } from '../constant';
import { setXrayVendorSuccess } from '../actions';

export function* setXrayVendor() {
  while (true) {
    try {
      const { vendor, value } = yield take(SET_XRAY_VENDOR);
      const result = yield call(Configuration.get, {
        'configKey.contains': `${xRayVendorPrefix}.${vendor}`,
      });
      if (result.length) {
        yield call(Configuration.updateMultiple, {
          configurations: [{ configKey: `${xRayVendorPrefix}.${vendor}`, configValue: value }],
        });
      } else {
        yield call(Configuration.createMultiple, {
          configurations: [{ configKey: `${xRayVendorPrefix}.${vendor}`, configValue: value }],
        });
      }
      yield put(setXrayVendorSuccess());
    } catch (err) {
      console.log(err);
    }
  }
}
