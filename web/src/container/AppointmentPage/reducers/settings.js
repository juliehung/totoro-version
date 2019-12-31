import produce from 'immer';
import { GET_SETTINGS_START, GET_SETTINGS_SUCCESS } from '../constant';

const initState = {
  settings: undefined,
  loading: false,
  generalSetting: undefined,
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
        draft.generalSetting = action.settings.preferences.generalSetting;
        draft.loading = initState.loading;
        break;
      default:
        break;
    }
  });

export default settings;
