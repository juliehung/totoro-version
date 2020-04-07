import moment from 'moment';
import { DisposalStatus } from '../constant';

export function filterDisposalsToSortedTodos(disposals) {
  return disposals
    .filter((d) => d.status === DisposalStatus.PERMANENT)
    .filter((d) => d.todo)
    .sort((a, b) => moment(a.todo.createdDate).diff(moment(b.todo.createdDate)));
}
