import { ITreatmentTask } from 'app/shared/model//treatment-task.model';
import { ITreatment } from 'app/shared/model//treatment.model';

export interface ITreatmentPlan {
  id?: number;
  activated?: boolean;
  name?: string;
  treatmentTasks?: ITreatmentTask[];
  treatment?: ITreatment;
}

export const defaultValue: Readonly<ITreatmentPlan> = {
  activated: false
};
