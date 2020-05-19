import {
  GET_EVENTS,
  GET_EVENTS_SUCCESS,
  SET_SELECT_EVENT,
  CREATE_EVENT,
  EDIT_TITLE,
  ADD_CONTACT_APPOINTMENTS,
  UNSELECT_APPOINTMENT,
  EDIT_TEMPLATE,
  FILTER_EVENTS,
  ADD_TAG,
  TOGGLE_PREVIEWING_MODAL,
  GET_CLINIC_SETTINGS_SUCCESS,
  GET_CLINIC_REMAINING_SUCCESS,
  SAVE_EVENT,
  EXECUTE_EVENT,
  EXECUTE_EVENT_SUCCESS,
  EXECUTE_EVENT_FAILED,
  DELETE_EVENT,
  SAVE_EVENT_SUCCESS,
  DELETE_EVENT_SUCCESS,
  SET_CARET_POSITION,
} from '../constant';

import produce from 'immer';
import moment from 'moment';
import uuid from 'react-uuid';

const initState = {
  staticEvents: [],
  events: [],
  isLoaded: false,
  selectedEventId: null,
  selectedEvent: null,
  editingEvent: null,
  tags: ['約診日期', '約診時間', '病患姓名', '診所名稱', '約診醫師', '約診內容'],
  visible: false,
  clinicName: '',
  clinicId: null,
  remaining: 0,
  isRemainingLoaded: false,
  isSentFailed: null,
  currentKey: 'ALL',
  caretPosition: -1,
  caretChangedBy: 'keydown',
  isEventsLoading: false,
  isDeletingEvent: false,
  total: 0,
};

const initialState = {
  ...initState,
};

