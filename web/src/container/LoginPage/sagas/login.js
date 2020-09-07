import { LOGIN_START } from '../constant';
import { call, put, take } from 'redux-saga/effects';
import Authenticate from '../../../models/authenticate';
import { loginSuccess, changeLoginFail } from '../actions';

export function* login() {
  while (true) {
    try {
      const action = yield take(LOGIN_START);
      const token = yield call(Authenticate.post, action.data);
      yield put(loginSuccess(token));
    } catch (error) {
      yield put(changeLoginFail(true));
    }
  }
}
