import {
    GET_USERS
  } from '../constant';
  import {
    call,
    take,
    put
  } from 'redux-saga/effects';
  import { getUsersSuccess } from '../action';
  import User from '../../../models/user';
  
  export function* getUsers() {
    while (true) {
      try {
        yield take(GET_USERS);
        const result = yield call(User.getAll);
        yield put(getUsersSuccess(result));
      } catch (err) {
        //  ignore
        console.log(err);
      }
    }
  }
  