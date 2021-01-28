export const parsePatientNameWithVipMark = (isVip, name) => {
  return name ? (isVip ? `*${name}` : name) : '';
};
