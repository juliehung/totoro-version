import produce from 'immer';
import { GET_RESOURCE_COLOR_SUCCESS, CHANGE_RESOURCE_COLOR_START, CHANGE_RESOURCE_COLOR_SUCCESS } from '../constant';
import { parseColorConfigToColor } from '../utils/parseColorConfigToColor';

const initialState = {
  changeColorSuccess: false,
  color: {},
};

/* eslint-disable default-case, no-param-reassign */
const resourceColor = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case GET_RESOURCE_COLOR_SUCCESS:
        draft.color = parseColorConfigToColor(action.color);
        break;
      case CHANGE_RESOURCE_COLOR_START:
        draft.changeColorSuccess = initialState.changeColorSuccess;
        break;
      case CHANGE_RESOURCE_COLOR_SUCCESS:
        draft.changeColorSuccess = true;
        const { id, color } = action;
        draft.color = { ...state.color, [id]: color };
        break;
      default:
        break;
    }
  });

export default resourceColor;
