import produce from 'immer';
import { GET_CONFIG_START, GET_CONFIG_SUCCESS, SET_XRAY_VENDOR_SUCCESS, ON_LEAVE_PAGE } from '../constant';

const initState = {
  loading: false,
  putSuccess: false,
  config: { xRayVendors: [] },
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
        draft.loading = initState.loading;
        break;
      case SET_XRAY_VENDOR_SUCCESS:
        draft.putSuccess = true;
        break;
      case ON_LEAVE_PAGE:
        draft.putSuccess = initState.putSuccess;
        draft.loading = initState.loading;
        break;
      default:
        break;
    }
  });

export default configurations;
