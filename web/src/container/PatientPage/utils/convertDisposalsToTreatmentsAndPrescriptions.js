import { toSurfaceAbbrivation } from './';

function convertDisposalsToTreatmentsAndPrescriptions(disposals) {
  if (!disposals) {
    return [];
  }

  const returnTreatments = [];
  const returnPrescriptions = [];
  const returnDisposalWithoutTreatmentsAndPrescriptions = [];

  disposals.forEach(d => {
    const {
      treatmentProcedures,
      prescription,
      revisitContent,
      revisitComment,
      chiefComplaint,
      registration,
      dateTime,
    } = d;
    const doctor = d.registration?.appointment?.doctor;

    const template = { doctor, revisitContent, revisitComment, chiefComplaint, dateTime };

    // treatmentProcedures
    treatmentProcedures?.forEach &&
      treatmentProcedures.forEach(t => {
        const id = `${t.id}txP`;
        let category;
        let title;
        let content;
        if (t.nhiProcedure) {
          category = '健保';
          title = (t?.nhiProcedure?.code ?? '') + ' ' + (t?.nhiProcedure?.name ?? '');
          content = (t?.nhiProcedure?.nhiIcd9Cm?.code ?? '') + ' ' + (t?.nhiProcedure?.nhiIcd9Cm?.name ?? '');
        } else {
          category = '自費';
          title = t?.procedure?.content ?? '';

          const price = `定價 ${t.price}`;
          const amount = `數量 ${t.quantity}`;
          const discount = `折扣 ${d.registration.accounting.discount}`;
          content = [price, amount, discount].join(', ');
        }
        const teeth = t.teeth
          ?.map(te => {
            const surface = toSurfaceAbbrivation(te.surface);
            return `${te.position} ${surface}`;
          })
          ?.join(', ');
        returnTreatments.push({ ...template, id, category, teeth, title, content });
      });

    // prescription
    prescription?.treatmentDrugs?.forEach &&
      prescription.treatmentDrugs.forEach(t => {
        const id = `${t.id}txD`;
        const category = '藥品';
        const teeth = '-';
        const title = t?.drug?.name ?? '';

        const day = `${t.day} 天 `;
        const frequency = `頻率 ${t.frequency}`;
        const way = `部位 ${t.way}`;
        const quantity = `用量 ${t.quantity}`;
        const totalAmount = `總量 ${t.totalAmount}`;

        const content = [day, frequency, way, quantity, totalAmount].join(', ');

        returnPrescriptions.push({ ...template, id, category, teeth, title, content });
      });

    // disposal without treatments and prescriptions
    if (!prescription?.treatmentDrugs?.length && !treatmentProcedures?.length) {
      const id = `${d.id}disposal`;
      const category = registration?.type;
      returnDisposalWithoutTreatmentsAndPrescriptions.push({ ...template, id, category });
    }
  });

  return [...returnTreatments, ...returnPrescriptions, ...returnDisposalWithoutTreatmentsAndPrescriptions];
}

export default convertDisposalsToTreatmentsAndPrescriptions;
