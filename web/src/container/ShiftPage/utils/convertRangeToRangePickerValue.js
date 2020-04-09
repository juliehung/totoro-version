import moment from 'moment';

export function convertRangeToRangePickerValue(range) {
  return [
    moment(range.start, 'HH:mm')._isValid ? moment(range.start, 'HH:mm') : undefined,
    moment(range.end, 'HH:mm')._isValid ? moment(range.end, 'HH:mm') : undefined,
  ];
}
