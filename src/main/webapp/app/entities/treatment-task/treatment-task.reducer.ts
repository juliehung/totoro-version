import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITreatmentTask, defaultValue } from 'app/shared/model/treatment-task.model';

export const ACTION_TYPES = {
  FETCH_TREATMENTTASK_LIST: 'treatmentTask/FETCH_TREATMENTTASK_LIST',
  FETCH_TREATMENTTASK: 'treatmentTask/FETCH_TREATMENTTASK',
  CREATE_TREATMENTTASK: 'treatmentTask/CREATE_TREATMENTTASK',
  UPDATE_TREATMENTTASK: 'treatmentTask/UPDATE_TREATMENTTASK',
  DELETE_TREATMENTTASK: 'treatmentTask/DELETE_TREATMENTTASK',
  RESET: 'treatmentTask/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITreatmentTask>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TreatmentTaskState = Readonly<typeof initialState>;

// Reducer

export default (state: TreatmentTaskState = initialState, action): TreatmentTaskState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTTASK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TREATMENTTASK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TREATMENTTASK):
    case REQUEST(ACTION_TYPES.UPDATE_TREATMENTTASK):
    case REQUEST(ACTION_TYPES.DELETE_TREATMENTTASK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTTASK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TREATMENTTASK):
    case FAILURE(ACTION_TYPES.CREATE_TREATMENTTASK):
    case FAILURE(ACTION_TYPES.UPDATE_TREATMENTTASK):
    case FAILURE(ACTION_TYPES.DELETE_TREATMENTTASK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTTASK_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TREATMENTTASK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TREATMENTTASK):
    case SUCCESS(ACTION_TYPES.UPDATE_TREATMENTTASK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TREATMENTTASK):
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

const apiUrl = 'api/treatment-tasks';

// Actions

export const getEntities: ICrudGetAllAction<ITreatmentTask> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTTASK_LIST,
    payload: axios.get<ITreatmentTask>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITreatmentTask> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TREATMENTTASK,
    payload: axios.get<ITreatmentTask>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITreatmentTask> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TREATMENTTASK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITreatmentTask> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TREATMENTTASK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITreatmentTask> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TREATMENTTASK,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
