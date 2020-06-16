import moment from 'moment';

export default function convertAppsToEvt(appointments) {
  return appointments.filter(a => a.registerArrivalTime !== a.expectedArrivalTime).map(a => convertAppToEvt(a));
}

export function mapStatusToColor(app) {
  const now = moment();

  if (app.registrationStatus) {
    if (app.registrationStatus === 'PENDING') {
      return '#0085fe ';
    }
    if (app.registrationStatus === 'IN_PROGRESS') {
      return '#00c49e';
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
    return '#fcb34c';
  }

  return '#c2cbd5';
}

export function convertAppToEvt(appointment) {
  const start = moment(appointment.expectedArrivalTime).toDate();
  const requiredTreatmentTime = appointment.requiredTreatmentTime;
  const end = moment(appointment.expectedArrivalTime).add(requiredTreatmentTime, 'minutes').toDate();
  const medicalId = appointment.medicalId;

  return {
    title: appointment.status === 'CANCEL' ? `[C] ${appointment.patientName}` : appointment.patientName,
    resourceId: appointment.doctor.user.id,
    start,
    end,
    color: mapStatusToColor(appointment),
    appointment,
    eventType: 'appointment',
    editable: appointment.registrationStatus ? false : true,
    resourceEditable: appointment.registrationStatus ? false : true,
    medicalId,
  };
}
