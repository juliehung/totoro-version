import moment from 'moment';
import { parseCalEvtToDayOffCron } from './parseCalEvtToDayOffCron';

export function handleCalendarEventForApi(calEvt) {
  let start, end;
  if (calEvt.allDay) {
    start = moment(calEvt.startDate).startOf('day').toISOString();
    end = moment(calEvt.endDate).endOf('day').toISOString();
  } else {
    start = moment(
      `${calEvt.startDate.format('YYYY-MM-DD')} ${calEvt.startTime.format('HH:mm')}`,
      'YYYY-MM-DD HH:mm',
    ).toISOString();
    end = moment(
      `${calEvt.endDate.format('YYYY-MM-DD')} ${calEvt.endTime.format('HH:mm')}`,
      'YYYY-MM-DD HH:mm',
    ).toISOString();
  }
  const note = calEvt.note ? calEvt.note : undefined;
  const id = calEvt.id;
  // doctor = { id: null} is a special way to delete doctor, instead of change id to null
  const doctor = calEvt.doctorId !== 'none' ? { id: calEvt.doctorId } : undefined;
  const timeType = 'WORK_TIME';

  if (calEvt.repeat === 'none') {
    return { start, end, note, doctor, timeType, id };
  } else {
    const repeatEnd = calEvt.repeatEndDate.endOf('d').toISOString();
    const duration = moment(end).diff(moment(start), 'm');
    const dayOffCron = parseCalEvtToDayOffCron(calEvt.repeat, start);

    return { start, end: repeatEnd, note, doctor, timeType, id, dayOffCron, duration };
  }
}
