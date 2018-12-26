import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import patient, {
  PatientState
} from 'app/entities/patient/patient.reducer';
// prettier-ignore
import appointment, {
  AppointmentState
} from 'app/entities/appointment/appointment.reducer';
// prettier-ignore
import registration, {
  RegistrationState
} from 'app/entities/registration/registration.reducer';
// prettier-ignore
import tag, {
  TagState
} from 'app/entities/tag/tag.reducer';
// prettier-ignore
import questionnaire, {
  QuestionnaireState
} from 'app/entities/questionnaire/questionnaire.reducer';
// prettier-ignore
import treatmentTask, {
  TreatmentTaskState
} from 'app/entities/treatment-task/treatment-task.reducer';
// prettier-ignore
import treatmentProcedure, {
  TreatmentProcedureState
} from 'app/entities/treatment-procedure/treatment-procedure.reducer';
// prettier-ignore
import nHIProcedure, {
  NHIProcedureState
} from 'app/entities/nhi-procedure/nhi-procedure.reducer';
// prettier-ignore
import nHICategory, {
  NHICategoryState
} from 'app/entities/nhi-category/nhi-category.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly patient: PatientState;
  readonly appointment: AppointmentState;
  readonly registration: RegistrationState;
  readonly tag: TagState;
  readonly questionnaire: QuestionnaireState;
  readonly treatmentTask: TreatmentTaskState;
  readonly treatmentProcedure: TreatmentProcedureState;
  readonly nHIProcedure: NHIProcedureState;
  readonly nHICategory: NHICategoryState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  patient,
  appointment,
  registration,
  tag,
  questionnaire,
  treatmentTask,
  treatmentProcedure,
  nHIProcedure,
  nHICategory,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
