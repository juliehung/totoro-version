export function handleCountDocApp(appointmentEvts) {
  const doctorAppCount = {};
  appointmentEvts.forEach(appointmentEvt => {
    const doctorId = appointmentEvt.appointment.doctor.user.id;
    if (doctorAppCount[doctorId]) {
      doctorAppCount[doctorId]++;
    } else {
      doctorAppCount[doctorId] = 1;
    }
  });
  return doctorAppCount;
}
