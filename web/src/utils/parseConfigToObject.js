import { set, get } from 'dot-prop';

export default function parseConfigToObject(config, prefix) {
  const initObject = {};
  config.forEach(r => {
    console.log('r', r);
    set(initObject, r.configKey, r.configValue);
  });
  console.log('initObject', initObject);
  const object = get(initObject, prefix) ?? {};
  console.log('object', object);
  return object;
}
