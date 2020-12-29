import {
  GET_OD_INDEXES,
  GET_DOCTOR_NHI_EXAM,
  GET_DOCTOR_NHI_TX,
  GET_INDEX_TREATMENT_PRECEDURE,
  GET_TOOTH_CLEAN,
  GET_NHI_SALARY,
  GET_DOCTOR_NHI_SALARY,
  GET_VALID_NHI_YEAR_MONTH,
  GET_VALID_NHI_BY_YEAR_MONTH,
  GET_NHI_ONE_BY_DISPOSAL_ID,
  INIT_NHI_SALARY,
} from '../constant';
import { call, delay, fork, put, take } from 'redux-saga/effects';
import {
  getOdIndexesFail,
  getOdIndexesSuccess,
  getDoctorNhiExamSuccess,
  getDoctorNhiExamFail,
  getDoctorNhiTxSuccess,
  getDoctorNhiTxFail,
  getToothCleanFail,
  getToothCleanSuccess,
  getIndexTreatmentProcedureSuccess,
  getNhiSalarySuccess,
  nhiSalaryNotFound,
  getDoctorNhiSalarySuccess,
  getTotalPointByDisposalDate,
  getTotalPointByDisposalDateSuccess,
  getValidNhiYearMonthSuccess,
  validNhiYearMonthNotFound,
  getValidNhiByYearMonthSuccess,
  validNhiByYearMonthNotFound,
  getNhiOneByDisposalIdSuccess,
  nhiOneByDisposalIdNotFound,
} from '../actions';
import OdIndexes from '../../../models/odIndexes';
import DoctorNhiExam from '../../../models/doctorNhiExam';
import DoctorNhiTx from '../../../models/doctorNhiTx';
import ToothClean from '../../../models/toothClean';
import IndexTreatmentProcedure from '../../../models/indexTreatmentProcedure';
import DoctorNhiSalary from '../../../models/doctorNhiSalary';

export function* initNhiSalary() {
  while (true) {
    try {
      const { begin, end } = yield take(INIT_NHI_SALARY);
      const nhiSalary = yield call(DoctorNhiSalary.getInitSalary, { begin, end });
      yield delay(300);
      yield put(getNhiSalarySuccess(nhiSalary));
      yield fork(getOdIndexes, begin, end);
      yield fork(getToothClean, begin, end);
      yield fork(getValidNhiYearMonth, begin);
      const totalPointByDisposalDate = yield call(DoctorNhiSalary.getTotalPointPresentByDisposalDate, { begin, end });
      yield put(getTotalPointByDisposalDateSuccess({ data: totalPointByDisposalDate, startDate: begin }));
    } catch (error) {
      yield put(nhiSalaryNotFound());
    }
  }
}

export function* getNhiSalary() {
  while (true) {
    try {
      const { begin, end, checkedModalData } = yield take(GET_NHI_SALARY);
      const nhiSalary = yield call(DoctorNhiSalary.getInitSalary, { begin, end });
      yield delay(300);
      yield put(getNhiSalarySuccess(nhiSalary));
      yield put(getTotalPointByDisposalDate());
      const totalPointByDisposalDate = yield call(DoctorNhiSalary.getTotalPointPresentByDisposalDate, { begin, end });
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
      yield delay(300);
      yield put(getDoctorNhiSalarySuccess({ doctorId, doctorOneSalary }));
    } catch (error) {
      console.log('error = ', error);
    }
  }
}

export function* getOdIndexes(begin, end) {
  try {
    const params = {
      begin: begin.startOf('day').toISOString(),
      end: end.endOf('day').toISOString(),
    };
    const result = yield call(OdIndexes.get, params);
    yield put(getOdIndexesSuccess(result));
  } catch (error) {
    yield put(getOdIndexesFail([]));
  }
}

export function* getToothClean(begin, end) {
  try {
    const params = {
      begin: begin.startOf('day').toISOString(),
      end: end.endOf('day').toISOString(),
    };
    const result = yield call(ToothClean.get, params);
    yield put(getToothCleanSuccess(result));
  } catch (error) {
    yield put(getToothCleanFail([]));
  }
}

export function* getValidNhiYearMonth(begin) {
  try {
    const validNhiYearMonths = yield call(DoctorNhiSalary.getValidNhiYearMonth);
    yield delay(300);
    yield put(getValidNhiYearMonthSuccess(validNhiYearMonths));
    try {
      const validNhiData = yield call(DoctorNhiSalary.getValidNhiByYearMonth, begin.format('YYYYMM'));
      yield put(getValidNhiByYearMonthSuccess(validNhiData));
      // try {
      //   const nhiOne = yield call(DoctorNhiSalary.getNhiOneByDisposalId, validNhiData[0]?.disposalId);
      //   yield put(getNhiOneByDisposalIdSuccess(nhiOne));
      // } catch (error) {
      //   yield put(nhiOneByDisposalIdNotFound());
      // }
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

export function* getDoctorNhiExam() {
  while (true) {
    try {
      const { begin, end } = yield take(GET_DOCTOR_NHI_EXAM);
      const params = {
        begin: begin.startOf('day').toISOString(),
        end: end.endOf('day').toISOString(),
      };
      const result = yield call(DoctorNhiExam.get, params);
      yield put(getDoctorNhiExamSuccess(result));
    } catch (error) {
      console.error(error);
      yield put(getDoctorNhiExamFail([]));
    }
  }
}

export function* getDoctorNhiTx() {
  while (true) {
    try {
      const { begin, end } = yield take(GET_DOCTOR_NHI_TX);
      const params = {
        begin: begin.startOf('day').toISOString(),
        end: end.endOf('day').toISOString(),
      };
      const result = yield call(DoctorNhiTx.get, params);
      yield put(getDoctorNhiTxSuccess(result));
    } catch (error) {
      console.error(error);
      yield put(getDoctorNhiTxFail([]));
    }
  }
}

export function* getIndexTreatmentProcedure() {
  while (true) {
    try {
      const { begin, end } = yield take(GET_INDEX_TREATMENT_PRECEDURE);
      const params = {
        begin: begin.startOf('day').toISOString(),
        end: end.endOf('day').toISOString(),
      };
      const result = yield call(IndexTreatmentProcedure.get, params);
      yield put(getIndexTreatmentProcedureSuccess(result));
    } catch (error) {
      yield put(getToothCleanFail([]));
    }
  }
}
