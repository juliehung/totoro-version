import produce from 'immer';
import { LOGIN_SUCCESS, LOGIN_FAIL, CHANGE_LOGIN_SUCCESS, CHECK_TOKEN_VALIDATION_FAIL } from '../constant';

const initState = {
  id_token: undefined,
  loginSuccess: false,
  loginFail: false,
  tokenValidateChecked: false,
};

export const initialState = { ...initState };

/* eslint-disable default-case, no-param-reassign */
const login = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case LOGIN_SUCCESS:
        draft.id_token = action.authenticate.id_token;
        break;
      case LOGIN_FAIL:
        draft.loginFail = action.loginFail;
        break;
      case CHANGE_LOGIN_SUCCESS:
        draft.loginSuccess = true;
        break;
      case CHECK_TOKEN_VALIDATION_FAIL:
        draft.tokenValidateChecked = true;
        break;
      default:
        break;
    }
  });

export default login;
