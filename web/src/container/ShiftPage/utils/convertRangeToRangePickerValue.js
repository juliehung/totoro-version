import moment from 'moment';

export function convertRangeToRangePickerValue(range) {
  return [moment(range.start, 'HH:mm'), moment(range.end, 'HH:mm')];
}
