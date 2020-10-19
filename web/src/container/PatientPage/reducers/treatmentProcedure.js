import produce from 'immer';
import { GET_RECENT_TREATMENT_PROCEDURE, GET_RECENT_TREATMENT_PROCEDURE_SUCCESS } from '../constant';

const initState = {
  loading: false,
  recentTreatmentProcedures: [],
};

/* eslint-disable default-case, no-param-reassign */
const treatmentProcedure = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_RECENT_TREATMENT_PROCEDURE:
        draft.loading = true;
        break;
      case GET_RECENT_TREATMENT_PROCEDURE_SUCCESS:
        draft.loading = false;
        draft.recentTreatmentProcedures = action.treatmentProcedures;
        break;
      default:
        break;
    }
  });

export default treatmentProcedure;
