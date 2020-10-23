import moment from 'moment';
import { toSurfaceAbbrivation } from './';

export default function convertTreatmentProcedureToTableObject(treatmentProcedures) {
  if (!treatmentProcedures) return [];

  return treatmentProcedures.map(t => {
    const key = t.id;

    const year = moment(t.createdDate).year() - 1911;
    const date = year + moment(t.createdDate).format('-MM-DD');

    const teeth = t.teeth
      .map(te => {
        const surface = toSurfaceAbbrivation(te.surface);
        return `${te.position} ${surface}`;
      })
      ?.join(', ');

    const treatment = t?.nhiProcedure ? t.nhiProcedure.name : t?.procedure?.content;

    const doctor = t.doctor.user.firstName;

    return { key, date, teeth, treatment, doctor };
  });
}
