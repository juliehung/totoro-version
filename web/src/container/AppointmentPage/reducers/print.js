import produce from 'immer';
import {
  CHANGE_PRINT_MODAL_VISIBLE,
  CHANGE_PRINT_DATE,
  CHANGE_PRINT_DOCTOR,
  GET_PRINT_APP_LIST_SUCCESS,
} from '../constant';
import moment from 'moment';
const initVisible = false;
const initDate = moment();
const initDoctor = [];
const initAppData = { appointmentList: [], doctorList: [] };

export const initialState = {
  visible: initVisible,
  date: initDate,
  doctor: initDoctor,
  appData: initAppData,
};

/* eslint-disable default-case, no-param-reassign */
const print = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_PRINT_MODAL_VISIBLE:
        draft.visible = !state.visible;
        break;
      case CHANGE_PRINT_DATE:
        draft.date = action.date;
        break;
      case CHANGE_PRINT_DOCTOR:
        draft.doctor = action.doctor;
        break;
      case GET_PRINT_APP_LIST_SUCCESS:
        draft.appData = action.appData;
        break;
      default:
        break;
    }
  });

export default print;
