import React, { useEffect, useState, useCallback } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { changeDate, getShift, createShift, editShift, changeResourceColor, getResourceColor } from './actions';
import ShiftPopover from './ShiftPopover';
import { message } from 'antd';
import Calendar from './Calendar';

//#region
const Container = styled.div`
  width: 100%;
  margin: 10px;
  .fc-license-message {
    display: none;
  }
  .fc-no-scrollbars {
    overflow: hidden !important;
  }
`;
//#endregion

function ShiftCalendar(props) {
  const {
    range,
    getShift,
    getResourceColor,
    createShiftSuccess,
    editShiftSuccess,
    changeColorSuccess,
    deleteSuccess,
    deleteDefaultShiftSuccess,
  } = props;

  const [popoverVisible, setPopoverVisible] = useState(false);

  const [clickInfo, setClickInfo] = useState({
    position: { x: undefined, y: undefined, vertical: undefined, horizontal: undefined },
    date: undefined,
    resourceId: undefined,
    color: undefined,
  });

  const [popoverSize, setPopoverSize] = useState({ height: undefined, width: undefined });

  const simulateMouseClick = element => {
    const mouseClickEvents = ['mousedown', 'click', 'mouseup'];
    mouseClickEvents.forEach(mouseEventType =>
      element.dispatchEvent(
        new MouseEvent(mouseEventType, {
          view: window,
          bubbles: true,
          cancelable: true,
          buttons: 1,
        }),
      ),
    );
  };

  const clickTitle = useCallback(() => {
    const title = document.querySelector('.fc-center');
    simulateMouseClick(title);
  }, []);

  useEffect(() => {
    const msg = document.querySelector('.fc-license-message');
    if (msg) {
      msg.parentNode.removeChild(msg);
    }
  });

  useEffect(() => {
    if (range.start && range.end) {
      getShift(range);
    }
    getResourceColor();
  }, [range, getShift, getResourceColor]);

  useEffect(() => {
    if (createShiftSuccess) {
      setPopoverVisible(false);
      message.success('新增成功');
    }
  }, [createShiftSuccess, setPopoverVisible]);

  useEffect(() => {
    if (editShiftSuccess) {
      message.success('更新成功');
    }
  }, [editShiftSuccess]);

  useEffect(() => {
    if (deleteSuccess) {
      message.warning('刪除成功');
    }
  }, [deleteSuccess]);

  useEffect(() => {
    if (deleteDefaultShiftSuccess) {
      message.warning('刪除成功');
    }
  }, [deleteDefaultShiftSuccess]);

  useEffect(() => {
    if (changeColorSuccess) {
      message.success('更新成功');
      clickTitle();
    }
    // clickTitle();
  }, [changeColorSuccess, clickTitle]);

  return (
    <Container>
      <Calendar
        clickInfo={clickInfo}
        setClickInfo={setClickInfo}
        popoverVisible={popoverVisible}
        setPopoverVisible={setPopoverVisible}
        changeColorSuccess={changeColorSuccess}
        popoverSize={popoverSize}
      />
      <ShiftPopover
        setVisible={setPopoverVisible}
        visible={popoverVisible}
        position={clickInfo.position}
        date={clickInfo.date}
        resourceId={clickInfo.resourceId}
        color={clickInfo.color}
        defaultShift={props.defaultShift}
        onConfirm={props.createShift}
        setSize={setPopoverSize}
        size={popoverSize}
      />
    </Container>
  );
}

const mapStateToProps = ({ homePageReducer, shiftPageReducer }) => ({
  resource: extractDoctorsFromUser(homePageReducer.user.users).map(d => ({ id: d.id, title: d.name })),
  range: shiftPageReducer.shift.range,
  defaultShift: shiftPageReducer.defaultShift.shift,
  createShiftSuccess: shiftPageReducer.shift.createShiftSuccess,
  deleteSuccess: shiftPageReducer.shift.deleteSuccess,
  editShiftSuccess: shiftPageReducer.shift.editShiftSuccess,
  changeColorSuccess: shiftPageReducer.resourceColor.changeColorSuccess,
  resourceColor: shiftPageReducer.resourceColor.color,
  deleteDefaultShiftSuccess: shiftPageReducer.defaultShift.deleteSuccess,
});

const mapDispatchToProps = {
  changeDate,
  getShift,
  createShift,
  editShift,
  changeResourceColor,
  getResourceColor,
};

export default connect(mapStateToProps, mapDispatchToProps)(ShiftCalendar);
