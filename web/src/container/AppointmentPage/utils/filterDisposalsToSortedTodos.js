import moment from 'moment';
import { DisposalStatus } from '../constant';

export function filterDisposalsToSortedTodos(disposals) {
  return disposals
    .filter(d => d.todo)
    .filter(d => d.status === DisposalStatus.PERMANENT)
    .sort((a, b) => moment(a.todo.createdDate).diff(moment(b.todo.createdDate)));
}
