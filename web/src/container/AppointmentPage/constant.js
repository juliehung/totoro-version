export const requiredTreatmentTimeDefault = [5, 15, 30, 45, 60, 75, 90, 105, 120];

export const repeatedCalendarEvtDefault = [
  { id: 'none', value: '不重複' },
  { id: 'day', value: '每天' },
  { id: 'week', value: '每周' },
  { id: 'month', value: '每月' },
];

export const repeatedCron = {
  day: '',
  week: '',
  month: '',
};

export const DisposalStatus = {
  TEMPORARY: 'TEMPORARY',
  PERMANENT: 'PERMANENT',
  MADE_APPT: 'MADE_APPT',
};

export const XRAY_VENDORS = {
  Poye: 1,
  Ray: 2,
  vision: 'vision',
  vixwin: 'vixwin',
};

export const patientSearchMode = {
  name: 'name',
  phone: 'phone',
  birth: 'birth',
  medical_id: 'medical_id',
  national_id: 'national_id',
};

export const APPT_CUSTOM_COLORS = [
  { id: 0, color: '#f09300', text: '預設顏色' },
  { id: 11, color: '#8e25a9', text: '自選顏色1(紫)' },
  { id: 12, color: '#a91355', text: '自選顏色1(深紅)' },
  { id: 13, color: '#0d9ce6', text: '自選顏色3(藍)' },
];

export const APPOINTMENT_PAGE = 'totoro-web-appointmentPage/';

export const CHANGE_CAL_DATE = `${APPOINTMENT_PAGE}CHANGE_CAL_DATE`;

export const CHANGE_CAL_FIRST_DAY = `${APPOINTMENT_PAGE}CHANGE_CAL_FIRST_DAY`;

export const GET_APPOINTMENTS_START = `${APPOINTMENT_PAGE}GET_APPOINTMENTS_START`;

export const GET_APPOINTMENTS_SUCCESS = `${APPOINTMENT_PAGE}GET_APPOINTMENTS_SUCCESS`;

export const CHANGE_PRINT_MODAL_VISIBLE = `${APPOINTMENT_PAGE}CHANGE_PRINT_MODAL_VISIBLE`;

export const CHANGE_PRINT_DATE = `${APPOINTMENT_PAGE}CHANGE_PRINT_DATE`;

export const GET_PRINT_APP_LIST_SUCCESS = `${APPOINTMENT_PAGE}GET_PRINT_APP_LIST_SUCCESS`;

export const CHANGE_SELECTED_DOCTORS = `${APPOINTMENT_PAGE}CHANGE_SELECTED_DOCTORS`;

export const CHANGE_CONFIRM_MODAL_VISIBLE = `${APPOINTMENT_PAGE}CHANGE_CONFIRM_MODAL_VISIBLE`;

export const INSERT_PENDING_INFO = `${APPOINTMENT_PAGE}INSERT_PENDING_INFO`;

export const EDIT_APPOINTMENT_START = `${APPOINTMENT_PAGE}EDIT_APPOINTMENT_START`;

export const EDIT_APPOINTMENT_SUCCESS = `${APPOINTMENT_PAGE}EDIT_APPOINTMENT_SUCCESS`;

export const CREATE_APPOINTMENT = `${APPOINTMENT_PAGE}CREATE_APPOINTMENT`;

export const CREATE_APPOINTMENT_SUCCESS = `${APPOINTMENT_PAGE}CREATE_APPOINTMENT_SUCCESS`;

export const CHANGE_CREATE_APPOINTMENT_VISIBLE = `${APPOINTMENT_PAGE}CHANGE_CREATE_APPOINTMENT_VISIBLE`;

export const SEARCH_PATIENTS_START = `${APPOINTMENT_PAGE}SEARCH_PATIENT_START`;

export const SEARCH_PATIENTS_SUCCESS = `${APPOINTMENT_PAGE}SEARCH_PATIENT_SUCCESS`;

export const CHANGE_PATIENT_SELECTED = `${APPOINTMENT_PAGE}CHANGE_PATIENT_SELECTED`;

export const GET_PATIENT_START_CREATE_APP = `${APPOINTMENT_PAGE}GET_PATIENT_START_CREATE_APP`;

export const GET_PATIENT_SUCCESS_CREATE_APP = `${APPOINTMENT_PAGE}GET_PATIENT_SUCCESS_CREATE_APP`;

export const CHANGE_CREATE_APP_EXPECTED_ARRIVAL_DATE = `${APPOINTMENT_PAGE}CHANGE_EXPECTED_ARRIVAL_DATE`;

export const CHANGE_CREATE_APP_EXPECTED_ARRIVAL_TIME = `${APPOINTMENT_PAGE}CHANGE_EXPECTED_ARRIVAL_TIME`;

export const CHANGE_CREATE_APP_COLOR_ID = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_COLOR_ID`;

export const CHANGE_CREATE_APP_DOCTOR = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_DOCTOR`;

