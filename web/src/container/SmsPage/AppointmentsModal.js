import React, { useState, useEffect } from 'react';
import { CloseOutlined, LeftOutlined, RightOutlined } from '@ant-design/icons';
import { DatePicker, Table } from 'antd';
import styled from 'styled-components';
import { connect } from 'react-redux';
import moment from 'moment';
import { getAppointments, toggleAppointmentModal, addContactAppointments } from './action';
import { StyledMediumButton, StyledModal } from './StyledComponents'

const NoMarginText = styled.p`
  margin: auto 0;
`;

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  background: #f8fafb;
  height: 56px;
  padding: 0 24px;
  border-radius: 4px 4px 0 0;
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
  border-radius: 0 0 4px 4px;
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

const NoPageTable = styled(Table)`
  margin: 0 24px 24px 24px;
  & ul {
    display: none;
  }
  & .ant-table-body {
    min-height: ${props => props.minHeight === null ? null : props.minHeight + 'px'};
    scrollbar-width: none; /* Firefox */
    -ms-overflow-style: none;  /* IE 10+ */
    &::-webkit-scrollbar {
      width: 0px;
      background: transparent; /* Chrome/Safari/Webkit */
    }
  }

  & .ant-table-tbody {
    height: ${props => props.dataSource.length === 0 ? props.minHeight + 'px' : null};
  }
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
    { title: '姓名', dataIndex: 'patientName', width: 140,
      sorter: (a, b) => a.patientName.localeCompare(b.patientName)
    },
    { title: '時間', dataIndex: 'expectedArrivalTime', 
      render: time => moment(time).format('HH:mm'), width: 100, 
      sorter: (a, b) => a.expectedArrivalTime.localeCompare(b.expectedArrivalTime)
    },
    { title: '號碼', dataIndex: 'phone', width: 100,
      sorter: (a, b) => a.phone.localeCompare(b.phone)
    },
    { title: '醫生', dataIndex: 'doctor', 
      render: doctor => doctor.user.firstName, width: 100,
      sortDirections: ['descend', 'ascend'],
      sorter: (a, b) => a.doctor.user.firstName.localeCompare(b.doctor.user.firstName)
    },
    // { title: '上次寄發', dataIndex: 'note', render: note => '?', width: 140},
    { title: '預約內容', dataIndex: 'note', render: note => note, width: 250,
      sorter: (a, b) => {
        var aNote = a.note ?? ''
        var bNote = b.note ?? ''
        return aNote.localeCompare(bNote)
      }
    },
  ];

  return ( 
    <StyledModal
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
      <NoPageTable
        minHeight={300}
        size="small"
        scroll={{y: 300}}
        rowKey="id"
        pagination={{ pageSize: appointments.length }}
        rowSelection={rowSelection} 
        dataSource={appointments} 
        columns={columns} />
      <ActionContainer>
        <StyledMediumButton
          className="styled-medium-btn"
          style={{ border: 'solid 1px white', width: '86px', background: 'rgba(255,255,255,0.08)'  }}
          type="primary" 
          onClick={handleOk} 
          shape="round">
            插入
        </StyledMediumButton>
        {tempAppointments.length > 0? 
          <SelectionContainer>
            <CloseOutlined onClick={handleCancelAll} />
            <TitleText style={{ marginLeft: '24px' }}>{`已選取${tempAppointments.length}個項目`}</TitleText> 
          </SelectionContainer> : 
          null 
        }
      </ActionContainer>
  </StyledModal>
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
