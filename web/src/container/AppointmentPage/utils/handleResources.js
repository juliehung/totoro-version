export function handleResources(doctors, shifts, getShiftSuccess) {
  return getShiftSuccess
    ? shifts
        .map(s => s.resourceId)
        .filter(r => r)
        .filter((item, pos, self) => self.indexOf(item) === pos)
        .map(r => r)
        .map(r => doctors.find(d => d.id === r))
        .filter(d => d)
        .map(d => ({ id: d.id, title: d.name }))
    : [];
}
