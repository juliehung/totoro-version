import moment from 'moment';

export default function analysisAppointments(appointments = []) {
  const appointmentsClone = JSON.parse(JSON.stringify(appointments));

  const currentDate = moment();
  const sortedAppointments = appointmentsClone.sort((a, b) =>
    moment(a.expectedArrivalTime).diff(moment(b.expectedArrivalTime)),
  );

  const appointmentsAnalysis = {
    noShow: 0,
    cancel: 0,
    appointment: 0,
    registration: 0,
    recentAppointment: undefined,
    recentRegistration: undefined,
  };

  let overDate = false;

  sortedAppointments.forEach(a => {
    const expectedArrivalTime = moment(a.expectedArrivalTime);
    appointmentsAnalysis.appointment++;

    // recentAppointment
    if (!overDate) {
      appointmentsAnalysis.recentAppointment = expectedArrivalTime.format('YYYY-MM-DD');
      if (moment().isBefore(expectedArrivalTime)) {
        overDate = true;
      }
    }

    if (a.registration) {
      appointmentsAnalysis.recentRegistration = moment(a.registration.arrivalTime).format('YYYY-MM-DD');
      appointmentsAnalysis.lastDoctorId = a.doctor.id;
    }

    if (a.status === 'CANCEL') {
      appointmentsAnalysis.cancel++;
    } else if (!a.registration && expectedArrivalTime.isBefore(currentDate)) {
      appointmentsAnalysis.noShow++;
    }
  });

  if (!appointmentsAnalysis.recentAppointment) {
    appointmentsAnalysis.recentAppointment = '無';
  }

  if (!appointmentsAnalysis.recentRegistration) {
    appointmentsAnalysis.recentRegistration = '無';
  }

  return appointmentsAnalysis;
}
