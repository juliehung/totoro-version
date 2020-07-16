import { fork } from 'redux-saga/effects';
import { setConfigs } from './setConfigs';
import { watchGetConfig } from './watchGetConfig';

export default function* settingPage() {
  yield fork(setConfigs);
  yield fork(watchGetConfig);
}
