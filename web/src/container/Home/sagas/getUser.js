import { call, put, takeLatest, all } from 'redux-saga/effects';
import user from '../../../models/user';
import { GET_USER_START } from '../constant';
import { getUserSuccess } from '../actions';

export function* getUsers() {
  try {
    const result = yield call(user.getAll);
    const responses = yield all(result.map(r => call(user.getByLogin, r.login)));
    yield put(getUserSuccess(responses));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetUsers() {
  yield takeLatest(GET_USER_START, getUsers);
}
