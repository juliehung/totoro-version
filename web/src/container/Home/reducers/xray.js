import produce from 'immer';
import { CHANGE_XRAY_MODAL_VISIBLE, XRAY_GREETING, XRAY_GREETING_SUCCESS, XRAY_GREETING_FAILURE } from '../constant';

import { ON_LEAVE_PAGE as ON_LEAVE_PAGE_REGISTRATION_PAGE } from '../../RegistrationPage/constant';
import { ON_LEAVE_PAGE as ON_LEAVE_PAGE_APPOINTMENT_PAGE } from '../../AppointmentPage/constant';

const initialState = {
  modalVisible: false,
  onRequest: false,
  serverState: false,
  serverError: false,
};

/* eslint-disable default-case, no-param-reassign */
const xray = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_XRAY_MODAL_VISIBLE:
        draft.modalVisible = action.visible;
        break;
      case ON_LEAVE_PAGE_APPOINTMENT_PAGE:
      case ON_LEAVE_PAGE_REGISTRATION_PAGE:
        draft.onRequest = initialState.onRequest;
        draft.serverState = initialState.serverState;
        draft.serverError = initialState.serverError;
        break;
      case XRAY_GREETING:
        draft.onRequest = true;
        draft.serverState = initialState.serverState;
        draft.serverError = initialState.serverError;
        break;
      case XRAY_GREETING_SUCCESS:
        draft.onRequest = initialState.onRequest;
        draft.serverState = true;
        draft.serverError = false;
        break;
      case XRAY_GREETING_FAILURE:
        draft.onRequest = initialState.onRequest;
        draft.serverState = false;
        draft.serverError = true;
        break;
      default:
        break;
    }
  });

export default xray;
