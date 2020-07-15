import parseConfigToObject from '../../../utils/parseConfigToObject';
import { xRayVendorPrefix } from '../../../models/configuration';

export function parseXrayVendorConfigtoObject(defaultShiftConfig) {
  const parsedXrayVendorConfig = parseConfigToObject(defaultShiftConfig, xRayVendorPrefix);
  return parsedXrayVendorConfig;
}
