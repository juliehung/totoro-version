import { set, get } from 'dot-prop';
import { shiftResourceColorConfigPrefix } from '../../../models/configuration';

export function parseColorConfigToColor(colorConfig) {
  let object = {};
  colorConfig.forEach(r => {
    set(object, r.configKey, r.configValue);
  });
  const parsedShiftConfig = get(object, shiftResourceColorConfigPrefix);
  return parsedShiftConfig.colorHex ? parsedShiftConfig.colorHex : {};
}
