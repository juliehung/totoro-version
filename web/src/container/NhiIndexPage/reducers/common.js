import produce from 'immer';
import { INIT_NHI_SALARY, GET_NHI_SALARY_SUCCESS, NHI_SALARY_NOT_FOUND } from '../constant';
const initState = {
  isNhiSalaryNotFound: false,
  loading: false,
};

/* eslint-disable default-case, no-param-reassign */
export default (state = initState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case INIT_NHI_SALARY:
        draft.isNhiSalaryNotFound = false;
        draft.loading = true;
        break;
      case NHI_SALARY_NOT_FOUND:
        draft.isNhiSalaryNotFound = true;
        break;
      case GET_NHI_SALARY_SUCCESS:
        draft.loading = false;
        break;
      default:
        break;
    }
  });
