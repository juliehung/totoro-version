import moment from 'moment';

const momentToRocString = date => {
  const dateMomentObject = moment(date);
  const year = dateMomentObject.year() - 1911;
  const dateString = dateMomentObject.format('/MM/DD HH:mm');
  return `${year}${dateString}`;
};

export default momentToRocString;
