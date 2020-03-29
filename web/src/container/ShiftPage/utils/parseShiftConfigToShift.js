import { set, get } from 'dot-prop';
import { defaultShiftConfigPrefix } from '../../../models/configuration';

export function parseShiftConfigToShift(shiftConfig) {
  let object = {};
  shiftConfig.forEach(r => {
    set(object, r.configKey, r.configValue);
  });
  const parsedShiftConfig = get(object, defaultShiftConfigPrefix);
  const defaultShift = Object.keys(parsedShiftConfig).map(k => {
    const name = parsedShiftConfig[k].name;
    const time = parsedShiftConfig[k].time.split(' ');
    const range = { start: time[0], end: time[1] };
    return { origin: { id: k, name, range } };
  });
  return defaultShift;
}
