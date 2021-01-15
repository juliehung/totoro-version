function toRefreshValidNhiData(validNhiData, user) {
  if (!validNhiData) {
    return {};
  }
  const userNotionalIds = user.map(user => {
    return {
      nationalId: user?.extendUser?.nationalId,
      doctorName: user?.firstName,
    };
  });
  const reValidNhiData = {};
  for (const data of validNhiData.slice()) {
    if (data?.nhiExtendDisposalList && data?.nhiExtendDisposalList[0] && data?.nhiExtendDisposalList[0]?.date) {
      const findDoctor = userNotionalIds.filter(({ nationalId }) => nationalId === data?.nhiExtendDisposalList[0]?.a15);
      if (data?.nhiExtendDisposalList[0]?.replenishmentDate) {
        reValidNhiData[data?.nhiExtendDisposalList[0]?.replenishmentDate] = [].concat(
          reValidNhiData[data?.nhiExtendDisposalList[0]?.replenishmentDate]
            ? reValidNhiData[data?.nhiExtendDisposalList[0]?.replenishmentDate]
            : [],
          [
            {
              disposalId: data?.disposalId,
              nhiExtendDisposal: {
                ...data?.nhiExtendDisposalList[0],
                doctorName: findDoctor.length !== 0 ? findDoctor[0]?.doctorName : '',
              },
            },
          ],
        );
      } else {
        reValidNhiData[data?.nhiExtendDisposalList[0]?.date] = [].concat(
          reValidNhiData[data?.nhiExtendDisposalList[0]?.date]
            ? reValidNhiData[data?.nhiExtendDisposalList[0]?.date]
            : [],
          [
            {
              disposalId: data?.disposalId,
              nhiExtendDisposal: {
                ...data?.nhiExtendDisposalList[0],
                doctorName: findDoctor.length !== 0 ? findDoctor[0]?.doctorName : '',
              },
            },
          ],
        );
      }
    }
  }
  return Object.fromEntries(
    Object.entries(reValidNhiData).sort(([a], [b]) => new Date(a).getTime() - new Date(b).getTime()),
  );
}

export default toRefreshValidNhiData;
