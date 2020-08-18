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
} from '../constant';

const initState = {
  odIndexes: [
    // Od object
    // {
    //   did: '',
    //   totalPat: '',
    //   distinctTotalPat: '',
    //   totalTooth: '',
    //   totalSurface: '',
    //   surfaceToothRate: '',
    //   toothPeopleRate: '',
    //   surfacePeopleRate: ''
    // }
  ],
  doctorNhiExam: [
    // doctorNhiExam object
    // {
    //   did: '',
    //   nhiExamCode: '',
    //   nhiExamPoint: '',
    //   totalNumber: '',
    //   totalPoint: ''
    // }
  ],
  doctorNhiTx: [
    // doctorNhiTx object
    // {
    //   did: '',
    //   nhiTxCode: '',
    //   nhiTxName: '',
    //   nhiTxPoint: '',
    //   totalNumber: '',
    //   totalPoint: ''
    // }
  ],
  toothClean: [],
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
export default (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
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
      case GET_OD_INDEXES_FAIL:
      case GET_DOCTOR_NHI_EXAM_FAIL:
      case GET_TOOTH_CLEAN_FAIL:
        break;
      default:
        break;
    }
  });
