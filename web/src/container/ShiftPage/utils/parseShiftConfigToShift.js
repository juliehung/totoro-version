import { set, get } from 'dot-prop';
import { defaultShiftConfigPrefix } from '../../../models/configuration';

export function parseShiftConfigToShift(shiftConfig) {
  const object = {};
  shiftConfig.forEach(r => {
    set(object, r.configKey, r.configValue);
  });
  const parsedShiftConfig = get(object, defaultShiftConfigPrefix);
  const defaultShift = Object.keys(parsedShiftConfig)
    .map(k => {
      const name = parsedShiftConfig[k].name;
      const time = parsedShiftConfig[k].time.split(' ');
      let range;
      if (time) {
        range = { start: time[0], end: time[1] };
      }
      return { origin: { id: k, name, range } };
    })
    .filter(ds => ds.origin.name && ds.origin.range && ds.origin.range.start && ds.origin.range.end);
  return defaultShift;
}
