import { set, get } from 'dot-prop';

export default function parseConfigToObject(config, prefix) {
  const initObject = {};
  config.forEach(r => {
    set(initObject, r.configKey, r.configValue);
  });
  const object = get(initObject, prefix) ?? {};
  return object;
}
