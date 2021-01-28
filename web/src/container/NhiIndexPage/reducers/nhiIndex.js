import produce from 'immer';
import {
  GET_OD_INDEXES_SUCCESS,
  GET_OD_INDEXES_FAIL,
  GET_TOOTH_CLEAN_SUCCESS,
  GET_TOOTH_CLEAN_FAIL,
  GET_NHI_SALARY_SUCCESS,
  GET_DOCTOR_NHI_SALARY_SUCCESS,
  GET_TOTAL_POINT_BY_DISPOSAL_DATE,
  GET_TOTAL_POINT_BY_DISPOSAL_DATE_SUCCESS,
  GET_VALID_NHI_YEAR_MONTH_SUCCESS,
  GET_VALID_NHI_BY_YEAR_MONTH_SUCCESS,
  GET_NHI_ONE_BY_DISPOSAL_ID_SUCCESS,
  GET_NHI_ONE_BY_DISPOSAL_ID,
  GET_VALID_NHI_BY_YEAR_MONTH,
  GET_ENDO_INDEXES_SUCCESS,
  ON_CHECKED_MODAL_DATA_CHANGE,
  INIT_CHECKED_MODAL_DATA_SUCCESS,
} from '../constant';

const initState = {
  nhiSalary: undefined,
  expandNhiSalary: undefined,
  totalPointByDisposalDate: undefined,
  totalPointLoading: false,
  odIndexes: [],
  toothClean: [],
  endoIndexes: [],
  validNhiYearMonth: undefined,
  validNhiDataLoading: undefined,
  validNhiData: undefined,
  nhiOneLoading: undefined,
  nhiOne: undefined,
  checkedModalData: [],
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
        draft.expandNhiSalary = [].concat(state.expandNhiSalary ? state.expandNhiSalary : [], action.doctorOneSalary);
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
      case GET_TOOTH_CLEAN_SUCCESS:
        draft.toothClean = action.toothClean;
        break;
      case GET_ENDO_INDEXES_SUCCESS:
        draft.endoIndexes = action.endoIndexes;
        break;
      case GET_VALID_NHI_YEAR_MONTH_SUCCESS:
        draft.validNhiYearMonths = action.validNhiYearMonths;
        break;
      case GET_VALID_NHI_BY_YEAR_MONTH:
        draft.validNhiDataLoading = true;
        break;
      case GET_VALID_NHI_BY_YEAR_MONTH_SUCCESS:
        draft.validNhiData = action.validNhiData;
        draft.validNhiDataLoading = false;
        break;
      case GET_NHI_ONE_BY_DISPOSAL_ID:
        draft.nhiOneLoading = true;
        break;
      case GET_NHI_ONE_BY_DISPOSAL_ID_SUCCESS:
        draft.nhiOne = action.nhiOne;
        draft.nhiOneLoading = false;
        break;
      case GET_OD_INDEXES_FAIL:
      case GET_TOOTH_CLEAN_FAIL:
        break;
      case ON_CHECKED_MODAL_DATA_CHANGE:
      case INIT_CHECKED_MODAL_DATA_SUCCESS:
        draft.checkedModalData = action.checkedModalData;
        break;
      default:
        break;
    }
  });
