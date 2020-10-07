import produce from 'immer';
import { GET_APPOINTMENT, GET_APPOINTMENT_SUCCESS, CHANGE_APPOINTMENT_LIST_MODAL_VISIBLE } from '../constant';

const initState = {
  loading: false,
  appointmentListModalVisible: false,
  appointment: [],
};

/* eslint-disable default-case, no-param-reassign */
const appointment = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_APPOINTMENT:
        draft.loading = true;
        break;
      case GET_APPOINTMENT_SUCCESS:
        draft.loading = false;
        draft.appointment = action.appointment;
        break;
      case CHANGE_APPOINTMENT_LIST_MODAL_VISIBLE:
        draft.appointmentListModalVisible = action.visible;
        break;
      default:
        break;
    }
  });

export default appointment;
