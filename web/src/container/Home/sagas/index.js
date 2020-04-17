import { fork } from 'redux-saga/effects';
import { getAccount } from './getAccount';
import { watchGetUsers } from './getUser';
import { watchGetSettings } from './getSettings';

export default function* homePage() {
  yield fork(getAccount);
  yield fork(watchGetUsers);
  yield fork(watchGetSettings);
}
