function toRefreshExpandSalary(expandSalary) {
  if (!expandSalary) {
    return [];
  }

  return expandSalary.map(({ doctorId, doctorOneSalary }) => {
    return {
      doctorId,
      doctorOneSalary: Object.keys(doctorOneSalary).map((key, index) => {
        return { ...doctorOneSalary[key], key: `expandSalary-${doctorId}-${index}` };
      }),
    };
  });
}

export default toRefreshExpandSalary;
