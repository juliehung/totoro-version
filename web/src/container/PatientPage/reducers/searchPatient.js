import produce from 'immer';
import { SEARCH_PATIENT_SUCCESS } from '../constant';

const initState = {
  searchMode: 'birth',
  patients: [],
};

/* eslint-disable default-case, no-param-reassign */
const searchPatient = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case SEARCH_PATIENT_SUCCESS:
        draft.patients = action.patients;
        break;
      default:
        break;
    }
  });

export default searchPatient;
