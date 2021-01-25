import moment from 'moment';
import { parsePatientNameWithVipMark } from '../../../utils/patientHelper';

const findNotWebAppt = appt => {
  const expectTime = moment(appt.expectedArrivalTime).format('YYYY-MM-DD hh:mm:ss');
  const arrivalTime = moment(appt.registerArrivalTime).format('YYYY-MM-DD hh:mm:ss');
  if (expectTime !== arrivalTime) {
    return appt;
  }
};

export default function convertAppToPrintItem(appointments) {
  if (appointments && appointments.length > 0) {
    const doctorList = [];
    const appointmentList = appointments
      .filter(a => a.status !== 'CANCEL')
      .filter(f => findNotWebAppt(f))
      .sort((a, b) => moment(a.expectedArrivalTime).diff(moment(b.expectedArrivalTime)))
      .map(p => {
        const key = p.id;
        const time = moment(p.expectedArrivalTime).format('HH:mm');
        const name = parsePatientNameWithVipMark(p.vipPatient, p.patientName);
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