const event = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      // make sure editign stuff is clear
      case GET_EVENTS:
        draft.selectedEvent = null;
        draft.selectedEventId = null;
        draft.editingEvent = null;
        draft.isLoaded = false;
        draft.isEventsLoading = true;
        break;
      case SAVE_EVENT:
        draft.isLoaded = false;
        break;
      case DELETE_EVENT:
        draft.isLoaded = false;
        draft.isDeletingEvent = true;
        break;
      case EXECUTE_EVENT:
        draft.isLoaded = false;
        draft.isSentFailed = null;
        break;

      case GET_EVENTS_SUCCESS: {
        const sorted = action.result.data.slice().sort((a, b) => (a.modifiedDate > b.modifiedDate ? -1 : 1));
        draft.total = action.result.total;
        draft.staticEvents = sorted;
        draft.events =
          state.currentKey === 'ALL'
            ? sorted
            : sorted.filter(event => {
                const sta = event.status.toLowerCase() === 'completed' ? 'sent' : 'draft';
                return sta === state.currentKey.toLowerCase();
              });
        draft.selectedEvent = null;
        draft.selectedEventId = null;
        draft.editingEvent = null;

        draft.isLoaded = true;
        draft.isEventsLoading = false;
        break;
      }

      case SAVE_EVENT_SUCCESS: {
        const snapShot = [...state.staticEvents];
        const identity = action.payload.identity;
        const toBePlaced = action.payload.result;
        const replaceIdx = snapShot.indexOf(
          snapShot.find(ev => (ev.id !== null ? identity === ev.id : identity === ev.tempId)),
        );
        snapShot[replaceIdx] = toBePlaced;
        draft.staticEvents = snapShot;
        draft.events =
          state.currentKey === 'ALL'
            ? draft.staticEvents
            : draft.staticEvents.filter(event => {
                const sta = event.status.toLowerCase() === 'completed' ? 'sent' : 'draft';
                return sta === state.currentKey.toLowerCase();
              });

        // still editing
        if (identity === state.selectedEventId) {
          draft.selectedEvent = draft.staticEvents[replaceIdx];
          draft.selectedEventId = toBePlaced.id;
          toBePlaced.isEdit = true;
          draft.editingEvent = { ...state.editingEvent, id: toBePlaced.id };
        }
        draft.isLoaded = true;
        break;
      }

      case DELETE_EVENT_SUCCESS: {
        let snapShot = [...state.staticEvents];
        const identity = action.identity;

        snapShot = snapShot.filter(event => event.id !== identity);
        draft.staticEvents = snapShot;
        draft.events =
          state.currentKey === 'ALL'
            ? draft.staticEvents
            : draft.staticEvents.filter(event => {
                const sta = event.status.toLowerCase() === 'completed' ? 'sent' : 'draft';
                return sta === state.currentKey.toLowerCase();
              });

        draft.selectedEvent = null;
        draft.selectedEventId = null;
        draft.editingEvent = null;
        draft.isLoaded = true;
        draft.isDeletingEvent = false;
        break;
      }

      case EXECUTE_EVENT_SUCCESS: {
        draft.isSentFailed = false;
        draft.visible = false;
        draft.isLoaded = true;
        break;
      }

      case EXECUTE_EVENT_FAILED: {
        draft.isSentFailed = true;
        draft.visible = false;
        draft.isLoaded = true;
        break;
      }

      case GET_CLINIC_SETTINGS_SUCCESS: {
        draft.clinicName = action.settings.preferences.generalSetting.clinicName;
        draft.clinicId = action.settings.id;
        break;
      }

      case GET_CLINIC_REMAINING_SUCCESS: {
        draft.remaining = action.remaining.remaining;
        draft.isRemainingLoaded = true;
        break;
      }

      case SET_SELECT_EVENT: {
        // 1. cleaning the view
        if (action.event === null) {
          draft.selectedEvent = null;
          draft.selectedEventId = null;
          draft.editingEvent = null;
          // if it doesn't have id, delete locally here.
          // if it has id => go saga > event > deleteEvent
          if (state.selectedEvent.id === null) {
            const newArr = state.staticEvents.filter(event => state.selectedEventId !== event.tempId);
            draft.staticEvents = newArr;
            draft.events =
              state.currentKey === 'ALL'
                ? draft.staticEvents
                : draft.staticEvents.filter(event => {
                    const sta = event.status.toLowerCase() === 'completed' ? 'sent' : 'draft';
                    return sta === state.currentKey.toLowerCase();
                  });
          }
          // 2. changing the view
        } else if (action.event !== null) {
          // 2-2.
          const selection = { ...action.event };
          if (selection.status === 'draft') selection.isEdit = true;
          draft.selectedEvent = selection;
          draft.selectedEventId = selection.id !== null ? selection.id : selection.tempId;
          draft.editingEvent = selection.isEdit ? selection : null;
          draft.caretPosition = -1;
        }
        break;
      }

      case CREATE_EVENT: {
        // 1. save existing
        const snapShot = [...state.staticEvents];
        if (state.editingEvent !== null) {
          const toBeSaved = state.editingEvent;
          const replaceIdx = snapShot.indexOf(
            snapShot.find(ev =>
              ev.id !== null ? state.selectedEventId === ev.id : state.selectedEventId === ev.tempId,
            ),
          );
          snapShot[replaceIdx] = toBeSaved;
          draft.events =
            state.currentKey === 'ALL'
              ? draft.staticEvents
              : draft.staticEvents.filter(event => {
                  const sta = event.status.toLowerCase() === 'completed' ? 'sent' : 'draft';
                  return sta === state.currentKey.toLowerCase();
                });
        }
        // 2. add new one
        const newEvent = {
          tempId: uuid(),
          clinicName: state.clinicName,
          isEdit: true,
          template: '',
          selectedAppointments: [
            // appointments
          ],
          id: null,
          title: '無主旨',
          status: 'draft',
          sms: [],
          metadata: {
            template: '',
            selectedAppointments: [],
          },
        };
        const allEvents = [newEvent, ...snapShot];

        // 3. go to ALL section
        draft.currentKey = 'ALL';
        draft.staticEvents = allEvents;
        draft.events = allEvents;

        // 4. selecting manually
        draft.selectedEvent = newEvent;
        draft.selectedEventId = newEvent.tempId;
        draft.editingEvent = newEvent;
        break;
      }

      //#region edit
      case EDIT_TITLE:
        draft.editingEvent.title = action.value;
        break;
      case EDIT_TEMPLATE:
        draft.editingEvent.metadata.template = action.value;
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent);
        break;
      case ADD_TAG:
        const adding = ' {{' + action.value + '}}';
        const currentTemplate = state.editingEvent.metadata.template;
        if (draft.caretPosition !== -1) {
          if (draft.caretChangedBy === 'keydown') draft.caretPosition += 1;
          draft.editingEvent.metadata.template =
            currentTemplate.slice(0, draft.caretPosition) + adding + currentTemplate.slice(draft.caretPosition);
          // moving caret position to adding-end-position
          draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent);
          draft.caretPosition += adding.length;
          draft.caretChangedBy = null;
        }
        break;
      case ADD_CONTACT_APPOINTMENTS:
        const allApps = [...action.appointments, ...state.editingEvent.metadata.selectedAppointments];
        draft.editingEvent.metadata.selectedAppointments = allApps.filter(distinct);
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent);
        break;
      case UNSELECT_APPOINTMENT: {
        const newArr = [...state.editingEvent.metadata.selectedAppointments];
        const removeIdx = state.editingEvent.metadata.selectedAppointments.indexOf(action.key);
        newArr.splice(removeIdx, 1);
        draft.editingEvent.metadata.selectedAppointments = newArr;
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent);
        break;
      }
      //#endregion

      case TOGGLE_PREVIEWING_MODAL:
        draft.visible = !state.visible;
        break;

      case FILTER_EVENTS: {
        // when loading data do not let user do filtering
        if (!state.isLoaded) break;
        // locally save
        const snapShot = [...state.staticEvents];
        if (state.editingEvent !== null) {
          const toBeSaved = state.editingEvent;
          const replaceIdx = snapShot.indexOf(
            snapShot.find(ev =>
              ev.id !== null ? state.selectedEventId === ev.id : state.selectedEventId === ev.tempId,
            ),
          );
          snapShot[replaceIdx] = toBeSaved;
        }
        draft.selectedEvent = null;
        draft.selectedEventId = null;
        draft.editingEvent = null;
        draft.currentKey = action.key;
        draft.staticEvents = snapShot;
        draft.events =
          action.key === 'ALL'
            ? snapShot
            : snapShot.filter(event => {
                const sta = event.status.toLowerCase() === 'completed' ? 'sent' : 'draft';
                return sta === action.key.toLowerCase();
              });
        break;
      }
      case SET_CARET_POSITION:
        draft.caretPosition = action.payload.idx;
        draft.caretChangedBy = action.payload.by;
        break;
      default:
        break;
    }
  });

