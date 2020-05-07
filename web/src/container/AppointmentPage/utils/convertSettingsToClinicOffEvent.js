import moment from 'moment';

export function convertSettingsToClinicOffEvent(generalSetting, range) {
  try {
    if (!generalSetting || !range.start || !range.end) {
      return [];
    } else {
      const {
        mon,
        tue,
        wed,
        thu,
        fri,
        sat,
        sun,
        morningStartTime,
        morningEndTime,
        noonStartTime,
        noonEndTime,
        eveningStartTime,
        eveningEndTime,
        nightStartTime,
        nightEndTime,
      } = generalSetting;

      const timeSetting = {
        morningStartTime,
        morningEndTime,
        noonStartTime,
        noonEndTime,
        eveningStartTime,
        eveningEndTime,
        nightStartTime,
        nightEndTime,
      };

      let backgroundEvent = [];
      for (let date = moment(range.start); date.isBefore(range.end); date.add(1, 'days')) {
        const dayOfWeek = date.day();
        switch (dayOfWeek) {
          case 0:
            backgroundEvent = [...backgroundEvent, ...handleGenerateBackgroundEvent(date, sun, timeSetting)];
            break;
          case 1:
            backgroundEvent = [...backgroundEvent, ...handleGenerateBackgroundEvent(date, mon, timeSetting)];
            break;
          case 2:
            backgroundEvent = [...backgroundEvent, ...handleGenerateBackgroundEvent(date, tue, timeSetting)];
            break;
          case 3:
            backgroundEvent = [...backgroundEvent, ...handleGenerateBackgroundEvent(date, wed, timeSetting)];
            break;
          case 4:
            backgroundEvent = [...backgroundEvent, ...handleGenerateBackgroundEvent(date, thu, timeSetting)];
            break;
          case 5:
            backgroundEvent = [...backgroundEvent, ...handleGenerateBackgroundEvent(date, fri, timeSetting)];
            break;
          case 6:
            backgroundEvent = [...backgroundEvent, ...handleGenerateBackgroundEvent(date, sat, timeSetting)];
            break;
          default:
            break;
        }
      }
      return backgroundEvent;
    }
  } catch (error) {
    console.log(error);
    return [];
  }
}

function handleGenerateBackgroundEvent(date, daySetting, timeSetting) {
  if (!daySetting.activated) {
    return [generateBackgroundEvent(true, date)];
  } else {
    return [...calculateBackgroundEvent(date, daySetting, timeSetting)];
  }
}

function calculateBackgroundEvent(date, daySetting, timeSetting) {
  let backgroundEvent = [];
  const {
    morningStartTime,
    morningEndTime,
    noonStartTime,
    noonEndTime,
    eveningStartTime,
    eveningEndTime,
  } = timeSetting;
  const { morningShift, noonShift, eveningShift } = daySetting;
  const dateString = date.format('YYYY-MM-DD');
  let startTime = moment(date).startOf('d');
  let endTime;

  if (morningShift) {
    endTime = moment(`${dateString} ${morningStartTime}`);
    backgroundEvent.push(generateBackgroundEvent(false, startTime, endTime));
    startTime = moment(`${dateString} ${morningEndTime}`);
  }

  if (noonShift) {
    endTime = moment(`${dateString} ${noonStartTime}`);
    backgroundEvent.push(generateBackgroundEvent(false, startTime, endTime));
    startTime = moment(`${dateString} ${noonEndTime}`);
  }

  if (eveningShift) {
    endTime = moment(`${dateString} ${eveningStartTime}`);
    backgroundEvent.push(generateBackgroundEvent(false, startTime, endTime));
    startTime = moment(`${dateString} ${eveningEndTime}`);
  }

  endTime = moment(date).endOf('d');
  backgroundEvent.push(generateBackgroundEvent(false, startTime, endTime));

  return backgroundEvent;
}

function generateBackgroundEvent(allDay, start, end) {
  if (allDay) {
    return {
      start: start.format('YYYY-MM-DD'),
      allDay: true,
      rendering: 'background',
      color: 'rgba(143,155,179,0.24)',
      eventType: 'clinicOff',
    };
  }
  return {
    start: start.format('YYYY-MM-DD HH:mm'),
    end: end.format('YYYY-MM-DD HH:mm'),
    rendering: 'background',
    color: 'rgba(143,155,179,0.24)',
    eventType: 'clinicOff',
  };
}
