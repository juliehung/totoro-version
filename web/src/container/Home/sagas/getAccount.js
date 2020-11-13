import { take, call, put } from 'redux-saga/effects';
import { GET_ACCOUNT_START } from '../constant';
import { getAccountSuccess } from '../actions';
import Account from '../../../models/account';

export function* getAccount() {
  while (true) {
    try {
      yield take(GET_ACCOUNT_START);
      const account = yield call(Account.get);
      yield put(getAccountSuccess(account));
    } catch (error) {
      console.log(error);
    }
  }
}
