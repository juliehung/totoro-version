import moment from 'moment';

export default function convertAppToPrintItem(appointments) {
  if (appointments && appointments.length > 0) {
    const doctorList = [];
    const appointmentList = appointments
      .filter(a => a.status !== 'CANCEL')
      .sort((a, b) => moment(a.expectedArrivalTime).diff(moment(b.expectedArrivalTime)))
      .map(p => {
        const key = p.id;
        const time = moment(p.expectedArrivalTime).format('HH:mm');
        const name = p.patientName;
        const mrn = p.medicalId;
        const birth = p.birth;
        const gender = p.gender;
        const phone = p.phone;
        const doctor = p.doctor.user.firstName;
        const note = p.note;
        if (!doctorList.includes(doctor)) {
          doctorList.push(doctor);
        }
        return { key, time, name, mrn, birth, gender, phone, doctor, note };
      });

    return { appointmentList, doctorList };
  }
  return { appointmentList: [], doctorList: [] };
}
