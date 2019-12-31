import moment from 'moment';
import analysisAppointments from './analysisAppointments';

export default function convertPatientToPatientDetail(patient, appointments) {
  const currentDate = moment();
  const appointmentsAnalysis = analysisAppointments(appointments);
  const { name, birth, id, gender } = patient;
  const age = birth ? `${birth}, ${currentDate.diff(moment(birth), 'y')}` : '';
  return { name, id, gender, age, appointmentsAnalysis };
}
