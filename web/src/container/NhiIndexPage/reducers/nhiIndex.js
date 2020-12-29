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
  GET_TOTAL_POINT_BY_DISPOSAL_DATE,
  GET_TOTAL_POINT_BY_DISPOSAL_DATE_SUCCESS,
  GET_VALID_NHI_YEAR_MONTH_SUCCESS,
  GET_VALID_NHI_BY_YEAR_MONTH_SUCCESS,
  GET_NHI_ONE_BY_DISPOSAL_ID_SUCCESS,
  GET_NHI_ONE_BY_DISPOSAL_ID,
} from '../constant';

const initState = {
  nhiSalary: undefined,
  expandNhiSalary: undefined,
  totalPointByDisposalDate: undefined,
  totalPointLoading: false,
  odIndexes: [],
  doctorNhiExam: [],
  doctorNhiTx: [],
  toothClean: [],
  indexTreatmentProcedure: [],
  validNhiYearMonth: undefined,
  validNhiData: undefined,
  nhiOneLoading: undefined,
  nhiOne: undefined,
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
      case GET_TOTAL_POINT_BY_DISPOSAL_DATE:
        draft.totalPointLoading = true;
        break;
      case GET_TOTAL_POINT_BY_DISPOSAL_DATE_SUCCESS:
        draft.totalPointByDisposalDate = action.totalPointByDisposalDate;
        draft.totalPointLoading = false;
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
      case GET_VALID_NHI_YEAR_MONTH_SUCCESS:
        draft.validNhiYearMonths = action.validNhiYearMonths;
        break;
      case GET_VALID_NHI_BY_YEAR_MONTH_SUCCESS:
        draft.validNhiData = action.validNhiData;
        break;
      case GET_NHI_ONE_BY_DISPOSAL_ID:
        draft.nhiOneLoading = true;
        break;
      case GET_NHI_ONE_BY_DISPOSAL_ID_SUCCESS:
        draft.nhiOne = action.nhiOne;
        draft.nhiOneLoading = false;
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
