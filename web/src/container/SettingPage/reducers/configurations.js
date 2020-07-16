import produce from 'immer';
import {
  GET_CONFIG_START,
  GET_CONFIG_SUCCESS,
  SET_CONFIGS_SUCCESS,
  SET_CONFIGS_FAILURE,
  SET_CONFIGS,
  ON_LEAVE_PAGE,
} from '../constant';

const initState = {
  loading: false,
  putSuccess: false,
  putFailure: false,
  config: { xRayVendors: {}, vixwinPath: {}, linkManagement: {} },
};

/* eslint-disable default-case, no-param-reassign */
const configurations = (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_CONFIG_START:
        draft.loading = true;
        break;
      case GET_CONFIG_SUCCESS:
        draft.config.xRayVendors = action.config.xRayVendors;
        draft.config.vixwinPath = action.config.vixwinPath;
        draft.config.linkManagement = action.config.linkManagement;
        draft.loading = initState.loading;
        break;
      case SET_CONFIGS:
        draft.putSuccess = initState.putSuccess;
        draft.putFailure = initState.putFailure;
        break;
      case SET_CONFIGS_SUCCESS:
        draft.putSuccess = true;
        draft.putFailure = initState.putFailure;
        break;
      case SET_CONFIGS_FAILURE:
        draft.putSuccess = initState.putSuccess;
        draft.putFailure = true;
        break;
      case ON_LEAVE_PAGE:
        draft.putSuccess = initState.putSuccess;
        draft.loading = initState.loading;
        draft.putFailure = initState.putFailure;
        break;
      default:
        break;
    }
  });

export default configurations;
