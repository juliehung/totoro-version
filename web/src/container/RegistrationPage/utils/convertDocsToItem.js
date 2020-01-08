import moment from 'moment';

export function convertDocsToItem(doc) {
  const key = doc.id;
  const id = doc.id;
  const createDate = moment(doc.createdDate).format('YYYY-MM-DD HH:mm');
  return { key, id, createDate };
}
