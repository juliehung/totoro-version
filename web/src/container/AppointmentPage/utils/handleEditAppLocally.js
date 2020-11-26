import moment from 'moment';
import { mapStatusToColor } from './convertAppsToEvt';

export function handleEditAppLocally(originApps, app) {
  const newEvent = convertEditAppToEvt(app);
  return [...originApps.filter(e => e.appointment.id !== newEvent.appointment.id), newEvent];
}

export function convertEditAppToEvt(appointment) {
  const start = moment(appointment.expectedArrivalTime).toDate();
  const requiredTreatmentTime =
    !appointment.requiredTreatmentTime || appointment.requiredTreatmentTime === 0
      ? 15
      : appointment.requiredTreatmentTime;
  const end = moment(appointment.expectedArrivalTime).add(requiredTreatmentTime, 'minutes').toDate();
  const registrationStatus = appointment.registration ? appointment.registration.status : undefined;
  return {
    title: appointment.status === 'CANCEL' ? `[C] ${appointment.patient.name}` : appointment.patient.name,
    resourceId: appointment.doctor.user.id,
    start,
    end,
    color: mapStatusToColor({
      registrationStatus,
      expectedArrivalTime: appointment.expectedArrivalTime,
      status: appointment.status,
      colorId: appointment.colorId,
    }),
    appointment: {
      ...appointment,
      birth: appointment.patient.birth,
      medicalId: appointment.patient.medicalId,
      patientName: appointment.patient.name,
      patientId: appointment.patient.id,
      phone: appointment.patient.phone,
      registrationStatus,
    },
    eventType: 'appointment',
  };
}
