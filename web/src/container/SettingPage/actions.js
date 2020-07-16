import {
  SET_CONFIGS,
  SET_CONFIGS_SUCCESS,
  GET_CONFIG_START,
  GET_CONFIG_SUCCESS,
  ON_LEAVE_PAGE,
  SET_CONFIGS_FAILURE,
} from './constant';

export function getConfig() {
  return { type: GET_CONFIG_START };
}

export function getConfigSuccess(config) {
  return { type: GET_CONFIG_SUCCESS, config };
}

export const setConfigs = ({ update, create }) => {
  return { type: SET_CONFIGS, configs: { update, create } };
};

export const setConfigSuccess = () => {
  return { type: SET_CONFIGS_SUCCESS };
};

export const setConfigsFailure = () => {
  return { type: SET_CONFIGS_FAILURE };
};

export const onLeavePage = () => {
  return { type: ON_LEAVE_PAGE };
};
