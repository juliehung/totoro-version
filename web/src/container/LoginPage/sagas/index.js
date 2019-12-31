import { fork } from 'redux-saga/effects';
import { login } from './login';
import { validate } from './validate';

export default function* loginPage() {
  yield fork(login);
  yield fork(validate);
}
