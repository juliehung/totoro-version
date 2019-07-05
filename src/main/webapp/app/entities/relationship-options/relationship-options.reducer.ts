import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRelationshipOptions, defaultValue } from 'app/shared/model/relationship-options.model';

export const ACTION_TYPES = {
  FETCH_RELATIONSHIPOPTIONS_LIST: 'relationshipOptions/FETCH_RELATIONSHIPOPTIONS_LIST',
  FETCH_RELATIONSHIPOPTIONS: 'relationshipOptions/FETCH_RELATIONSHIPOPTIONS',
  CREATE_RELATIONSHIPOPTIONS: 'relationshipOptions/CREATE_RELATIONSHIPOPTIONS',
  UPDATE_RELATIONSHIPOPTIONS: 'relationshipOptions/UPDATE_RELATIONSHIPOPTIONS',
  DELETE_RELATIONSHIPOPTIONS: 'relationshipOptions/DELETE_RELATIONSHIPOPTIONS',
  RESET: 'relationshipOptions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRelationshipOptions>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RelationshipOptionsState = Readonly<typeof initialState>;

// Reducer

export default (state: RelationshipOptionsState = initialState, action): RelationshipOptionsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RELATIONSHIPOPTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_RELATIONSHIPOPTIONS):
    case REQUEST(ACTION_TYPES.DELETE_RELATIONSHIPOPTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS):
    case FAILURE(ACTION_TYPES.CREATE_RELATIONSHIPOPTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_RELATIONSHIPOPTIONS):
    case FAILURE(ACTION_TYPES.DELETE_RELATIONSHIPOPTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RELATIONSHIPOPTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_RELATIONSHIPOPTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RELATIONSHIPOPTIONS):
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

const apiUrl = 'api/relationship-options';

// Actions

export const getEntities: ICrudGetAllAction<IRelationshipOptions> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS_LIST,
  payload: axios.get<IRelationshipOptions>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRelationshipOptions> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RELATIONSHIPOPTIONS,
    payload: axios.get<IRelationshipOptions>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRelationshipOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RELATIONSHIPOPTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRelationshipOptions> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RELATIONSHIPOPTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRelationshipOptions> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RELATIONSHIPOPTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
