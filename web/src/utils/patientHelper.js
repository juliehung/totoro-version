export const parsePatientNameWithVipMark = (isVip, name) => {
  return isVip ? (name ? `*${name}` : name) : '';
};
