import moment from 'moment';

export default function convertBirthToROCBirth(birth) {
  return moment(birth)
    .add(-1911, 'year')
    .format('YYYY-MM-DD')
    .replace(/^0+/, '');
}
