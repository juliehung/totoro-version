import moment from 'moment';
import { toRocString } from './';

export default function convertAppointmentToCardObject(appointments, users) {
  if (!appointments) return [];
  const currentDate = moment();
  return appointments.map(a => {
    let isCancel = false;
    let isFuture = false;
    let isRegistration = false;
    const { id, note, expectedArrivalTime, requiredTreatmentTime } = a;
    const doctor = users.find(u => u.id === a.doctor.id);
    const expectedArrivalTimeString = toRocString(expectedArrivalTime) + moment(expectedArrivalTime).format(' HH:mm');

    if (a.status === 'CANCEL') {
      isCancel = true;
    }
    if (a.registration) {
      isRegistration = true;
    }
    if (moment(expectedArrivalTime).isAfter(currentDate)) {
      isFuture = true;
    }

    return {
      id,
      note,
      expectedArrivalTime: expectedArrivalTimeString,
      requiredTreatmentTime,
      doctor,
      isCancel,
      isFuture,
      isRegistration,
    };
  });
}
