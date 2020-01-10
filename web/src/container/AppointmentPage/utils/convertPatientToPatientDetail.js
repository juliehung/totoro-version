import moment from 'moment';
import analysisAppointments from './analysisAppointments';

const Gender = [
  { ch: '男性', en: 'MALE' },
  { ch: '女性', en: 'FEMALE' },
  { ch: '其他', en: 'OTHER' },
];

export default function convertPatientToPatientDetail(patient, appointments) {
  const currentDate = moment();
  const appointmentsAnalysis = analysisAppointments(appointments);
  const { name, birth, id, gender } = patient;
  const age = birth ? `${currentDate.diff(moment(birth), 'y')}` : '';
  const genderTranslate = (() => {
    if (!gender) {
      return undefined;
    }
    const findGender = Gender.find(g => g.en === gender);
    if (findGender) {
      return findGender.ch;
    }
    return undefined;
  })();
  return { name, id, gender: genderTranslate, birth, age, appointmentsAnalysis };
}
