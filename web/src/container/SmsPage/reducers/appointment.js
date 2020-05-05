import { GET_APPOINTMENTS_SUCCESS, TOGGLE_APPOINTMENT_MODAL } from '../constant';
import produce from 'immer';

const initState = {
  appointments: [],
  visible: false,
};

const initialState = {
  ...initState,
};

const appointment = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case TOGGLE_APPOINTMENT_MODAL:
        draft.visible = !state.visible;
        break;
      case GET_APPOINTMENTS_SUCCESS:
        draft.appointments = action.appointments;
        break;
      default:
        break;
    }
  });

export default appointment;
