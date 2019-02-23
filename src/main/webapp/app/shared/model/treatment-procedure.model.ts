import { Moment } from 'moment';
import { INHIProcedure } from 'app/shared/model//nhi-procedure.model';
import { ITreatmentTask } from 'app/shared/model//treatment-task.model';
import { IProcedure } from 'app/shared/model//procedure.model';
import { IAppointment } from 'app/shared/model//appointment.model';
import { IRegistration } from 'app/shared/model//registration.model';
import { ITooth } from 'app/shared/model//tooth.model';
import { ITodo } from 'app/shared/model//todo.model';
import { IDisposal } from 'app/shared/model//disposal.model';

export const enum TreatmentProcedureStatus {
  PLANNED = 'PLANNED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCEL = 'CANCEL',
  HIDE = 'HIDE',
  TEMPORARY = 'TEMPORARY'
}

export interface ITreatmentProcedure {
  id?: number;
  status?: TreatmentProcedureStatus;
  quantity?: number;
  total?: number;
  note?: string;
  completedDate?: Moment;
  price?: number;
  nhiProcedure?: INHIProcedure;
  treatmentTask?: ITreatmentTask;
  procedure?: IProcedure;
  appointment?: IAppointment;
  registration?: IRegistration;
  teeth?: ITooth[];
  todo?: ITodo;
  disposal?: IDisposal;
}

export const defaultValue: Readonly<ITreatmentProcedure> = {};
