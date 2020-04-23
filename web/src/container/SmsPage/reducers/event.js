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
  SAVE_EVENT_AND_SEND_IMMEDIATELY,
  EXECUTE_EVENT_FAILED,
  DELETE_EVENT,
} from '../constant';

import produce from 'immer';
import moment from 'moment';
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
  remaining: null,
  isWrongNumberLength: false,
  isWrongContentLength: false,
  isChargeFailed: false,
  currentKey: 'ALL'
};

const initialState = {
  ...initState
};

const event = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case SAVE_EVENT:
      case EXECUTE_EVENT:
      case DELETE_EVENT:
      case SAVE_EVENT_AND_SEND_IMMEDIATELY:
        draft.isLoaded = false
        break
      // make sure editign stuff is clear 
      case GET_EVENTS:
        draft.selectedEvent = null
        draft.selectedEventId = null
        draft.editingEvent = null
        break
      case GET_CLINIC_SETTINGS_SUCCESS:
        draft.clinicName = action.settings.preferences.generalSetting.clinicName;
        draft.clinicId = action.settings.id
        break
      case GET_EVENTS_SUCCESS:
        var sorted = action.events.slice().sort((a, b) => (a.modifiedDate > b.modifiedDate) ? -1 : 1)
        draft.staticEvents = sorted
        draft.events = state.currentKey === 'ALL' ? 
          sorted : 
          sorted.filter(event => {
            var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
            return sta === state.currentKey.toLowerCase()
          })
        draft.selectedEvent = null
        draft.selectedEventId = null
        draft.editingEvent = null
        draft.isLoaded = true
        break;
      case GET_CLINIC_REMAINING_SUCCESS:
        draft.remaining = action.remaining.remaining
        break
      case EXECUTE_EVENT_FAILED:
        draft.isChargeFailed = true
        draft.isLoaded = true
        break;
      case SET_SELECT_EVENT: {
        if (action.payload.event === null){
          draft.selectedEvent = null
          draft.selectedEventId = null
          draft.editingEvent = null
          // close new
          if (action.payload.shouldDumpDraft) {
            var newArr = state.staticEvents.slice();
            newArr.shift();
            draft.staticEvents = newArr
            draft.events = state.currentKey === 'ALL' ? 
              newArr : 
              newArr.filter(event => {
                var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
                return sta === state.currentKey.toLowerCase()
              })
          }
          // close existing
          else if(state.editingEvent !== null) {
            draft.selectedEvent = state.editingEvent
            draft.selectedEventId = state.editingEvent.id
            draft.editingEvent = state.editingEvent
          }
        }
        else if (action.payload.event !== null) {
          draft.selectedEvent = action.payload.event
          draft.selectedEventId = action.payload.event.id
        }
              
        break;
      }
      case CREATE_EVENT:
        var newEvent = {
          clinicName: state.clinicName,
          isEdit: true,
          template: '',
          selectedAppointments: [
            // appointmnets
          ],
          id: null,
          title: '無主旨',
          status: 'DRAFT',
          sms: []
        };
        var allEvents = [newEvent, ...state.staticEvents]
        draft.currentKey = 'ALL'
        draft.staticEvents = allEvents
        draft.events = allEvents
        draft.selectedEvent = draft.events[0]
        draft.selectedEventId = draft.events[0].id
        draft.editingEvent = draft.events[0]
        break

      case EDIT_TITLE:
        draft.editingEvent.title = action.value
        break;
      case EDIT_TEMPLATE:
        draft.editingEvent.template = action.value
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70)
        break;
      case ADD_TAG:
        var adding = ' {{' + action.value + '}}'
        draft.editingEvent.template += adding
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70)
        break
      case ADD_CONTACT_APPOINTMENTS:
        const allApps = [...action.appointments, ...state.editingEvent.selectedAppointments]
        draft.editingEvent.selectedAppointments = allApps.filter(distinct)
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongNumberLength = draft.editingEvent.sms.some(m => checkPhoneFormat(m.phone))
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70)
        break
      case UNSELECT_APPOINTMENT: {
        const newArr = [...state.editingEvent.selectedAppointments]
        var removeIdx = state.editingEvent.selectedAppointments.indexOf(action.key)
        newArr.splice(removeIdx, 1);
        draft.editingEvent.selectedAppointments = newArr;
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongNumberLength = draft.editingEvent.sms.some(m => checkPhoneFormat(m.phone))
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70)
        break
      }

      case TOGGLE_PREVIEWING_MODAL:
        draft.visible = !state.visible
        // just init
        draft.isChargeFailed = false
        break
      case FILTER_EVENTS:
        draft.selectedEvent = null
        draft.selectedEventId = null
        draft.currentKey = action.key
        draft.events = action.key === 'ALL'? state.staticEvents : 
        state.staticEvents.filter(event => {
          var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
          return sta === action.key.toLowerCase()
        })
        break   

      default:
        break;
    }
  })

const processingEventSms = (clinicName, event) => {
 return event.selectedAppointments.map(app => {
  var content = decodeTemplate(clinicName, app, event.template);
  return {
    metadata: {
      patientId: app.patientId,
      patientName: app.patientName,
      appointmentDate: app.expectedArrivalTime,
    },
    phone: app.phone.replace(/^09/, '+8869'),
    content
  }
 })
}

const decodeTemplate = (clinicName, appointment, template) => {
  var content = template;
  initialState.tags.forEach(tag => {
    var word = '{{' + tag + '}}'
    var replaceable = new RegExp(word, "g");
    if (tag === '約診日期') {
      content = content.replace(replaceable, moment(appointment.expectedArrivalTime).format('MM/DD'))
    } else if (tag === '約診時間') {
      content = content.replace(replaceable, moment(appointment.expectedArrivalTime).format('HH:mm'))
    } else if (tag === '病患姓名') {
      content = content.replace(replaceable, appointment.patientName)
    } else if (tag === '診所名稱') {
      content = content.replace(replaceable, clinicName)
    } else if (tag === '約診醫師') {
      content = content.replace(replaceable, appointment.doctor.user.firstName)
    } else if (tag === '約診內容') {
      content = content.replace(replaceable, !appointment.note ? '' : appointment.note)
    }
  })
  return content;
}

const distinct = (element, idx, array) => array.map(e => e.id).indexOf(element.id) === idx;

const checkPhoneFormat = phone => !phone.startsWith('+8869') || phone.length !== 13

export default event;
