import moment from 'moment';

function getCurrentMonthPoint(totalPointByDisposalDate) {
  if (!totalPointByDisposalDate?.data) {
    return {};
  }
  const filterWithDate = totalPointByDisposalDate?.data
    .filter(
      ({ disposalDate }) =>
        new Date(totalPointByDisposalDate?.startDate).getMonth() + 1 === new Date(disposalDate).getMonth() + 1,
    )
    .sort((a, b) => new Date(a.disposalDate).getTime() - new Date(b.disposalDate).getTime());
  const totalPoint = filterWithDate.map(({ total }) => total).reduce((a, b) => a + b);
  const treatmentTotalPoint = filterWithDate.map(({ treatmentPoint }) => treatmentPoint).reduce((a, b) => a + b);
  const examinationTotalPoint = filterWithDate
    .map(
      ({ infectionExaminationPoint, regularExaminationPoint }) => infectionExaminationPoint + regularExaminationPoint,
    )
    .reduce((a, b) => a + b);
  const totalAbs = Math.round(totalPoint / filterWithDate.length);
  const currentMonthDates = moment(totalPointByDisposalDate?.startDate).daysInMonth();

  const reTotalPointByDisposalDate = [];
  for (let i = 1; i <= currentMonthDates; i++) {
    const findMapping = filterWithDate.filter(data => new Date(data?.disposalDate).getDate() === i);
    if (findMapping.length !== 0) {
      reTotalPointByDisposalDate.push({
        ...findMapping[0],
        name: `${new Date(findMapping[0]?.disposalDate).getDate()}`,
        color: findMapping[0]?.total > totalAbs ? '#26c8f0' : '#ffc935',
      });
    } else {
      const disposalDate = new Date(`${new Date().getFullYear()}-${new Date().getMonth()}-${i}`).toISOString();
      reTotalPointByDisposalDate.push({
        copayment: 0,
        disposalDate: `${disposalDate}`,
        endoPoint: 0,
        infectionExaminationPoint: 0,
        pedoPoint: 0,
        perioPoint: 0,
        regularExaminationPoint: 0,
        total: 0,
        totalDisposal: 0,
        treatmentPoint: 0,
        name: `${i}`,
        color: '#ffc935',
      });
    }
  }
  const colors = reTotalPointByDisposalDate.map(({ total = 0 }) => (total > totalAbs ? '#26c8f0' : '#ffc935'));

  return {
    totalPoint,
    treatmentTotalPoint,
    examinationTotalPoint,
    totalPointByDisposalDate: reTotalPointByDisposalDate,
    colors: colors,
  };
}

export default getCurrentMonthPoint;
