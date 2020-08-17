import {
  GET_DOCTOR_NHI_EXAM,
  GET_DOCTOR_NHI_EXAM_FAIL,
  GET_DOCTOR_NHI_EXAM_SUCCESS,
  GET_DOCTOR_NHI_TX,
  GET_DOCTOR_NHI_TX_FAIL,
  GET_DOCTOR_NHI_TX_SUCCESS,
  GET_OD_INDEXES,
  GET_OD_INDEXES_FAIL,
  GET_OD_INDEXES_SUCCESS,
} from './constant';

export function getOdIndexes(begin, end) {
  return { type: GET_OD_INDEXES, begin, end };
}

export function getOdIndexesSuccess(odIndexes) {
  return { type: GET_OD_INDEXES_SUCCESS, odIndexes };
}

export function getOdIndexesFail(odIndexes) {
  return { type: GET_OD_INDEXES_FAIL, odIndexes };
}

export function getDoctorNhiExam(begin, end) {
  return { type: GET_DOCTOR_NHI_EXAM, begin, end };
}

export function getDoctorNhiExamSuccess(doctorNhiExam) {
  return { type: GET_DOCTOR_NHI_EXAM_SUCCESS, doctorNhiExam };
}

export function getDoctorNhiExamFail(doctorNhiExam) {
  return { type: GET_DOCTOR_NHI_EXAM_FAIL, doctorNhiExam };
}

export function getDoctorNhiTx(begin, end) {
  return { type: GET_DOCTOR_NHI_TX, begin, end };
}

export function getDoctorNhiTxSuccess(doctorNhiTx) {
  return { type: GET_DOCTOR_NHI_TX_SUCCESS, doctorNhiTx };
}

export function getDoctorNhiTxFail(doctorNhiTx) {
  return { type: GET_DOCTOR_NHI_TX_FAIL, doctorNhiTx };
}
