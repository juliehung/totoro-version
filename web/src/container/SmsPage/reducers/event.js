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
  SAVE_EVENT_SUCCESS,
  DELETE_EVENT_SUCCESS,
} from '../constant';

import produce from 'immer';
import moment from 'moment';
import uuid from 'react-uuid'

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
      
      // make sure editign stuff is clear 
      case GET_EVENTS:
        draft.selectedEvent = null
        draft.selectedEventId = null
        draft.editingEvent = null
        draft.isLoaded = false
        break
      case SAVE_EVENT:
      case DELETE_EVENT:
      case EXECUTE_EVENT:
      case SAVE_EVENT_AND_SEND_IMMEDIATELY:
        draft.isLoaded = false
        break

      case GET_EVENTS_SUCCESS: {
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
      }
    
      case SAVE_EVENT_SUCCESS: {
        const snapShot = [...state.staticEvents]
        const identity = action.payload.identity
        var toBePlaced = action.payload.result
        // stilling editing ?
        if (identity === state.editingEvent.id || identity === state.editingEvent.tempId) {
          toBePlaced = {...state.editingEvent, ...toBePlaced}
        }
        const replaceIdx = snapShot.indexOf(snapShot.find(ev => ev.id !== null ? identity === ev.id : identity === ev.tempId))
        snapShot[replaceIdx] = toBePlaced
        draft.staticEvents = snapShot
        draft.events = state.currentKey === 'ALL' ? 
          draft.staticEvents : 
          draft.staticEvents.filter(event => {
            var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
            return sta === state.currentKey.toLowerCase()
          })
        draft.selectedEvent = draft.staticEvents[replaceIdx]
        draft.editingEvent = toBePlaced
        draft.isLoaded = true
        break
      }

      case DELETE_EVENT_SUCCESS: {
        let snapShot = [...state.staticEvents]
        const identity = action.identity
       
        snapShot = snapShot.filter(event => event.id !== identity)
        draft.staticEvents = snapShot
        draft.events = state.currentKey === 'ALL' ? 
          draft.staticEvents : 
          draft.staticEvents.filter(event => {
            var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
            return sta === state.currentKey.toLowerCase()
          })

          draft.selectedEvent = null
          draft.selectedEventId = null
          draft.editingEvent = null
          draft.isLoaded = true
        break
      }

      case EXECUTE_EVENT_FAILED: {
        draft.isChargeFailed = true
        draft.isLoaded = true
        break
      }

   
      case GET_CLINIC_SETTINGS_SUCCESS: {
        draft.clinicName = action.settings.preferences.generalSetting.clinicName;
        draft.clinicId = action.settings.id
        break
      }

      case GET_CLINIC_REMAINING_SUCCESS: {
        draft.remaining = action.remaining.remaining
        break
      }

      case SET_SELECT_EVENT: {     
        // 1. cleaning the view 
        if (action.event === null){
          draft.selectedEvent = null
          draft.selectedEventId = null
          draft.editingEvent = null
          // if it doesn't have id, delete locally here.
          // if it has id => go saga > event > deleteEvent
          if(state.selectedEvent.id === null) {
            var newArr = state.staticEvents.filter(event => state.selectedEventId !== event.tempId)
            draft.staticEvents = newArr
            draft.events = state.currentKey === 'ALL' ? 
              draft.staticEvents : 
              draft.staticEvents.filter(event => {
                var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
                return sta === state.currentKey.toLowerCase()
              })
          }
        // 2. changing the view
        } else if (action.event !== null) {
          // 2-2. 
          var selection = {...action.event};
          if (selection.status === 'draft') selection.isEdit = true
          draft.selectedEvent = selection
          draft.selectedEventId = selection.id !== null ? selection.id : selection.tempId
          draft.editingEvent = selection.isEdit ? selection : null
        }
        break;
      }

      case CREATE_EVENT: {
        // 1. save existing
        const snapShot = [...state.staticEvents]
        if (state.editingEvent !== null) {
          const toBeSaved = state.editingEvent
          const replaceIdx = snapShot.indexOf(snapShot.find(ev => ev.id !== null ? state.selectedEventId === ev.id : state.selectedEventId === ev.tempId))
          snapShot[replaceIdx] = toBeSaved
          draft.events = state.currentKey === 'ALL' ? 
            draft.staticEvents : 
            draft.staticEvents.filter(event => {
              var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
              return sta === state.currentKey.toLowerCase()
            })
        }
        // 2. add new one
        var newEvent = {
          tempId: uuid(),
          clinicName: state.clinicName,
          isEdit: true,
          template: '',
          selectedAppointments: [
            // appointmnets
          ],
          id: null,
          title: '無主旨',
          status: 'draft',
          sms: [],
          metadata: {
            template: '',
            selectedAppointments: []
          }
        };
        var allEvents = [newEvent, ...snapShot]

        // 3. go to ALL section
        draft.currentKey = 'ALL'
        draft.staticEvents = allEvents
        draft.events = allEvents
          
        // 4. selecting manually
        draft.selectedEvent = newEvent
        draft.selectedEventId = newEvent.tempId
        draft.editingEvent = newEvent
        break
      }

      //#region edit
      case EDIT_TITLE:
        draft.editingEvent.title = action.value
        break;
      case EDIT_TEMPLATE:
        draft.editingEvent.metadata.template = action.value
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70) || draft.editingEvent.metadata.template.length === 0
        break;
      case ADD_TAG:
        var adding = ' {{' + action.value + '}}'
        draft.editingEvent.metadata.template += adding
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70) || draft.editingEvent.metadata.template.length === 0
        break
      case ADD_CONTACT_APPOINTMENTS:
        const allApps = [...action.appointments, ...state.editingEvent.metadata.selectedAppointments]
        draft.editingEvent.metadata.selectedAppointments = allApps.filter(distinct)
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongNumberLength = draft.editingEvent.sms.some(m => checkPhoneFormat(m.phone))
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70) || draft.editingEvent.metadata.template.length === 0
        break
      case UNSELECT_APPOINTMENT: {
        const newArr = [...state.editingEvent.metadata.selectedAppointments]
        var removeIdx = state.editingEvent.metadata.selectedAppointments.indexOf(action.key)
        newArr.splice(removeIdx, 1);
        draft.editingEvent.metadata.selectedAppointments = newArr;
        draft.editingEvent.sms = processingEventSms(draft.clinicName, draft.editingEvent)
        draft.isWrongNumberLength = draft.editingEvent.sms.some(m => checkPhoneFormat(m.phone))
        draft.isWrongContentLength = draft.editingEvent.sms.some(m => m.content.length > 70) || draft.editingEvent.metadata.template.length === 0
        break
      }
      //#endregion


      case TOGGLE_PREVIEWING_MODAL:
        draft.visible = !state.visible
        // just init
        draft.isChargeFailed = false
        break

      case FILTER_EVENTS: {
        // when loading data do not let user do filtering
        if (!state.isLoaded) break
        // locally save
        const snapShot = [...state.staticEvents]
        if (state.editingEvent !== null) {
          const toBeSaved = state.editingEvent
          const replaceIdx = snapShot.indexOf(snapShot.find(ev => ev.id !== null ? state.selectedEventId === ev.id : state.selectedEventId === ev.tempId))
          snapShot[replaceIdx] = toBeSaved
        }
        draft.selectedEvent = null
        draft.selectedEventId = null
        draft.editingEvent = null
        draft.currentKey = action.key
        draft.staticEvents = snapShot
        draft.events = action.key === 'ALL'? 
          snapShot : 
          snapShot.filter(event => {
            var sta = event.status.toLowerCase() === 'completed' ? 'sent': 'draft'
            return sta === action.key.toLowerCase()
          })
        break   
      }

      default:
        break;
    }
  })

const processingEventSms = (clinicName, event) => {
 return event.metadata.selectedAppointments.map(app => {
  var content = decodeTemplate(clinicName, app, event.metadata.template);
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
