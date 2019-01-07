import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAccounting, defaultValue } from 'app/shared/model/accounting.model';

export const ACTION_TYPES = {
  FETCH_ACCOUNTING_LIST: 'accounting/FETCH_ACCOUNTING_LIST',
  FETCH_ACCOUNTING: 'accounting/FETCH_ACCOUNTING',
  CREATE_ACCOUNTING: 'accounting/CREATE_ACCOUNTING',
  UPDATE_ACCOUNTING: 'accounting/UPDATE_ACCOUNTING',
  DELETE_ACCOUNTING: 'accounting/DELETE_ACCOUNTING',
  RESET: 'accounting/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAccounting>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AccountingState = Readonly<typeof initialState>;

// Reducer

export default (state: AccountingState = initialState, action): AccountingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACCOUNTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ACCOUNTING):
    case REQUEST(ACTION_TYPES.UPDATE_ACCOUNTING):
    case REQUEST(ACTION_TYPES.DELETE_ACCOUNTING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACCOUNTING):
    case FAILURE(ACTION_TYPES.CREATE_ACCOUNTING):
    case FAILURE(ACTION_TYPES.UPDATE_ACCOUNTING):
    case FAILURE(ACTION_TYPES.DELETE_ACCOUNTING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTING_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACCOUNTING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACCOUNTING):
    case SUCCESS(ACTION_TYPES.UPDATE_ACCOUNTING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACCOUNTING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/accountings';

// Actions

export const getEntities: ICrudGetAllAction<IAccounting> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTING_LIST,
    payload: axios.get<IAccounting>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAccounting> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACCOUNTING,
    payload: axios.get<IAccounting>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAccounting> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACCOUNTING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAccounting> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACCOUNTING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAccounting> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACCOUNTING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