const processingEventSms = (clinicName, event) => {
  return event.metadata.selectedAppointments.map(app => {
    const content = decodeTemplate(clinicName, app, event.metadata.template);
    return {
      metadata: {
        patientId: app.patientId,
        patientName: app.patientName,
        appointmentDate: app.expectedArrivalTime,
        appointmentId: app.id,
      },
      phone: app.phone.trim().replace(/^09/, '+8869'),
      content,
    };
  });
};

const decodeTemplate = (clinicName, appointment, template) => {
  let content = template;
  initialState.tags.forEach(tag => {
    const word = '{{' + tag + '}}';
    const replaceable = new RegExp(word, 'g');
    if (tag === '約診日期') {
      content = content.replace(replaceable, moment(appointment.expectedArrivalTime).format('MM/DD'));
    } else if (tag === '約診時間') {
      content = content.replace(replaceable, moment(appointment.expectedArrivalTime).format('HH:mm'));
    } else if (tag === '病患姓名') {
      content = content.replace(replaceable, appointment.patientName);
    } else if (tag === '診所名稱') {
      content = content.replace(replaceable, clinicName);
    } else if (tag === '約診醫師') {
      content = content.replace(replaceable, appointment.doctor.user.firstName);
    } else if (tag === '約診內容') {
      content = content.replace(replaceable, !appointment.note ? '' : appointment.note);
    }
  });
  return content;
};

const distinct = (element, idx, array) => array.map(e => e.id).indexOf(element.id) === idx;

export default event;
