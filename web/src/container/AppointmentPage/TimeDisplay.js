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

  return <span>{currentTime.format('LLLL')}</span>;
}

export default TimeDisplay;
