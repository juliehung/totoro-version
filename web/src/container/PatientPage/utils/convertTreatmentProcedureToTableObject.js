import { toSurfaceAbbrivation } from './';

export default function convertTreatmentProcedureToTableObject(treatmentProcedures) {
  if (!treatmentProcedures) return [];

  return treatmentProcedures.map(t => {
    const key = t.id;

    const date = t.completedDate;

    const teeth = t.teeth
      .map(te => {
        const surface = te?.surface ? toSurfaceAbbrivation(te?.surface) : '';
        return `${te.position} ${surface}`;
      })
      ?.join(', ');

    const treatment = t?.nhiProcedure ? t.nhiProcedure.code + ' ' + t.nhiProcedure.name : t?.procedure?.content;

    const doctor = t.doctor.user.firstName;

    return { key, date, teeth, treatment, doctor };
  });
}
