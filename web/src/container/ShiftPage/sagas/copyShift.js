import { take, call, put, select, all } from 'redux-saga/effects';
import { ON_COPY_SHIFT_START } from '../constant';
import { onCopyShiftSuccess } from '../actions';
import Shift from '../../../models/shift';
import moment from 'moment';

const accessCopyReducer = ({ shiftPageReducer }) => shiftPageReducer.copy;

export function* copyShift() {
  while (true) {
    try {
      yield take(ON_COPY_SHIFT_START);
      const copyReducer = yield select(accessCopyReducer);
      const { range, prevRange, doctor, deleteCurrent, selectAllDoctor } = copyReducer;
      const deleteConfig = {
        ...{
          'toDate.greaterOrEqualThan': range.start.toISOString(),
          'fromDate.lessOrEqualThan': range.end.toISOString(),
        },
        ...(!selectAllDoctor && { 'userId.equals': doctor.id }),
      };
      const createConfig = {
        ...{
          'toDate.greaterOrEqualThan': prevRange.start.toISOString(),
          'fromDate.lessOrEqualThan': prevRange.end.toISOString(),
        },
        ...(!selectAllDoctor && { 'userId.equals': doctor.id }),
      };

      if (deleteCurrent) {
        yield deleteShifts(deleteConfig);
      }
      yield createShiftsInterval7Days(createConfig);

      yield put(onCopyShiftSuccess());
    } catch (err) {
      console.log(err);
    }
  }
}

function* deleteShifts(rule) {
  const currentShift = yield call(Shift.get, rule);
  yield all(currentShift.map(c => call(Shift.delete, c.id)));
}

function* createShiftsInterval7Days(rule) {
  const prevShift = yield call(Shift.get, rule);
  const newShift = prevShift.map(s => ({
    userId: s.userId,
    fromDate: moment(s.fromDate).add(7, 'd').toISOString(),
    toDate: moment(s.toDate).add(7, 'd').toISOString(),
  }));
  yield all(newShift.map(n => call(Shift.post, n)));
}
