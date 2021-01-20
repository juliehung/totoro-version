import moment from 'moment';
import {
  GET_NHI_SALARY,
  GET_DOCTOR_NHI_SALARY,
  GET_VALID_NHI_BY_YEAR_MONTH,
  GET_NHI_ONE_BY_DISPOSAL_ID,
  INIT_NHI_SALARY,
} from '../constant';
import { call, fork, put, take } from 'redux-saga/effects';
import {
  getOdIndexesFail,
  getOdIndexesSuccess,
  getToothCleanFail,
  getToothCleanSuccess,
  getNhiSalarySuccess,
  nhiSalaryNotFound,
  getDoctorNhiSalarySuccess,
  getTotalPointByDisposalDateSuccess,
  getValidNhiYearMonthSuccess,
  validNhiYearMonthNotFound,
  getValidNhiByYearMonthSuccess,
  validNhiByYearMonthNotFound,
  getNhiOneByDisposalIdSuccess,
  nhiOneByDisposalIdNotFound,
  getTotalPointByDisposalDate,
  getEndoIndexesSuccess,
  getEndoIndexesFail,
} from '../actions';
import OdIndexes from '../../../models/odIndexes';
import ToothClean from '../../../models/toothClean';
import EndoIndexes from '../../../models/endoIndexes';
import DoctorNhiSalary from '../../../models/doctorNhiSalary';

export function* initNhiSalary() {
  while (true) {
    try {
      const { begin } = yield take(INIT_NHI_SALARY);
      const end = moment();
      yield put(getTotalPointByDisposalDate());
      const nhiSalary = yield call(DoctorNhiSalary.getInitSalary, { begin, end });
      const totalPointByDisposalDate = yield call(DoctorNhiSalary.getTotalPointPresentByDisposalDate, { begin, end });
      yield fork(getOdIndexes, begin, end);
      yield fork(getToothClean, begin, end);
      yield fork(getEndoIndexes, begin, end);
      yield fork(getValidNhiYearMonth, begin);
      yield put(getNhiSalarySuccess(nhiSalary));
      yield put(getTotalPointByDisposalDateSuccess({ data: totalPointByDisposalDate, startDate: begin }));
    } catch (error) {
      yield put(nhiSalaryNotFound());
    }
  }
}

export function* getNhiSalary() {
  while (true) {
    try {
      const { begin, checkedModalData } = yield take(GET_NHI_SALARY);
      const end = moment(begin).endOf('month');
      yield put(getTotalPointByDisposalDate());
      const nhiSalary = yield call(DoctorNhiSalary.getInitSalary, {
        begin,
        end,
        checkedModalData,
      });
      const totalPointByDisposalDate = yield call(DoctorNhiSalary.getTotalPointPresentByDisposalDate, {
        begin,
        end,
        checkedModalData,
      });
      yield fork(getOdIndexes, begin, end, checkedModalData);
      yield fork(getToothClean, begin, end, checkedModalData);
      yield fork(getEndoIndexes, begin, end, checkedModalData);
      yield put(getNhiSalarySuccess(nhiSalary));
      yield put(getTotalPointByDisposalDateSuccess({ data: totalPointByDisposalDate, startDate: begin }));
    } catch (error) {
      yield put(nhiSalaryNotFound());
    }
  }
}

export function* getDoctorNhiSalary() {
  while (true) {
    try {
      const { doctorId, begin, end } = yield take(GET_DOCTOR_NHI_SALARY);
      const doctorOneSalary = yield call(DoctorNhiSalary.getSalaryOneByDoctorId, { doctorId, begin, end });
      yield put(getDoctorNhiSalarySuccess({ doctorId, doctorOneSalary }));
    } catch (error) {
      console.log('error = ', error);
    }
  }
}

export function* getOdIndexes(begin, end, checkedModalData) {
  try {
    const params = {
      begin: begin.startOf('day').toISOString(),
      end: end.endOf('day').toISOString(),
      excludeDisposalId: checkedModalData ? checkedModalData : [],
    };
    const result = yield call(OdIndexes.get, params);
    yield put(getOdIndexesSuccess(result));
  } catch (error) {
    yield put(getOdIndexesFail([]));
  }
}

export function* getToothClean(begin, end, checkedModalData) {
  try {
    const params = {
      begin: begin.startOf('day').toISOString(),
      end: end.endOf('day').toISOString(),
      excludeDisposalId: checkedModalData ? checkedModalData : [],
    };
    const result = yield call(ToothClean.get, params);
    yield put(getToothCleanSuccess(result));
  } catch (error) {
    yield put(getToothCleanFail([]));
  }
}

export function* getEndoIndexes(begin, end, checkedModalData) {
  try {
    const params = {
      begin: begin.startOf('day').toISOString(),
      end: end.subtract(90, 'days').endOf('day').toISOString(),
      excludeDisposalId: checkedModalData ? checkedModalData : [],
    };
    const result = yield call(EndoIndexes.get, params);
    yield put(getEndoIndexesSuccess(result));
  } catch (error) {
    yield put(getEndoIndexesFail([]));
  }
}

export function* getValidNhiYearMonth(begin) {
  try {
    const validNhiYearMonths = yield call(DoctorNhiSalary.getValidNhiYearMonth);
    yield put(getValidNhiYearMonthSuccess(validNhiYearMonths));
    try {
      const validNhiData = yield call(DoctorNhiSalary.getValidNhiByYearMonth, begin.format('YYYYMM'));
      yield put(getValidNhiByYearMonthSuccess(validNhiData));
    } catch (error) {
      yield put(validNhiByYearMonthNotFound());
    }
  } catch (error) {
    yield put(validNhiYearMonthNotFound());
  }
}

export function* getValidNhiByYearMonth() {
  while (true) {
    try {
      const { yearMonth } = yield take(GET_VALID_NHI_BY_YEAR_MONTH);
      const validNhiData = yield call(DoctorNhiSalary.getValidNhiByYearMonth, yearMonth);
      yield put(getValidNhiByYearMonthSuccess(validNhiData));
    } catch (error) {
      yield put(validNhiByYearMonthNotFound());
    }
  }
}

export function* getNhiOneByDisposalId() {
  while (true) {
    try {
      const { disposalId } = yield take(GET_NHI_ONE_BY_DISPOSAL_ID);
      const nhiOne = yield call(DoctorNhiSalary.getNhiOneByDisposalId, disposalId);
      yield put(getNhiOneByDisposalIdSuccess(nhiOne));
    } catch (error) {
      yield put(nhiOneByDisposalIdNotFound());
    }
  }
}
