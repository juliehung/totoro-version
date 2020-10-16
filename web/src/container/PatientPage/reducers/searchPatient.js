import produce from 'immer';
import { SEARCH_PATIENT_SUCCESS } from '../constant';

const initState = {
  searchedText: undefined,
  total: [],
  name: [],
  phone: [],
  birth: [],
  mrn: [],
  nationalId: [],
};

/* eslint-disable default-case, no-param-reassign */
const searchPatient = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case SEARCH_PATIENT_SUCCESS:
        [draft.name, draft.birth, draft.phone, draft.mrn, draft.nationalId] = action.patients;
        draft.total = [...draft.name, ...draft.phone, ...draft.birth, ...draft.mrn, ...draft.nationalId].filter(
          (v, i, a) => a.findIndex(t => t.id === v.id) === i,
        );
        draft.searchedText = action.searchText;
        break;
      default:
        break;
    }
  });

export default searchPatient;
