import { call, put, takeLatest, select, cancelled } from 'redux-saga/effects';
import { GET_TODOS_START } from '../constant';
import { getTodosSuccess } from '../actions';
import Disposal from '../../../models/disposal';

export function* getTodos() {
  const abortController = new AbortController();
  try {
    const getCalendarDate = state => state.appointmentPageReducer.calendar.calendarDate;
    const calDate = yield select(getCalendarDate);
    const result = yield call(Disposal.getByDate, calDate, abortController.signal);
    yield put(getTodosSuccess(result));
  } catch (err) {
    //  ignore
    console.log(err);
  } finally {
    if (yield cancelled()) {
      abortController.abort();
    }
  }
}

export function* watchGetTodos() {
  yield takeLatest(GET_TODOS_START, getTodos);
}
