import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_USERS_START } from '../constant';
import { getDoctorsSuccess } from '../actions';
import user from '../../../models/user';
import extractDoctorsFromUser from '../utils/extractDoctorsFromUser';

export function* getUsers() {
  try {
    const result = yield call(user.getAll);
    yield put(getDoctorsSuccess(extractDoctorsFromUser(result)));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetUsers() {
  yield takeLatest(GET_USERS_START, getUsers);
}
