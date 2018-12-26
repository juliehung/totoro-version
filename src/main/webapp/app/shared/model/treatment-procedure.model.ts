import { INHIProcedure } from 'app/shared/model//nhi-procedure.model';
import { ITreatmentTask } from 'app/shared/model//treatment-task.model';

export interface ITreatmentProcedure {
  id?: number;
  price?: number;
  teeth?: string;
  surfaces?: string;
  nhiDeclared?: boolean;
  nhiProcedure?: INHIProcedure;
  treatmentTask?: ITreatmentTask;
}

export const defaultValue: Readonly<ITreatmentProcedure> = {
  nhiDeclared: false
};
