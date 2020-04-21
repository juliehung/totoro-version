import { fork } from 'redux-saga/effects';
import { getAccount } from './getAccount';
import { watchGetUsers } from './getUser';
import { watchGetSettings } from './getSettings';
import { putSettings } from './putSettings';

export default function* homePage() {
  yield fork(getAccount);
  yield fork(watchGetUsers);
  yield fork(watchGetSettings);
  yield fork(putSettings);
}
