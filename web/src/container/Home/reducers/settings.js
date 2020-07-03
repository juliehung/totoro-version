import produce from 'immer';
import { GET_SETTINGS_START, GET_SETTINGS_SUCCESS, PUT_SETTINGS_START, PUT_SETTINGS_SUCCESS } from '../constant';

const initState = {
  settings: undefined,
  loading: false,
  putSuccess: false,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const settings = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_SETTINGS_START:
        draft.loading = true;
        break;
      case GET_SETTINGS_SUCCESS:
        draft.settings = action.settings;
        draft.loading = initState.loading;
        break;
      case PUT_SETTINGS_START:
        draft.putSuccess = initState.putSuccess;
        break;
      case PUT_SETTINGS_SUCCESS:
        draft.putSuccess = true;
        draft.settings = action.settings;
        break;
      default:
        break;
    }
  });

export default settings;
