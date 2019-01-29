import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILedger, defaultValue } from 'app/shared/model/ledger.model';

export const ACTION_TYPES = {
  FETCH_LEDGER_LIST: 'ledger/FETCH_LEDGER_LIST',
  FETCH_LEDGER: 'ledger/FETCH_LEDGER',
  CREATE_LEDGER: 'ledger/CREATE_LEDGER',
  UPDATE_LEDGER: 'ledger/UPDATE_LEDGER',
  DELETE_LEDGER: 'ledger/DELETE_LEDGER',
  RESET: 'ledger/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILedger>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LedgerState = Readonly<typeof initialState>;

// Reducer

export default (state: LedgerState = initialState, action): LedgerState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LEDGER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LEDGER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LEDGER):
    case REQUEST(ACTION_TYPES.UPDATE_LEDGER):
    case REQUEST(ACTION_TYPES.DELETE_LEDGER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LEDGER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LEDGER):
    case FAILURE(ACTION_TYPES.CREATE_LEDGER):
    case FAILURE(ACTION_TYPES.UPDATE_LEDGER):
    case FAILURE(ACTION_TYPES.DELETE_LEDGER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEDGER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEDGER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LEDGER):
    case SUCCESS(ACTION_TYPES.UPDATE_LEDGER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LEDGER):
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

const apiUrl = 'api/ledgers';

// Actions

export const getEntities: ICrudGetAllAction<ILedger> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LEDGER_LIST,
    payload: axios.get<ILedger>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILedger> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LEDGER,
    payload: axios.get<ILedger>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILedger> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LEDGER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILedger> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LEDGER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILedger> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LEDGER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
