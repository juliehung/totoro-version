import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INhiMedicalRecord, defaultValue } from 'app/shared/model/nhi-medical-record.model';

export const ACTION_TYPES = {
  FETCH_NHIMEDICALRECORD_LIST: 'nhiMedicalRecord/FETCH_NHIMEDICALRECORD_LIST',
  FETCH_NHIMEDICALRECORD: 'nhiMedicalRecord/FETCH_NHIMEDICALRECORD',
  CREATE_NHIMEDICALRECORD: 'nhiMedicalRecord/CREATE_NHIMEDICALRECORD',
  UPDATE_NHIMEDICALRECORD: 'nhiMedicalRecord/UPDATE_NHIMEDICALRECORD',
  DELETE_NHIMEDICALRECORD: 'nhiMedicalRecord/DELETE_NHIMEDICALRECORD',
  RESET: 'nhiMedicalRecord/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INhiMedicalRecord>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type NhiMedicalRecordState = Readonly<typeof initialState>;

// Reducer

export default (state: NhiMedicalRecordState = initialState, action): NhiMedicalRecordState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NHIMEDICALRECORD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NHIMEDICALRECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NHIMEDICALRECORD):
    case REQUEST(ACTION_TYPES.UPDATE_NHIMEDICALRECORD):
    case REQUEST(ACTION_TYPES.DELETE_NHIMEDICALRECORD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NHIMEDICALRECORD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NHIMEDICALRECORD):
    case FAILURE(ACTION_TYPES.CREATE_NHIMEDICALRECORD):
    case FAILURE(ACTION_TYPES.UPDATE_NHIMEDICALRECORD):
    case FAILURE(ACTION_TYPES.DELETE_NHIMEDICALRECORD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIMEDICALRECORD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NHIMEDICALRECORD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NHIMEDICALRECORD):
    case SUCCESS(ACTION_TYPES.UPDATE_NHIMEDICALRECORD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NHIMEDICALRECORD):
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

const apiUrl = 'api/nhi-medical-records';

// Actions

export const getEntities: ICrudGetAllAction<INhiMedicalRecord> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NHIMEDICALRECORD_LIST,
  payload: axios.get<INhiMedicalRecord>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<INhiMedicalRecord> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NHIMEDICALRECORD,
    payload: axios.get<INhiMedicalRecord>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INhiMedicalRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NHIMEDICALRECORD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INhiMedicalRecord> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NHIMEDICALRECORD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INhiMedicalRecord> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NHIMEDICALRECORD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
