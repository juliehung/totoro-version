import { fork } from 'redux-saga/effects';
import { setXrayVendor } from './setXrayVendor';
import { watchGetConfig } from './watchGetConfig';

export default function* settingPage() {
  yield fork(setXrayVendor);
  yield fork(watchGetConfig);
}
