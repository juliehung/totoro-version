import { fork } from 'redux-saga/effects';
import { getAccount } from './getAccount';

export default function* homePage() {
  yield fork(getAccount);
}
