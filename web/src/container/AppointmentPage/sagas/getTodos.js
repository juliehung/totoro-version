import { call, put, takeLatest, select } from 'redux-saga/effects';
import { GET_TODOS_START } from '../constant';
import { getTodosSuccess } from '../actions';
import Disposal from '../../../models/disposal';

export function* getTodos() {
  try {
    const getCalendarDate = state => state.appointmentPageReducer.calendar.calendarDate;
    const calDate = yield select(getCalendarDate);
    const result = yield call(Disposal.getByDate, calDate);
    yield put(getTodosSuccess(result));
  } catch (err) {
    //  ignore
    console.log(err);
  }
}

export function* watchGetTodos() {
  yield takeLatest(GET_TODOS_START, getTodos);
}
