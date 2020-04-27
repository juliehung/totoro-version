import React, { useState, useEffect } from 'react';
import { CloseOutlined, LeftOutlined, RightOutlined } from '@ant-design/icons';
import { Modal, DatePicker, Table } from 'antd';
import styled from 'styled-components';
import { connect } from 'react-redux';
import moment from 'moment';
import { getAppointments, toggleAppointmentModal, addContactAppointments } from './action';
import { StyledLargerButton } from './Button'
const NoMarginText = styled.p`
  margin: auto 0;
`;

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  background: ##f8fafb;
  height: 56px;
  margin: 0 24px;
`;

const Header = styled(NoMarginText)`
  font-size: 18px;
  font-weight: bold;
`;

const DateContainer = styled.div`
  display: flex;
  align-items: center;
  height: 60px;
  margin: 0 24px;
  & *:not(:first-child){
    margin-right: 8px;
  }
`;

const ActionContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #3266ff;
  height: 56px;
  padding: 0 24px;
  flex-direction: row-reverse;
`;

const SelectionContainer = styled.div`
  display: flex;
  align-items: center;
  color: white;
`;

const TitleText = styled(NoMarginText)`
  font-size: 15px;
  font-weight: 600;
`;

function AppointmentsModal(props) {
  const { getAppointments, toggleAppointmentModal, addContactAppointments, appointments, visible } = props
  const [ date, setDate ] = useState(moment().startOf('day'));
  const [ tempAppointments, setTempAppointments ] = useState([]);
  
  useEffect(() => {
    if (visible) getAppointments({start: moment(date), end: moment(date).add(1, 'days').add(1, 'seconds')});
    // eslint-disable-next-line
  }, [date])

  const handleOk = () => {
    addContactAppointments(tempAppointments);
    toggleAppointmentModal()
    setTempAppointments([])
  }

  const handleCancelAll= () => {
    setTempAppointments([]);
  }

  const selectChange = appointmentIds => {
    setTempAppointments(appointmentIds.map(id => appointments.find(app => app.id === id)).filter(app => app));
  };
  
  const rowSelection = {
    onChange: selectChange,
    hideDefaultSelections: true,
    selections: [
      Table.SELECTION_ALL,
    ],
    selectedRowKeys: tempAppointments.map(app => app.id)
  };

  const columns = [
    { title: '姓名', dataIndex: 'patientName', width: 140 },
    { title: '時間', dataIndex: 'expectedArrivalTime', render: time => moment(time).format('HH:mm'), width: 100 },
    { title: '號碼', dataIndex: 'phone', width: 100 },
    { title: '醫生', dataIndex: 'doctor', render: doctor => doctor.user.firstName, width: 100},
    // { title: '上次寄發', dataIndex: 'note', render: note => '?', width: 140},
    { title: '預約內容', dataIndex: 'note', render: note => note, width: 250},
  ];

  return ( 
    <Modal
      width={900}
      centered
      bodyStyle={{ padding: '0' }}
      visible={visible}
      footer={null}
      onCancel={toggleAppointmentModal}
    >
      <HeaderContainer>
        <Header>
          新增寄送對象
        </Header>
      </HeaderContainer>
      <DateContainer>
        <TitleText>約診日期：</TitleText>
        <LeftOutlined onClick={()=> setDate(moment(date).add(-1, 'days'))}/>
        <DatePicker value={date} onChange={setDate} />
        <RightOutlined onClick={()=> setDate(moment(date).add(1, 'days'))}/>
      </DateContainer>
      <Table
        size="small"
        style={{ margin: '0 24px' }}
        rowKey="id"
        rowSelection={rowSelection} 
        dataSource={appointments} 
        columns={columns} />
      <ActionContainer>
        <StyledLargerButton
          className="styled-larger-btn"
          style={{ border: 'solid 1px white', width: '86px' }}
          type="primary" 
          onClick={handleOk} 
          shape="round">
            插入
        </StyledLargerButton>
        {tempAppointments.length > 0? 
          <SelectionContainer>
            <CloseOutlined onClick={handleCancelAll} />
            <TitleText style={{ marginLeft: '24px' }}>{`已選取${tempAppointments.length}個項目`}</TitleText> 
          </SelectionContainer> : 
          null 
        }
      </ActionContainer>
  </Modal>
  );
}

const mapStateToProps = ({ smsPageReducer }) => {
  return { 
    appointments: smsPageReducer.appointment.appointments,
    visible: smsPageReducer.appointment.visible 
  }
};

const mapDispatchToProps = { getAppointments, toggleAppointmentModal, addContactAppointments };

export default connect(mapStateToProps, mapDispatchToProps)(AppointmentsModal);
