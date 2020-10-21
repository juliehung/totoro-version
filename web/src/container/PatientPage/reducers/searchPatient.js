import produce from 'immer';
import { SEARCH_PATIENT_SUCCESS, CHANGE_DRAWER_VISIBLE, GET_REGISTRATION_TODAY_SUCCESS } from '../constant';

const initState = {
  drawerOpen: false,
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
      case CHANGE_DRAWER_VISIBLE:
        draft.drawerOpen = action.visible;
        if (!action.visible) {
          draft.total = [];
          draft.name = [];
          draft.phone = [];
          draft.birth = [];
          draft.mrn = [];
          draft.nationalId = [];
        }
        break;
      case SEARCH_PATIENT_SUCCESS:
        [draft.name, draft.birth, draft.phone, draft.mrn, draft.nationalId] = action.patients;
        draft.total = [...draft.name, ...draft.phone, ...draft.birth, ...draft.mrn, ...draft.nationalId].filter(
          (v, i, a) => a.findIndex(t => t.id === v.id) === i,
        );
        draft.searchedText = action.searchText;
        break;
      case GET_REGISTRATION_TODAY_SUCCESS:
        draft.total = action.registrations.map(r => {
          const { patient } = r;
          return patient;
        });
        draft.name = [];
        draft.phone = [];
        draft.birth = [];
        draft.mrn = [];
        draft.nationalId = [];

        break;
      default:
        break;
    }
  });

export default searchPatient;
