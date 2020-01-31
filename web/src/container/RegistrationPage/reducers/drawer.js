import { ON_SELECT_PATIENT, CHANGE_DRAWER_VISIBLE, GET_DOC_SUCCESS } from '../constant';
import produce from 'immer';
import { convertDocsToItem } from '../utils/convertDocsToItem';

const initState = {
  visible: false,
  patient: { name: undefined, id: undefined },
  docs: [],
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
      case GET_DOC_SUCCESS:
        draft.docs = action.docs.map(d => convertDocsToItem(d));
        break;
      default:
        break;
    }
  });

export default drawer;
