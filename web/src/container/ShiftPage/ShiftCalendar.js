import React, { useEffect } from 'react';
import FullCalendar from '@fullcalendar/react';
import '@fullcalendar/core/main.css';
import interactionPlugin from '@fullcalendar/interaction';
import resourceTimelinePlugin from '@fullcalendar/resource-timeline';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';
import '@fullcalendar/list/main.css';
import { connect } from 'react-redux';
// import zhTW from '@fullcalendar/core/locales/zh-tw';
import styled from 'styled-components';
import '@fullcalendar/timeline/main.css';
import '@fullcalendar/resource-timeline/main.css';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';

//#region
const Container = styled.div`
  height: 100%;
  width: 100%;
`;
//#endregion

function ShiftCalendar(props) {
  useEffect(() => {
    const msg = document.querySelector('.fc-license-message');
    if (msg) {
      msg.parentNode.removeChild(msg);
    }
  });
  return (
    <Container>
      <FullCalendar
        height="auto"
        resources={props.doctors.map((d, i) => {
          return { id: i + 1, title: d.name };
        })}
        slotWidth={60}
        events={[
          {
            resourceId: 1,
            title: 'Lunch',
            start: '2020-03-21T13:00:00.000Z',
            end: '2020-03-21T14:00:00.000Z',
            description: '🍱',
            index: 'evt-0',
          },
          {
            resourceId: 2,
            title: 'Hair Appointment',
            start: '2020-03-21T10:30:00.000Z',
            end: '2020-03-21T12:30:00.000Z',
            description: '💇',
            index: 'evt-1',
          },
          {
            resourceId: 3,
            title: 'Nap Time',
            start: '2020-03-21T10:00:00.000Z',
            end: '2020-03-21T13:00:00.000Z',
            description: '😴',
            index: 'evt-2',
          },
          {
            resourceId: 1,
            title: 'Lunch',
            start: '2020-03-20T11:00:00.000Z',
            end: '2020-03-20T12:00:00.000Z',
            description: '🍲',
            index: 'evt-3',
          },
          {
            resourceId: 1,
            title: "Drink at Moe's",
            start: '2020-03-20T12:05:00.000Z',
            end: '2020-03-20T15:00:00.000Z',
            description: '🍺',
            index: 'evt-4',
          },
          {
            resourceId: 4,
            title: "Sling Drinks at Moe's",
            start: '2020-03-20T10:00:00.000Z',
            end: '2020-03-20T16:00:00.000Z',
            description: '🍺',
            index: 'evt-5',
          },
          {
            resourceId: 3,
            title: 'Rap Time',
            start: '2020-03-20T12:00:00.000Z',
            end: '2020-03-20T13:00:00.000Z',
            description: '🎤',
            index: 'evt-6',
          },
          {
            resourceId: 5,
            title: "Drink at Moe's",
            start: '2020-03-20T10:00:00.000Z',
            end: '2020-03-21T16:00:00.000Z',
            description: '🍺🍺🍺🍺🍺🍺🍺🍺',
            index: 'evt-7',
          },
          {
            resourceId: 4,
            title: "Sling Drinks at Moe's",
            start: '2020-03-21T10:00:00.000Z',
            end: '2020-03-21T16:00:00.000Z',
            description: '🍺',
            index: 'evt-8',
          },
        ]}
        selectable
        plugins={[interactionPlugin, resourceTimelinePlugin]}
        defaultView={'resourceTimelineDay'}
        views={{
          resourceTimelineFourDays: {
            type: 'resourceTimeline',
            duration: { days: 4 },
          },
        }}
        editable={true}
        resourceLabelText={'Doctor'}
        header={{
          left: 'prev,next',
          center: 'title',
          right: 'resourceTimelineDay,resourceTimelineWeek,resourceTimelineMonth',
        }}
        resourceAreaWidth={'20%'}
      />
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer }) => ({ doctors: extractDoctorsFromUser(homePageReducer.user.users) });

// const mapDispatchToProps = {};

export default connect(mapStateToProps)(ShiftCalendar);
