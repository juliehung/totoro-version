import produce from 'immer';
import {
  CHANGE_CONFIRM_MODAL_VISIBLE,
  INSERT_PENDING_INFO,
  EDIT_APPOINTMENT_START,
  EDIT_APPOINTMENT_SUCCESS,
} from '../constant';

const initState = {
  appointmentId: undefined,
  visible: false,
  pendingInfo: {},
  oKLoading: false,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const drop = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_CONFIRM_MODAL_VISIBLE:
        draft.visible = action.visible;
        if (action.visible) {
          draft.oKLoading = false;
        }
        break;
      case INSERT_PENDING_INFO:
        draft.pendingInfo = action.info;
        break;
      case EDIT_APPOINTMENT_START:
        draft.oKLoading = true;
        break;
      case EDIT_APPOINTMENT_SUCCESS:
        draft.visible = false;
        break;
      default:
        break;
    }
  });

export default drop;
