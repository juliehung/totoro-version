import {
  SET_XRAY_VENDOR,
  SET_XRAY_VENDOR_SUCCESS,
  GET_CONFIG_START,
  GET_CONFIG_SUCCESS,
  ON_LEAVE_PAGE,
} from './constant';

export function getConfig() {
  return { type: GET_CONFIG_START };
}

export function getConfigSuccess(config) {
  return { type: GET_CONFIG_SUCCESS, config };
}

export const setXrayVendor = ({ vendor, value }) => {
  return { type: SET_XRAY_VENDOR, vendor, value };
};

export const setXrayVendorSuccess = () => {
  return { type: SET_XRAY_VENDOR_SUCCESS };
};

export const onLeavePage = () => {
  return { type: ON_LEAVE_PAGE };
};
