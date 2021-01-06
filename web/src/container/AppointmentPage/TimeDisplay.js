import React, { useState, useEffect, useRef } from 'react';
import moment from 'moment';

function TimeDisplay() {
  const [currentTime, setCurrentTime] = useState(moment());
  const timeout = useRef(undefined);

  useEffect(() => {
    timeout.current = setTimeout(() => {
      setCurrentTime(moment());
    }, 1000);
    return () => {
      clearTimeout(timeout.current);
    };
  }, [setCurrentTime, currentTime]);

  const year = currentTime.year() - 1911;
  const month = currentTime.month();
  const date = currentTime.date();
  const day = currentTime.format('dd');
  const time = currentTime.format('LT');

  return <span>{`${year}年${month + 1}月${date}日 週${day} ${time}`}</span>;
}

export default TimeDisplay;
