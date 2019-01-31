import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITreatment, defaultValue } from 'app/shared/model/treatment.model';

export const ACTION_TYPES = {
  FETCH_TREATMENT_LIST: 'treatment/FETCH_TREATMENT_LIST',
  FETCH_TREATMENT: 'treatment/FETCH_TREATMENT',
  CREATE_TREATMENT: 'treatment/CREATE_TREATMENT',
  UPDATE_TREATMENT: 'treatment/UPDATE_TREATMENT',
  DELETE_TREATMENT: 'treatment/DELETE_TREATMENT',
  RESET: 'treatment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITreatment>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TreatmentState = Readonly<typeof initialState>;

// Reducer

export default (state: TreatmentState = initialState, action): TreatmentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TREATMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TREATMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TREATMENT):
    case REQUEST(ACTION_TYPES.UPDATE_TREATMENT):
    case REQUEST(ACTION_TYPES.DELETE_TREATMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TREATMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TREATMENT):
    case FAILURE(ACTION_TYPES.CREATE_TREATMENT):
    case FAILURE(ACTION_TYPES.UPDATE_TREATMENT):
    case FAILURE(ACTION_TYPES.DELETE_TREATMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TREATMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_TREATMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TREATMENT):
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

const apiUrl = 'api/treatments';

// Actions

export const getEntities: ICrudGetAllAction<ITreatment> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENT_LIST,
    payload: axios.get<ITreatment>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITreatment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENT,
    payload: axios.get<ITreatment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITreatment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TREATMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITreatment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TREATMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITreatment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TREATMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
