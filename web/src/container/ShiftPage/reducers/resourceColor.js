import produce from 'immer';
import { CHANGE_RESOURCE_COLOR_START, CHANGE_RESOURCE_COLOR_SUCCESS } from '../constant';

const initialState = {
  changeColorSuccess: false,
};

/* eslint-disable default-case, no-param-reassign */
const resourceColor = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case CHANGE_RESOURCE_COLOR_START:
        draft.changeColorSuccess = initialState.changeColorSuccess;
        break;
      case CHANGE_RESOURCE_COLOR_SUCCESS:
        draft.changeColorSuccess = true;
        break;
      default:
        break;
    }
  });

export default resourceColor;
