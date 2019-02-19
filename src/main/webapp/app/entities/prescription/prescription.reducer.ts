import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPrescription, defaultValue } from 'app/shared/model/prescription.model';

export const ACTION_TYPES = {
  FETCH_PRESCRIPTION_LIST: 'prescription/FETCH_PRESCRIPTION_LIST',
  FETCH_PRESCRIPTION: 'prescription/FETCH_PRESCRIPTION',
  CREATE_PRESCRIPTION: 'prescription/CREATE_PRESCRIPTION',
  UPDATE_PRESCRIPTION: 'prescription/UPDATE_PRESCRIPTION',
  DELETE_PRESCRIPTION: 'prescription/DELETE_PRESCRIPTION',
  RESET: 'prescription/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPrescription>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type PrescriptionState = Readonly<typeof initialState>;

// Reducer

export default (state: PrescriptionState = initialState, action): PrescriptionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRESCRIPTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRESCRIPTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRESCRIPTION):
    case REQUEST(ACTION_TYPES.UPDATE_PRESCRIPTION):
    case REQUEST(ACTION_TYPES.DELETE_PRESCRIPTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PRESCRIPTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRESCRIPTION):
    case FAILURE(ACTION_TYPES.CREATE_PRESCRIPTION):
    case FAILURE(ACTION_TYPES.UPDATE_PRESCRIPTION):
    case FAILURE(ACTION_TYPES.DELETE_PRESCRIPTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESCRIPTION_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRESCRIPTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRESCRIPTION):
    case SUCCESS(ACTION_TYPES.UPDATE_PRESCRIPTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRESCRIPTION):
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

const apiUrl = 'api/prescriptions';

// Actions

export const getEntities: ICrudGetAllAction<IPrescription> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PRESCRIPTION_LIST,
    payload: axios.get<IPrescription>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IPrescription> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRESCRIPTION,
    payload: axios.get<IPrescription>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPrescription> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRESCRIPTION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPrescription> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRESCRIPTION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPrescription> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRESCRIPTION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
