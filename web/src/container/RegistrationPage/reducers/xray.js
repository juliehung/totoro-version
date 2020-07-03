import produce from 'immer';
import { XRAY_GREETING, XRAY_GREETING_SUCCESS, XRAY_GREETING_FAILURE, ON_LEAVE_PAGE } from '../constant';

const initialState = {
  onRequest: false,
  serverState: false,
  serverError: false,
};

/* eslint-disable default-case, no-param-reassign */
const xray = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case ON_LEAVE_PAGE:
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
