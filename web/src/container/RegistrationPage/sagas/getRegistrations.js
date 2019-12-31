import { call, put, takeLatest } from 'redux-saga/effects';
import { GET_REGISTRATIONS_START } from '../constant';
import { getRegistrationsSuccess } from '../actions';
import registration from '../../../models/registration';

export function* getRegistrations({ range }) {
    try {
        const result = yield call(registration.getBetween, range);
        yield put(getRegistrationsSuccess(result));
    } catch (err) {
        //  ignore
        console.log(err);
    }
}

export function* watchGetRegistrations() {
    yield takeLatest(GET_REGISTRATIONS_START, getRegistrations);
}
