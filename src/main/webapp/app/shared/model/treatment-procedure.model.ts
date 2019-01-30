import { INHIProcedure } from 'app/shared/model//nhi-procedure.model';
import { ITreatmentTask } from 'app/shared/model//treatment-task.model';
import { IProcedure } from 'app/shared/model//procedure.model';
import { IAppointment } from 'app/shared/model//appointment.model';
import { IRegistration } from 'app/shared/model//registration.model';

export const enum TreatmentProcedureStatus {
  PLANNED = 'PLANNED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCEL = 'CANCEL',
  HIDE = 'HIDE'
}

export interface ITreatmentProcedure {
  id?: number;
  status?: TreatmentProcedureStatus;
  quantity?: number;
  total?: number;
  note?: string;
  nhiProcedure?: INHIProcedure;
  treatmentTask?: ITreatmentTask;
  procedure?: IProcedure;
  appointment?: IAppointment;
  registration?: IRegistration;
}

export const defaultValue: Readonly<ITreatmentProcedure> = {};