export const CHANGE_CREATE_APP_DEFAULT_DOCTOR = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_DEFAULT_DOCTOR`;

export const CHANGE_CREATE_APP_DURATION = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_DURATION`;

export const CHANGE_CREATE_APP_DEFAULT_DURATION = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_DEFAULT_DURATION`;

export const CHANGE_CREATE_APP_NOTE = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_NOTE`;

export const CHANGE_CREATE_APP_SPECIAL_NOTE = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_SPECIAL_NOTE`;

export const CHECK_CONFIRM_BUTTON_DISABLE = `${APPOINTMENT_PAGE}CHECK_CONFIRM_BUTTON_DISABLE`;

export const CHANGE_CREATE_APP_PATIENT_NAME = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_PATIENT_NAME`;

export const CHANGE_CREATE_APP_PATIENT_PHONE = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_PATIENT_PHONE`;

export const CHANGE_CREATE_APP_PATIENT_NATIONAL_ID = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_PATIENT_ID`;

export const CHANGE_CREATE_APP_PATIENT_BIRTH = `${APPOINTMENT_PAGE}CHANGE_CREATE_APP_PATIENT_BIRTH`;

export const CREATE_PATIENT = `${APPOINTMENT_PAGE}CREATE_PATIENT`;

export const CREATE_PATIENT_SUCCESS = `${APPOINTMENT_PAGE}CREATE_PATIENT_SUCCESS`;

export const CHANGE_EDIT_APP_MODAL_VISIBLE = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_MODAL_VISIBLE`;

export const INSERT_APP_TO_EDIT_APP_MODAL = `${APPOINTMENT_PAGE}INSERT_APP_TO_EDIT_APP_MODAL`;

export const GET_PATIENT_SUCCESS_EDIT_APP = `${APPOINTMENT_PAGE}GET_PATIENT_SUCCESS_EDIT_APP`;

export const DELETE_APPOINTMENT_START = `${APPOINTMENT_PAGE}DELETE_APPOINTMENT_START`;

export const DELETE_APPOINTMENT_SUCCESS = `${APPOINTMENT_PAGE}DELETE_APPOINTMENT_SUCCESS`;

export const CHANGE_EDIT_APP_CONFIRM_DELETE = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_CONFIRM_DELETE`;

export const CHANGE_EDIT_APP_EXPECTED_ARRIVAL_DATE = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_EXPECTED_ARRIVAL_DATE`;

export const CHANGE_EDIT_APP_EXPECTED_ARRIVAL_TIME = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_EXPECTED_ARRIVAL_TIME`;

export const CHANGE_EDIT_APP_COLOR_ID = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_COLOR_ID`;

export const CHANGE_EDIT_APP_DOCTOR = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_DOCTOR`;

export const CHANGE_EDIT_APP_DURATION = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_DURATION`;

export const CHANGE_EDIT_APP_NOTE = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_NOTE`;

export const CHANGE_EDIT_APP_SPECIAL_NOTE = `${APPOINTMENT_PAGE}CHANGE_EDIT_APP_SPECIAL_NOTE`;

export const CHECK_EDIT_APP_CONFIRM_MODAL_BUTTON_DISABLE = `${APPOINTMENT_PAGE}CHECK_EDIT_APP_CONFIRM_MODAL_BUTTON_DISABLE`;

export const GET_CALENDAR_EVENT_START = `${APPOINTMENT_PAGE}GET_CALENDAR_EVENT_START`;

export const GET_CALENDAR_EVENT_SUCCESS = `${APPOINTMENT_PAGE}GET_CALENDAR_EVENT_SUCCESS`;

export const CHANGE_CREATE_CAL_EVT_START_DATE = `${APPOINTMENT_PAGE}CAHNGE_CREATE_CAL_EVT_START_DATE`;

export const CAHNGE_CREATE_CAL_EVT_START_TIME = `${APPOINTMENT_PAGE}CAHNGE_CREATE_CAL_EVT_START_TIME`;

export const CAHNGE_CREATE_CAL_EVT_END_DATE = `${APPOINTMENT_PAGE}CAHNGE_CREATE_CAL_EVT_END_DATE`;

export const CAHNGE_CREATE_CAL_EVT_END_TIME = `${APPOINTMENT_PAGE}CAHNGE_CREATE_CAL_EVT_END_TIME`;

export const CHANGE_CREATE_CAL_EVT_ALL_DAY = `${APPOINTMENT_PAGE}CHANGE_CREATE_CAL_EVT_ALL_DAY`;

export const CHANGE_CREATE_CAL_EVT_DOCTOR = `${APPOINTMENT_PAGE}CHANGE_CREATE_CAL_EVT_DOCTOR`;

export const CHANGE_CREATE_CAL_EVT_REPEAT = `${APPOINTMENT_PAGE}CHANGE_CREATE_CAL_EVT_REPEAT`;

