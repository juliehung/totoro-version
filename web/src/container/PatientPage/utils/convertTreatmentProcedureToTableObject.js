import moment from 'moment';

export default function convertTreatmentProcedureToTableObject(treatmentProcedures) {
  if (!treatmentProcedures) return [];

  return treatmentProcedures.map(t => {
    const key = t.id;

    const year = moment(t.createdDate).year() - 1911;
    const date = year + moment(t.createdDate).format('-MM-DD');

    const teeth = t.teeth.map(te => te.position).join(', ');

    const treatment = t?.nhiProcedure?.name;

    const doctor = t.doctor.user.firstName;

    return { key, date, teeth, treatment, doctor };
  });
}
