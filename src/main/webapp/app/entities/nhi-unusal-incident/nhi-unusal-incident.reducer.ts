import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INHIUnusalIncident, defaultValue } from 'app/shared/model/nhi-unusal-incident.model';

export const ACTION_TYPES = {
  FETCH_NHIUNUSALINCIDENT_LIST: 'nHIUnusalIncident/FETCH_NHIUNUSALINCIDENT_LIST',
  FETCH_NHIUNUSALINCIDENT: 'nHIUnusalIncident/FETCH_NHIUNUSALINCIDENT',
  CREATE_NHIUNUSALINCIDENT: 'nHIUnusalIncident/CREATE_NHIUNUSALINCIDENT',
  UPDATE_NHIUNUSALINCIDENT: 'nHIUnusalIncident/UPDATE_NHIUNUSALINCIDENT',
  DELETE_NHIUNUSALINCIDENT: 'nHIUnusalIncident/DELETE_NHIUNUSALINCIDENT',
  RESET: 'nHIUnusalIncident/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INHIUnusalIncident>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NHIUnusalIncidentState = Readonly<typeof initialState>;

// Reducer

export default (state: NHIUnusalIncidentState = initialState, action): NHIUnusalIncidentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIUNUSALINCIDENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIUNUSALINCIDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIUNUSALINCIDENT):
    case REQUEST(ACTION_TYPES.UPDATE_NHIUNUSALINCIDENT):
    case REQUEST(ACTION_TYPES.DELETE_NHIUNUSALINCIDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIUNUSALINCIDENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIUNUSALINCIDENT):
    case FAILURE(ACTION_TYPES.CREATE_NHIUNUSALINCIDENT):
    case FAILURE(ACTION_TYPES.UPDATE_NHIUNUSALINCIDENT):
    case FAILURE(ACTION_TYPES.DELETE_NHIUNUSALINCIDENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIUNUSALINCIDENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIUNUSALINCIDENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIUNUSALINCIDENT):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIUNUSALINCIDENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIUNUSALINCIDENT):
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

const apiUrl = 'api/nhi-unusal-incidents';

// Actions

export const getEntities: ICrudGetAllAction<INHIUnusalIncident> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIUNUSALINCIDENT_LIST,
  payload: axios.get<INHIUnusalIncident>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INHIUnusalIncident> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIUNUSALINCIDENT,
    payload: axios.get<INHIUnusalIncident>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INHIUnusalIncident> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIUNUSALINCIDENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INHIUnusalIncident> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIUNUSALINCIDENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INHIUnusalIncident> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIUNUSALINCIDENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
