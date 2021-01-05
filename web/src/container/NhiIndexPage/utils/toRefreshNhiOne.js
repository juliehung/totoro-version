import uuid from 'react-uuid';
import { staticExaminationData } from './staticExaminationData';

function toRefreshValidNhiData(nhiOne, validNhiData, doctorData) {
  if (!validNhiData && !nhiOne) {
    return undefined;
  }
  const treatmentData = [];
  const examinationData = [];
  const userNotionalIds = doctorData.map(doctor => {
    return {
      nationalId: doctor?.extendUser?.nationalId,
      doctorName: doctor?.firstName,
    };
  });
  const findMappingData = validNhiData.filter(({ disposalId }) => disposalId === nhiOne?.id);
  if (nhiOne && nhiOne?.treatmentProcedures.length !== 0) {
    for (const treatmentProcedure of nhiOne?.treatmentProcedures) {
      const nhiName = `${treatmentProcedure?.nhiProcedure?.code} ${treatmentProcedure?.nhiProcedure?.name}`;
      treatmentData.push({
        _id: `${nhiOne?.id}-${uuid()}`,
        nhiName,
        point: treatmentProcedure?.nhiProcedure?.point,
        total: treatmentProcedure?.total,
        quantity: treatmentProcedure?.quantity,
        multiplier:
          treatmentProcedure?.total &&
          `${
            Math.round(
              treatmentProcedure?.total / (treatmentProcedure?.quantity * treatmentProcedure?.nhiProcedure?.point),
            ) * 100
          }%`,
      });
    }
  }
  if (nhiOne && nhiOne?.nhiExtendDisposals.length !== 0) {
    for (const nhiExtendDisposal of nhiOne?.nhiExtendDisposals) {
      const nhiName = `${nhiExtendDisposal?.examinationCode} ${
        staticExaminationData[nhiExtendDisposal?.examinationCode]
      }`;
      examinationData.push({
        _id: `${nhiOne?.id}-${uuid()}`,
        nhiName,
        point: nhiExtendDisposal?.examinationPoint,
        total: nhiExtendDisposal?.examinationPoint,
        quantity: 1,
        multiplier: '100%',
      });
    }
  }

  if (findMappingData.length !== 0) {
    const { nhiExtendDisposalList } = findMappingData[0];
    const findMappingDoctor = userNotionalIds.filter(({ nationalId }) => nationalId === nhiExtendDisposalList[0]?.a15);
    const examinationPoint = examinationData.map(({ total }) => total).reduce((a, b) => (b ? a + b : a), 0);
    const treatmentPoint = treatmentData.map(({ total }) => total).reduce((a, b) => (b ? a + b : a), 0);
    return {
      ...nhiOne,
      patientName: nhiExtendDisposalList[0]?.patientName,
      doctorName: findMappingDoctor.length !== 0 ? findMappingDoctor[0].doctorName : '',
      patientNationalId: nhiExtendDisposalList[0]?.a12,
      examinationPoint,
      treatmentPoint,
      totalPoint: examinationPoint + treatmentPoint,
      nhiOneTableData: [].concat(treatmentData, examinationData),
    };
  }
  return nhiOne;
}

export default toRefreshValidNhiData;
