import moment from 'moment';
import { APPT_CUSTOM_COLORS } from '../constant';

export default function convertAppsToEvt(appointments) {
  return appointments.filter(a => a.registerArrivalTime !== a.expectedArrivalTime).map(a => convertAppToEvt(a));
}

export function mapStatusToColor(app) {
  const now = moment();

  if (app.registrationStatus) {
    if (app.registrationStatus === 'PENDING') {
      return '#009788 ';
    }
    if (app.registrationStatus === 'IN_PROGRESS') {
      return 'rgb(0, 151, 136)';
    }
    if (app.registrationStatus === 'FINISHED') {
      return 'rgb(0, 151, 136)';
    }
  }

  const isBefore = now.isBefore(moment(app.expectedArrivalTime));
  const status = app.status;

  if (status === 'CANCEL') {
    return '#616161';
  }

  const color = APPT_CUSTOM_COLORS.find(c => c.id === app.colorId);
  if (color) {
    return color.color;
  }

  if (isBefore) {
    return 'rgb(240, 147, 0)';
  }

  return 'rgb(240, 147, 0)';
}

export function convertAppToEvt(appointment) {
  const start = moment(appointment.expectedArrivalTime).toDate();
  const requiredTreatmentTime = appointment.requiredTreatmentTime;
  const end = moment(appointment.expectedArrivalTime).add(requiredTreatmentTime, 'minutes').toDate();
  const medicalId = appointment.medicalId;
  const status = appointment.status === 'CANCEL' ? '[C]' : '';
  const firstVisit = appointment.firstVisit ? '[N]' : '';

  return {
    title: `${status}${firstVisit}${appointment?.vipPatient ? `*${appointment.patientName}` : appointment.patientName}`,
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
