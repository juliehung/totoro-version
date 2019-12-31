import moment from 'moment';

export function filterDisposalsToSortedTodos(disposals) {
  return disposals
    .filter(d => d.status === 'PERMANENT')
    .sort((a, b) => moment(a.todo.createdDate).diff(moment(b.todo.createdDate)));
}
