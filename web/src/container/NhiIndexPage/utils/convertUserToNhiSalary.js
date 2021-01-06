function convertUserToNhiSalary(salary, users) {
  if (!salary) {
    return undefined;
  }
  const reNhiSalary = Object.keys(salary).map(key => {
    const findMappingDoctor = users.filter(({ id }) => id === parseInt(key));
    return (
      findMappingDoctor.length > 0 && {
        ...salary[key],
        doctorName: findMappingDoctor[0]?.firstName,
        doctorId: findMappingDoctor[0]?.id,
      }
    );
  });

  return reNhiSalary
    .filter(d => d?.doctorId && d?.doctorName)
    .sort((a, b) => a.doctorId - b.doctorId)
    .map((record, index) => {
      return {
        ...record,
        no: index + 1,
      };
    });
}

export default convertUserToNhiSalary;
