import { useCallback, useEffect } from 'react';

export const useDebouncedEffect = (effect, delay, deps) => {
  const callback = useCallback(effect, [effect]);

  useEffect(() => {
    const handler = setTimeout(() => {
      callback();
    }, delay);

    return () => {
      clearTimeout(handler);
    };
  }, [callback, delay, deps]);
};
