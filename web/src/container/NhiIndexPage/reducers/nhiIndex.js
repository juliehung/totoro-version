import produce from 'immer';
import {
  GET_OD_INDEXES_SUCCESS,
  GET_OD_INDEXES_FAIL,
  GET_DOCTOR_NHI_EXAM_SUCCESS,
  GET_DOCTOR_NHI_EXAM_FAIL,
  GET_DOCTOR_NHI_TX_SUCCESS,
  GET_DOCTOR_NHI_TX_FAIL,
  GET_TOOTH_CLEAN_SUCCESS,
  GET_TOOTH_CLEAN_FAIL,
  GET_INDEX_TREATMENT_PRECEDURE_SUCCESS,
  GET_INDEX_TREATMENT_PRECEDURE_FAIL,
  GET_NHI_SALARY_SUCCESS,
  GET_DOCTOR_NHI_SALARY_SUCCESS,
} from '../constant';

const initState = {
  nhiSalary: undefined,
  expandNhiSalary: undefined,
  odIndexes: [],
  doctorNhiExam: [],
  doctorNhiTx: [],
  toothClean: [],
  indexTreatmentProcedure: [],
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
export default (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_NHI_SALARY_SUCCESS:
        draft.nhiSalary = action.nhiSalary;
        break;
      case GET_DOCTOR_NHI_SALARY_SUCCESS:
        draft.expandNhiSalary = [].concat(!!state.expandNhiSalary ? state.expandNhiSalary : [], action.doctorOneSalary);
        break;
      case GET_OD_INDEXES_SUCCESS:
        draft.odIndexes = action.odIndexes;
        break;
      case GET_DOCTOR_NHI_EXAM_SUCCESS:
        draft.doctorNhiExam = action.doctorNhiExam;
        break;
      case GET_DOCTOR_NHI_TX_SUCCESS:
        draft.doctorNhiTx = action.doctorNhiTx;
        break;
      case GET_DOCTOR_NHI_TX_FAIL:
        break;
      case GET_TOOTH_CLEAN_SUCCESS:
        draft.toothClean = action.toothClean;
        break;
      case GET_INDEX_TREATMENT_PRECEDURE_SUCCESS:
        draft.indexTreatmentProcedure = action.indexTreatmentProcedure;
        break;
      case GET_OD_INDEXES_FAIL:
      case GET_DOCTOR_NHI_EXAM_FAIL:
      case GET_TOOTH_CLEAN_FAIL:
      case GET_INDEX_TREATMENT_PRECEDURE_FAIL:
        break;
      default:
        break;
    }
  });
