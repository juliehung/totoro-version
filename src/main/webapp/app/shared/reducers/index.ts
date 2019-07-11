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
import drug, {
  DrugState
} from 'app/entities/drug/drug.reducer';
// prettier-ignore
import todo, {
  TodoState
} from 'app/entities/todo/todo.reducer';
// prettier-ignore
import prescription, {
  PrescriptionState
} from 'app/entities/prescription/prescription.reducer';
// prettier-ignore
import treatmentDrug, {
  TreatmentDrugState
} from 'app/entities/treatment-drug/treatment-drug.reducer';
// prettier-ignore
import disposal, {
  DisposalState
} from 'app/entities/disposal/disposal.reducer';
// prettier-ignore
import registrationDel, {
  RegistrationDelState
} from 'app/entities/registration-del/registration-del.reducer';
// prettier-ignore
import conditionType, {
  ConditionTypeState
} from 'app/entities/condition-type/condition-type.reducer';
// prettier-ignore
import nhiIcd9Cm, {
  NhiIcd9CmState
} from 'app/entities/nhi-icd-9-cm/nhi-icd-9-cm.reducer';
// prettier-ignore
import nhiIcd10Cm, {
  NhiIcd10CmState
} from 'app/entities/nhi-icd-10-cm/nhi-icd-10-cm.reducer';
// prettier-ignore
import nhiProcedure, {
  NhiProcedureState
} from 'app/entities/nhi-procedure/nhi-procedure.reducer';
// prettier-ignore
import nhiProcedureType, {
  NhiProcedureTypeState
} from 'app/entities/nhi-procedure-type/nhi-procedure-type.reducer';
// prettier-ignore
import nhiIcd10Pcs, {
  NhiIcd10PcsState
} from 'app/entities/nhi-icd-10-pcs/nhi-icd-10-pcs.reducer';
// prettier-ignore
import nhiDayUpload, {
  NhiDayUploadState
} from 'app/entities/nhi-day-upload/nhi-day-upload.reducer';
// prettier-ignore
import nhiDayUploadDetails, {
  NhiDayUploadDetailsState
} from 'app/entities/nhi-day-upload-details/nhi-day-upload-details.reducer';
// prettier-ignore
import nhiExtendPatient, {
  NhiExtendPatientState
} from 'app/entities/nhi-extend-patient/nhi-extend-patient.reducer';
// prettier-ignore
import nhiMedicalRecord, {
  NhiMedicalRecordState
} from 'app/entities/nhi-medical-record/nhi-medical-record.reducer';
// prettier-ignore
import esign, {
  EsignState
} from 'app/entities/esign/esign.reducer';
// prettier-ignore
import marriageOptions, {
  MarriageOptionsState
} from 'app/entities/marriage-options/marriage-options.reducer';
// prettier-ignore
import careerOptions, {
  CareerOptionsState
} from 'app/entities/career-options/career-options.reducer';
// prettier-ignore
import relationshipOptions, {
  RelationshipOptionsState
} from 'app/entities/relationship-options/relationship-options.reducer';

// prettier-ignore
import nhiMonthDeclaration, {
  NhiMonthDeclarationState
} from 'app/entities/nhi-month-declaration/nhi-month-declaration.reducer';
// prettier-ignore
import nhiMonthDeclarationDetails, {
  NhiMonthDeclarationDetailsState
} from 'app/entities/nhi-month-declaration-details/nhi-month-declaration-details.reducer';
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
  readonly drug: DrugState;
  readonly todo: TodoState;
  readonly prescription: PrescriptionState;
  readonly treatmentDrug: TreatmentDrugState;
  readonly disposal: DisposalState;
  readonly registrationDel: RegistrationDelState;
  readonly conditionType: ConditionTypeState;
  readonly nhiIcd9Cm: NhiIcd9CmState;
  readonly nhiIcd10Cm: NhiIcd10CmState;
  readonly nhiProcedure: NhiProcedureState;
  readonly nhiProcedureType: NhiProcedureTypeState;
  readonly nhiIcd10Pcs: NhiIcd10PcsState;
  readonly nhiDayUpload: NhiDayUploadState;
  readonly nhiDayUploadDetails: NhiDayUploadDetailsState;
  readonly nhiExtendPatient: NhiExtendPatientState;
  readonly nhiMedicalRecord: NhiMedicalRecordState;
  readonly esign: EsignState;
  readonly marriageOptions: MarriageOptionsState;
  readonly careerOptions: CareerOptionsState;
  readonly relationshipOptions: RelationshipOptionsState;
  readonly nhiMonthDeclaration: NhiMonthDeclarationState;
  readonly nhiMonthDeclarationDetails: NhiMonthDeclarationDetailsState;
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
  drug,
  todo,
  prescription,
  treatmentDrug,
  disposal,
  registrationDel,
  conditionType,
  nhiIcd9Cm,
  nhiIcd10Cm,
  nhiProcedure,
  nhiProcedureType,
  nhiIcd10Pcs,
  nhiDayUpload,
  nhiDayUploadDetails,
  nhiExtendPatient,
  nhiMedicalRecord,
  esign,
  marriageOptions,
  careerOptions,
  relationshipOptions,
  nhiMonthDeclaration,
  nhiMonthDeclarationDetails,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
