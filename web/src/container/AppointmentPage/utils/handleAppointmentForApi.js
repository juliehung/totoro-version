import moment from 'moment';

export function handleAppointmentForApi(appointment) {
  return {
    patient: { id: appointment.patientId },
    doctor: { id: appointment.doctorId },
    expectedArrivalTime: moment(
      appointment.expectedArrivalDate.format('YYYY-MM-DD') + ' ' + appointment.expectedArrivalTime.format('HH:mm'),
      'YYYY-MM-DD HH:mm',
    ).toISOString(),
    requiredTreatmentTime: appointment.duration,
    note: appointment.note,
    microscope: appointment.specialNote.includes('micro'),
    baseFloor: appointment.specialNote.includes('baseFloor'),
    firstVisit: appointment.specialNote.includes('firstVisit'),
    status: 'CONFIRMED',
    colorId: appointment.colorId,
  };
}
