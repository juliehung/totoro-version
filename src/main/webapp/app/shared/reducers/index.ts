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
// prettier-ignore
import hospital, {
  HospitalState
} from 'app/entities/hospital/hospital.reducer';
// prettier-ignore
import accounting, {
  AccountingState
} from 'app/entities/accounting/accounting.reducer';
// prettier-ignore
import calendar, {
  CalendarState
} from 'app/entities/calendar/calendar.reducer';
// prettier-ignore
import nHIUnusalIncident, {
  NHIUnusalIncidentState
} from 'app/entities/nhi-unusal-incident/nhi-unusal-incident.reducer';
// prettier-ignore
import nHIUnusalContent, {
  NHIUnusalContentState
} from 'app/entities/nhi-unusal-content/nhi-unusal-content.reducer';
// prettier-ignore
import patientIdentity, {
  PatientIdentityState
} from 'app/entities/patient-identity/patient-identity.reducer';
// prettier-ignore
import calendarSetting, {
  CalendarSettingState
} from 'app/entities/calendar-setting/calendar-setting.reducer';
// prettier-ignore
import tooth, {
  ToothState
} from 'app/entities/tooth/tooth.reducer';
// prettier-ignore
import ledger, {
  LedgerState
} from 'app/entities/ledger/ledger.reducer';
// prettier-ignore
import procedure, {
  ProcedureState
} from 'app/entities/procedure/procedure.reducer';
// prettier-ignore
import procedureType, {
  ProcedureTypeState
} from 'app/entities/procedure-type/procedure-type.reducer';
// prettier-ignore
import treatment, {
  TreatmentState
} from 'app/entities/treatment/treatment.reducer';
// prettier-ignore
import treatmentPlan, {
  TreatmentPlanState
} from 'app/entities/treatment-plan/treatment-plan.reducer';
// prettier-ignore
import findingType, {
  FindingTypeState
} from 'app/entities/finding-type/finding-type.reducer';
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
  readonly hospital: HospitalState;
  readonly accounting: AccountingState;
  readonly calendar: CalendarState;
  readonly nHIUnusalIncident: NHIUnusalIncidentState;
  readonly nHIUnusalContent: NHIUnusalContentState;
  readonly patientIdentity: PatientIdentityState;
  readonly calendarSetting: CalendarSettingState;
  readonly tooth: ToothState;
  readonly ledger: LedgerState;
  readonly procedure: ProcedureState;
  readonly procedureType: ProcedureTypeState;
  readonly treatment: TreatmentState;
  readonly treatmentPlan: TreatmentPlanState;
  readonly findingType: FindingTypeState;
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
  hospital,
  accounting,
  calendar,
  nHIUnusalIncident,
  nHIUnusalContent,
  patientIdentity,
  calendarSetting,
  tooth,
  ledger,
  procedure,
  procedureType,
  treatment,
  treatmentPlan,
  findingType,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
