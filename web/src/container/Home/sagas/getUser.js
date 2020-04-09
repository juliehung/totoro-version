import { call, put, takeLatest } from 'redux-saga/effects';
import user from '../../../models/user';
import { GET_USER_START } from '../constant';
import { getUserSuccess } from '../actions';

export function* getUsers() {
  try {
    let result = yield call(user.getAll);
    for (const r of result) {
      const account = yield call(user.getByLogin, r.login);
      const id = account.id;
      const avatar = account.extendUser.avatar;
      if (avatar) {
        result = [...result.filter(r => r.id !== id), account];
      }
    }
    yield put(getUserSuccess(result));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetUsers() {
  yield takeLatest(GET_USER_START, getUsers);
}
