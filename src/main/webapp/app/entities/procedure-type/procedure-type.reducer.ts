import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProcedureType, defaultValue } from 'app/shared/model/procedure-type.model';

export const ACTION_TYPES = {
  FETCH_PROCEDURETYPE_LIST: 'procedureType/FETCH_PROCEDURETYPE_LIST',
  FETCH_PROCEDURETYPE: 'procedureType/FETCH_PROCEDURETYPE',
  CREATE_PROCEDURETYPE: 'procedureType/CREATE_PROCEDURETYPE',
  UPDATE_PROCEDURETYPE: 'procedureType/UPDATE_PROCEDURETYPE',
  DELETE_PROCEDURETYPE: 'procedureType/DELETE_PROCEDURETYPE',
  RESET: 'procedureType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProcedureType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProcedureTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: ProcedureTypeState = initialState, action): ProcedureTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROCEDURETYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROCEDURETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROCEDURETYPE):
    case REQUEST(ACTION_TYPES.UPDATE_PROCEDURETYPE):
    case REQUEST(ACTION_TYPES.DELETE_PROCEDURETYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROCEDURETYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROCEDURETYPE):
    case FAILURE(ACTION_TYPES.CREATE_PROCEDURETYPE):
    case FAILURE(ACTION_TYPES.UPDATE_PROCEDURETYPE):
    case FAILURE(ACTION_TYPES.DELETE_PROCEDURETYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCEDURETYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROCEDURETYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROCEDURETYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_PROCEDURETYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROCEDURETYPE):
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

const apiUrl = 'api/procedure-types';

// Actions

export const getEntities: ICrudGetAllAction<IProcedureType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROCEDURETYPE_LIST,
  payload: axios.get<IProcedureType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProcedureType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROCEDURETYPE,
    payload: axios.get<IProcedureType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProcedureType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROCEDURETYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProcedureType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROCEDURETYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProcedureType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROCEDURETYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
