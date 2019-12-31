import moment from 'moment';

export default function convertAppToEvt(appointments) {
  const now = moment();
  const doctorAppCount = {};
  if (appointments && appointments.length > 0) {
    return {
      appointment: appointments
        .filter(a => a.registerArrivalTime !== a.expectedArrivalTime)
        .map(appointment => {
          const start = moment(appointment.expectedArrivalTime).toDate();
          const requiredTreatmentTime =
            !appointment.requiredTreatmentTime || appointment.requiredTreatmentTime === 0
              ? 15
              : appointment.requiredTreatmentTime;
          const end = moment(appointment.expectedArrivalTime)
            .add(requiredTreatmentTime, 'minutes')
            .toDate();
          const doctorId = appointment.doctor.user.id;
          if (doctorAppCount[doctorId]) {
            doctorAppCount[doctorId]++;
          } else {
            doctorAppCount[doctorId] = 1;
          }

          return {
            title: appointment.status === 'CANCEL' ? `[C] ${appointment.patientName}` : appointment.patientName,
            resourceId: appointment.doctor.user.id,
            start,
            end,
            color: mapStatusToColor(appointment, now),
            appointment,
            eventType: 'appointment',
          };
        }),
      doctorAppCount,
    };
  }
  return { appointment: [], doctorAppCount: {} };
}

function mapStatusToColor(app, now) {
  if (app.registrationStatus) {
    if (app.registrationStatus === 'PENDING') {
      return '#91c5ff ';
    }
    if (app.registrationStatus === 'IN_PROGRESS') {
      return '#83d2b4';
    }
    if (app.registrationStatus === 'FINISHED') {
      return '#c2cbd5';
    }
  }

  const isBefore = now.isBefore(moment(app.expectedArrivalTime));
  const status = app.status;

  if (status === 'CANCEL') {
    return '#c2cbd5';
  }

  if (isBefore) {
    return '#fcb754';
  }

  return '#c2cbd5';
}
