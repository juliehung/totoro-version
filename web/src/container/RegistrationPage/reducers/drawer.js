import { ON_SELECT_PATIENT, CHANGE_DRAWER_VISIBLE } from '../constant';
import produce from 'immer';
// import moment from 'moment';

const initState = {
  visible: false,
  patient: { name: undefined, medicalId: undefined },
};

const initialState = { ...initState };

const drawer = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case ON_SELECT_PATIENT:
        draft.patient = action.patient;
        draft.visible = true;
        break;
      case CHANGE_DRAWER_VISIBLE:
        draft.visible = action.visible;
        break;
      default:
        break;
    }
  });

export default drawer;
