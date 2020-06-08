import produce from 'immer';
import { XRAY_GREETING, XRAY_GREETING_SUCCESS, XRAY_GREETING_FAILURE, ON_LEAVE_PAGE } from '../constant';

const initialState = {
  serverState: false,
  serverError: false,
};

/* eslint-disable default-case, no-param-reassign */
const xray = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case ON_LEAVE_PAGE:
      case XRAY_GREETING:
        draft.serverState = false;
        draft.serverError = false;
        break;
      case XRAY_GREETING_SUCCESS:
        draft.serverState = true;
        draft.serverError = false;
        break;
      case XRAY_GREETING_FAILURE:
        draft.serverState = false;
        draft.serverError = true;
        break;
      default:
        break;
    }
  });

export default xray;
