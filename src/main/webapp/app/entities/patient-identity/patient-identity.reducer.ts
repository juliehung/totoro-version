import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPatientIdentity, defaultValue } from 'app/shared/model/patient-identity.model';

export const ACTION_TYPES = {
  FETCH_PATIENTIDENTITY_LIST: 'patientIdentity/FETCH_PATIENTIDENTITY_LIST',
  FETCH_PATIENTIDENTITY: 'patientIdentity/FETCH_PATIENTIDENTITY',
  CREATE_PATIENTIDENTITY: 'patientIdentity/CREATE_PATIENTIDENTITY',
  UPDATE_PATIENTIDENTITY: 'patientIdentity/UPDATE_PATIENTIDENTITY',
  DELETE_PATIENTIDENTITY: 'patientIdentity/DELETE_PATIENTIDENTITY',
  RESET: 'patientIdentity/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPatientIdentity>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PatientIdentityState = Readonly<typeof initialState>;

// Reducer

export default (state: PatientIdentityState = initialState, action): PatientIdentityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PATIENTIDENTITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PATIENTIDENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PATIENTIDENTITY):
    case REQUEST(ACTION_TYPES.UPDATE_PATIENTIDENTITY):
    case REQUEST(ACTION_TYPES.DELETE_PATIENTIDENTITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PATIENTIDENTITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PATIENTIDENTITY):
    case FAILURE(ACTION_TYPES.CREATE_PATIENTIDENTITY):
    case FAILURE(ACTION_TYPES.UPDATE_PATIENTIDENTITY):
    case FAILURE(ACTION_TYPES.DELETE_PATIENTIDENTITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PATIENTIDENTITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PATIENTIDENTITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PATIENTIDENTITY):
    case SUCCESS(ACTION_TYPES.UPDATE_PATIENTIDENTITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PATIENTIDENTITY):
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

const apiUrl = 'api/patient-identities';

// Actions

export const getEntities: ICrudGetAllAction<IPatientIdentity> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PATIENTIDENTITY_LIST,
  payload: axios.get<IPatientIdentity>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPatientIdentity> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PATIENTIDENTITY,
    payload: axios.get<IPatientIdentity>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPatientIdentity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PATIENTIDENTITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPatientIdentity> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PATIENTIDENTITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPatientIdentity> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PATIENTIDENTITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
