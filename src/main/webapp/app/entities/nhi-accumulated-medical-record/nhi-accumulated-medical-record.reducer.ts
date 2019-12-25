import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiAccumulatedMedicalRecord, defaultValue } from 'app/shared/model/nhi-accumulated-medical-record.model';

export const ACTION_TYPES = {
  FETCH_NHIACCUMULATEDMEDICALRECORD_LIST: 'nhiAccumulatedMedicalRecord/FETCH_NHIACCUMULATEDMEDICALRECORD_LIST',
  FETCH_NHIACCUMULATEDMEDICALRECORD: 'nhiAccumulatedMedicalRecord/FETCH_NHIACCUMULATEDMEDICALRECORD',
  CREATE_NHIACCUMULATEDMEDICALRECORD: 'nhiAccumulatedMedicalRecord/CREATE_NHIACCUMULATEDMEDICALRECORD',
  UPDATE_NHIACCUMULATEDMEDICALRECORD: 'nhiAccumulatedMedicalRecord/UPDATE_NHIACCUMULATEDMEDICALRECORD',
  DELETE_NHIACCUMULATEDMEDICALRECORD: 'nhiAccumulatedMedicalRecord/DELETE_NHIACCUMULATEDMEDICALRECORD',
  RESET: 'nhiAccumulatedMedicalRecord/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiAccumulatedMedicalRecord>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type NhiAccumulatedMedicalRecordState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiAccumulatedMedicalRecordState = initialState, action): NhiAccumulatedMedicalRecordState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIACCUMULATEDMEDICALRECORD):
    case REQUEST(ACTION_TYPES.UPDATE_NHIACCUMULATEDMEDICALRECORD):
    case REQUEST(ACTION_TYPES.DELETE_NHIACCUMULATEDMEDICALRECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD):
    case FAILURE(ACTION_TYPES.CREATE_NHIACCUMULATEDMEDICALRECORD):
    case FAILURE(ACTION_TYPES.UPDATE_NHIACCUMULATEDMEDICALRECORD):
    case FAILURE(ACTION_TYPES.DELETE_NHIACCUMULATEDMEDICALRECORD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIACCUMULATEDMEDICALRECORD):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIACCUMULATEDMEDICALRECORD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIACCUMULATEDMEDICALRECORD):
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

const apiUrl = 'api/nhi-accumulated-medical-records';

// Actions

export const getEntities: ICrudGetAllAction<INhiAccumulatedMedicalRecord> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD_LIST,
    payload: axios.get<INhiAccumulatedMedicalRecord>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<INhiAccumulatedMedicalRecord> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIACCUMULATEDMEDICALRECORD,
    payload: axios.get<INhiAccumulatedMedicalRecord>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiAccumulatedMedicalRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIACCUMULATEDMEDICALRECORD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiAccumulatedMedicalRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIACCUMULATEDMEDICALRECORD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiAccumulatedMedicalRecord> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIACCUMULATEDMEDICALRECORD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
