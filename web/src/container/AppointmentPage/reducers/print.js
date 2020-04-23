import produce from 'immer';
import { CHANGE_PRINT_MODAL_VISIBLE, CHANGE_PRINT_DATE, GET_PRINT_APP_LIST_SUCCESS } from '../constant';
import moment from 'moment';

const initVisible = false;
const intiDate = moment();
const initPrintButtonDisable = true;
const initAppData = { appointmentList: [], doctorList: [] };

export const initialState = {
  visible: initVisible,
  date: intiDate,
  printButtonDisable: initPrintButtonDisable,
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
        draft.printButtonDisable = initPrintButtonDisable;
        break;
      case GET_PRINT_APP_LIST_SUCCESS:
        draft.printButtonDisable = false;
        draft.appData = action.appData;
        break;
      default:
        break;
    }
  });

export default print;
