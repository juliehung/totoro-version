import moment from 'moment';
import { toRocString } from './';

export default function convertAppointmentToCardObject(appointments, users) {
  if (!appointments) return [];
  const currentDate = moment();
  return appointments
    .map(a => {
      let isCancel = false;
      let isRegistration = false;
      let isFuture = false;
      const { id, note, expectedArrivalTime, requiredTreatmentTime } = a;
      const doctor = users.find(u => u.id === a.doctor.id);
      const expectedArrivalTimeString = `${toRocString(expectedArrivalTime)} ${moment(expectedArrivalTime).format(
        ' HH:mm',
      )} ~ ${moment(expectedArrivalTime).add(requiredTreatmentTime, 'm').format(' HH:mm')}`;

      if (a?.registration) {
        isRegistration = true;
      } else if (a?.status === 'CANCEL') {
        isCancel = true;
      } else if (moment(expectedArrivalTime).isAfter(currentDate)) {
        isFuture = true;
      }

      return {
        id,
        note,
        expectedArrivalTime,
        expectedArrivalTimeString,
        requiredTreatmentTime,
        doctor,
        isRegistration,
        isCancel,
        isFuture,
      };
    })
    .sort((a, b) => moment(b.expectedArrivalTime) - moment(a.expectedArrivalTime));
}
