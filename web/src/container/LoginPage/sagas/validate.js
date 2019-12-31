import { CHECK_TOKEN_VALIDATION } from '../constant';
import { call, put, take } from 'redux-saga/effects';
import Authenticate from '../../../models/authenticate';
import { changeLoginSuccess, checkTokenValidateFail } from '../actions';

export function* validate() {
  while (true) {
    try {
      yield take(CHECK_TOKEN_VALIDATION);
      yield call(Authenticate.get);
      yield put(changeLoginSuccess(true));
    } catch (error) {
      // ignore
      yield put(checkTokenValidateFail());
    }
  }
}
