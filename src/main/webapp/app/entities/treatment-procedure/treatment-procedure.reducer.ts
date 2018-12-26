import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITreatmentProcedure, defaultValue } from 'app/shared/model/treatment-procedure.model';

export const ACTION_TYPES = {
  FETCH_TREATMENTPROCEDURE_LIST: 'treatmentProcedure/FETCH_TREATMENTPROCEDURE_LIST',
  FETCH_TREATMENTPROCEDURE: 'treatmentProcedure/FETCH_TREATMENTPROCEDURE',
  CREATE_TREATMENTPROCEDURE: 'treatmentProcedure/CREATE_TREATMENTPROCEDURE',
  UPDATE_TREATMENTPROCEDURE: 'treatmentProcedure/UPDATE_TREATMENTPROCEDURE',
  DELETE_TREATMENTPROCEDURE: 'treatmentProcedure/DELETE_TREATMENTPROCEDURE',
  RESET: 'treatmentProcedure/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITreatmentProcedure>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TreatmentProcedureState = Readonly<typeof initialState>;

// Reducer

export default (state: TreatmentProcedureState = initialState, action): TreatmentProcedureState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTPROCEDURE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTPROCEDURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TREATMENTPROCEDURE):
    case REQUEST(ACTION_TYPES.UPDATE_TREATMENTPROCEDURE):
    case REQUEST(ACTION_TYPES.DELETE_TREATMENTPROCEDURE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTPROCEDURE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTPROCEDURE):
    case FAILURE(ACTION_TYPES.CREATE_TREATMENTPROCEDURE):
    case FAILURE(ACTION_TYPES.UPDATE_TREATMENTPROCEDURE):
    case FAILURE(ACTION_TYPES.DELETE_TREATMENTPROCEDURE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTPROCEDURE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTPROCEDURE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TREATMENTPROCEDURE):
    case SUCCESS(ACTION_TYPES.UPDATE_TREATMENTPROCEDURE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TREATMENTPROCEDURE):
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

const apiUrl = 'api/treatment-procedures';

// Actions

export const getEntities: ICrudGetAllAction<ITreatmentProcedure> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTPROCEDURE_LIST,
    payload: axios.get<ITreatmentProcedure>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITreatmentProcedure> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTPROCEDURE,
    payload: axios.get<ITreatmentProcedure>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITreatmentProcedure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TREATMENTPROCEDURE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITreatmentProcedure> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TREATMENTPROCEDURE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITreatmentProcedure> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TREATMENTPROCEDURE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
