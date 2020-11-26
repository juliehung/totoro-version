import produce from 'immer';
import { GET_NHI_PATIENT_STATUS, GET_NHI_PATIENT_STATUS_SUCCESS } from '../constant';

const initState = {
  loading: false,
  nhiPatientFluorideStatus: undefined,
  nhiPatientScalingStatus: undefined,
};

/* eslint-disable default-case, no-param-reassign */
const nhiPatientStatus = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_NHI_PATIENT_STATUS:
        draft.loading = true;
        break;
      case GET_NHI_PATIENT_STATUS_SUCCESS:
        draft.loading = false;
        draft.nhiPatientFluorideStatus = action?.nhiPatientFluorideStatus;
        draft.nhiPatientScalingStatus = action?.nhiPatientScalingStatus;
        break;
      default:
        break;
    }
  });

export default nhiPatientStatus;
