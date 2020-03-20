import { fork } from 'redux-saga/effects';
import { getAccount } from './getAccount';
import { watchGetUsers } from './getUser';

export default function* homePage() {
  yield fork(getAccount);
  yield fork(watchGetUsers);
}
