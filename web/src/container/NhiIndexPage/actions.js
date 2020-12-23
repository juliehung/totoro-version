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
  GET_TOOTH_CLEAN,
  GET_TOOTH_CLEAN_FAIL,
  GET_TOOTH_CLEAN_SUCCESS,
  GET_INDEX_TREATMENT_PRECEDURE,
  GET_INDEX_TREATMENT_PRECEDURE_SUCCESS,
  GET_INDEX_TREATMENT_PRECEDURE_FAIL,
  INIT_NHI_SALARY,
  GET_NHI_SALARY_SUCCESS,
  NHI_SALARY_NOT_FOUND,
  GET_DOCTOR_NHI_SALARY,
  GET_DOCTOR_NHI_SALARY_SUCCESS,
} from './constant';

export function getNhiSalary(begin, end) {
  return { type: INIT_NHI_SALARY, begin, end };
}

export function getNhiSalarySuccess(nhiSalary) {
  return { type: GET_NHI_SALARY_SUCCESS, nhiSalary };
}

export function nhiSalaryNotFound() {
  return { type: NHI_SALARY_NOT_FOUND };
}

export function getDoctorNhiSalary(doctorId, begin, end) {
  return { type: GET_DOCTOR_NHI_SALARY, doctorId, begin, end };
}

export function getDoctorNhiSalarySuccess(doctorOneSalary) {
  return { type: GET_DOCTOR_NHI_SALARY_SUCCESS, doctorOneSalary };
}

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

export function getToothClean(begin, end) {
  return { type: GET_TOOTH_CLEAN, begin, end };
}

export function getToothCleanSuccess(toothClean) {
  return { type: GET_TOOTH_CLEAN_SUCCESS, toothClean };
}

export function getToothCleanFail(toothClean) {
  return { type: GET_TOOTH_CLEAN_FAIL, toothClean };
}

export function getIndexTreatmentProcedure(begin, end) {
  return { type: GET_INDEX_TREATMENT_PRECEDURE, begin, end };
}

export function getIndexTreatmentProcedureSuccess(indexTreatmentProcedure) {
  return { type: GET_INDEX_TREATMENT_PRECEDURE_SUCCESS, indexTreatmentProcedure };
}

export function getIndexTreatmentProcedureFail(indexTreatmentProcedure) {
  return { type: GET_INDEX_TREATMENT_PRECEDURE_FAIL, indexTreatmentProcedure };
}