export const CHANGE_CREATE_CAL_EVT_REAPEAT_END_DATE = `${APPOINTMENT_PAGE}CHANGE_CREATE_CAL_EVT_REAPEAT_END_DATE`;

export const CHANGE_CREATE_CAL_EVT_NOTE = `${APPOINTMENT_PAGE}CHANGE_CREATE_CAL_EVT_NOTE`;

export const CHECK_CREATE_CAL_CONFIRM_BUTTON_DISABLE = `${APPOINTMENT_PAGE}CHECK_CREATE_CAL_CONFIRM_BUTTON_DISABLE`;

export const CHANGE_CREATE_CAL_MODAL_VISIBLE = `${APPOINTMENT_PAGE}CHANGE__CREATE_CAL_MODAL_VISIBLE`;

export const CREATE_CAL_EVT_START = `${APPOINTMENT_PAGE}CREATE_CAL_EVT_START`;

export const CREATE_CAL_EVT_SUCCESS = `${APPOINTMENT_PAGE}CREATE_CAL_EVT_SUCCESS`;

export const GET_ALL_EVENTS = `${APPOINTMENT_PAGE}GET_ALL_EVENTS`;

export const INSERT_CAL_EVT_TO_EDIT_CAL_EVT_MODEL = `${APPOINTMENT_PAGE}INSERT_CAL_EVT_TO_EDIT_CAL_EVT_MODEL`;

export const CHANGE_EDIT_CAL_MODAL_VISIBLE = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_MODAL_VISIBLE`;

export const CHANGE_EDIT_CAL_EVT_START_DATE = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_EVT_START_DATE`;

export const CAHNGE_EDIT_CAL_EVT_START_TIME = `${APPOINTMENT_PAGE}CAHNGE_EDIT_CAL_EVT_START_TIME`;

export const CAHNGE_EDIT_CAL_EVT_END_DATE = `${APPOINTMENT_PAGE}CAHNGE_EDIT_CAL_EVT_END_DATE`;

export const CAHNGE_EDIT_CAL_EVT_END_TIME = `${APPOINTMENT_PAGE}CAHNGE_EDIT_CAL_EVT_END_TIME`;

export const CHANGE_EDIT_CAL_EVT_ALL_DAY = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_EVT_ALL_DAY`;

export const CHANGE_EDIT_CAL_EVT_DOCTOR = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_EVT_DOCTOR`;

export const CHANGE_EDIT_CAL_EVT_REPEAT = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_EVT_REPEAT`;

export const CHANGE_EDIT_CAL_EVT_REPEAT_END_DATE = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_EVT_REPEAT_END_DATE`;

export const CHANGE_EDIT_CAL_EVT_NOTE = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_EVT_NOTE`;

export const CHECK_EDIT_CAL_CONFIRM_BUTTON_DISABLE = `${APPOINTMENT_PAGE}CHECK_EDIT_CAL_CONFIRM_BUTTON_DISABLE`;

export const CHANGE_EDIT_CAL_EVT_CONFIRM_DELETE = `${APPOINTMENT_PAGE}CHANGE_EDIT_CAL_EVT_CONFIRM_DELETE`;

export const EDIT_CAL_EVT_START = `${APPOINTMENT_PAGE}EDIT_CAL_EVT_START`;

export const EDIT_CAL_EVT_SUCCESS = `${APPOINTMENT_PAGE}EDIT_CAL_EVT_SUCCESS`;

export const DELETE_CAL_EVT_START = `${APPOINTMENT_PAGE}DELETE_CAL_EVT_START`;

export const DELETE_CAL_EVT_SUCCESS = `${APPOINTMENT_PAGE}DELETE_CAL_EVT_SUCCESS`;

export const CHANGE_CAL_SLOT_DURATION = `${APPOINTMENT_PAGE}CHANGE_CAL_SLOT_DURATION`;

export const CHANGE_CALENDAR_FULLSCREEN = `${APPOINTMENT_PAGE}CHANGE_CALENDAR_FULLSCREEN`;

export const POPOVER_CANCEL_APP_START = `${APPOINTMENT_PAGE}POPOVER_CANCEL_APP_START`;

export const POPOVER_CANCEL_APP_SUCCESS = `${APPOINTMENT_PAGE}POPOVER_CANCEL_APP_SUCCESS`;

export const GET_SHIFT_START = `${APPOINTMENT_PAGE}GET_SHIFT_START`;

export const GET_SHIFT_SUCCESS = `${APPOINTMENT_PAGE}GET_SHIFT_SUCCESS`;

export const ON_LEAVE_PAGE = `${APPOINTMENT_PAGE}ON_LEAVE_PAGE`;

export const CHANGE_PATIENT_SEARCH_MODE = `${APPOINTMENT_PAGE}CHANGE_PATIENT_SEARCH_MODE`;

export const CHANGE_CALENADR_RANGE = `${APPOINTMENT_PAGE}CHANGE_CALENADR_RANGE`;
