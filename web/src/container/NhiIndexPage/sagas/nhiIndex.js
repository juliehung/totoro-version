import { GET_OD_INDEXES, GET_DOCTOR_NHI_EXAM, GET_DOCTOR_NHI_TX } from '../constant';
import { call, put, take } from 'redux-saga/effects';
import {
  getOdIndexesFail,
  getOdIndexesSuccess,
  getDoctorNhiExamSuccess,
  getDoctorNhiExamFail,
  getDoctorNhiTxSuccess,
  getDoctorNhiTxFail,
} from '../actions';
import OdIndexes from '../../../models/odIndexes';
import DoctorNhiExam from '../../../models/doctorNhiExam';
import DoctorNhiTx from '../../../models/doctorNhiTx';

export function* getOdIndexes() {
  while (true) {
    try {
      const { begin, end } = yield take(GET_OD_INDEXES);
      const params = {
        begin: begin.startOf('day').toISOString(),
        end: end.endOf('day').toISOString(),
      };
      const result = yield call(OdIndexes.get, params);
      yield put(getOdIndexesSuccess(result));
    } catch (error) {
      console.error(error);
      yield put(getOdIndexesFail([]));
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
