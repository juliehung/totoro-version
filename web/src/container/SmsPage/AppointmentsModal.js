import React, { useState, useEffect } from 'react';
import { CloseOutlined, LeftOutlined, RightOutlined } from '@ant-design/icons';
import { Table } from 'antd';
import styled from 'styled-components';
import { connect } from 'react-redux';
import moment from 'moment';
import { getAppointments, toggleAppointmentModal, addContactAppointments } from './action';
import { StyledMediumButton, StyledModal } from './StyledComponents';
import { P2 } from '../../utils/textComponents';
import { Default, O1 } from '../../utils/colors';
import DatePicker from '../../component/DatePicker';

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
  & > *:not(:first-child) {
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

const PhoneText = styled(P2)`
  &::before {
    content: ${props => (props.isInvalid ? '"📵 "' : '')};
  }
`;

const NoPageTable = styled(Table)`
  margin: 0 24px 24px 24px;
  & ul {
    display: none;
  }

  & colgroup {
    & col:first-child {
      width: ${props => props.selctionPlaceWidth + 'px'};
    }
  }

  & .ant-checkbox-input {
    &:hover {
      border-color: #36f;
    }
  }

  & .ant-table-body {
    min-height: ${props => (props.minHeight === null ? null : props.minHeight + 'px')};
    scrollbar-width: none; /* Firefox */
    -ms-overflow-style: none; /* IE 10+ */
    &::-webkit-scrollbar {
      width: 0px;
      background: transparent; /* Chrome/Safari/Webkit */
    }
  }

  /*checkbox hover*/
  & .ant-checkbox:hover .ant-checkbox-inner,
  & .ant-checkbox-checked:hover .ant-checkbox-inner {
    border-color: #36f;
  }

  & .ant-checkbox-checked {
    &::after {
      border-color: #36f;
      border-radius: 3px;
    }
  }

  & .ant-checkbox-checked .ant-checkbox-inner {
    background: #36f;
    border-color: #36f;
    border-radius: 3px;

    &::after {
      transform: rotate(45deg) scale(0.7) translate(-50%, -50%) translateY(-2px) translateX(-1.5px);
    }
  }

  & .ant-checkbox-disabled .ant-checkbox-inner {
    border-color: rgba(143, 155, 179, 0.4);
    background-color: rgba(143, 155, 179, 0.2);
  }

  & .ant-checkbox-indeterminate .ant-checkbox-inner {
    background: #36f;
    border-color: #36f;
    border-radius: 3px;

    &::after {
      background: white;
      height: 1px;
      width: 6px;
    }
  }

  & .ant-table-tbody {
    & .ant-table-placeholder {
      height: ${props => (props.dataSource.length === 0 ? props.minHeight + 'px' : null)};
    }

    & > tr > td {
    }

    & > tr.ant-table-row {
      & > td {
        color: ${Default};
      }
      &:hover > td {
        background: rgba(51, 102, 255, 0.08);
      }
    }

    & > tr.ant-table-row-selected {
      & > td {
        background: rgba(51, 102, 255, 0.08);
        color: #3366ff;
      }
    }
  }
`;

function AppointmentsModal(props) {
  const { getAppointments, toggleAppointmentModal, addContactAppointments, appointments, visible } = props;
  const [date, setDate] = useState(moment().startOf('day'));
  const [tempAppointments, setTempAppointments] = useState([]);

  useEffect(() => {
    if (visible) getAppointments({ start: moment(date), end: moment(date).add(1, 'days').add(1, 'seconds') });
  }, [date, visible, getAppointments]);

  const handleOk = () => {
    addContactAppointments(tempAppointments);
    toggleAppointmentModal();
    setTempAppointments([]);
  };

  const handleCancelAll = () => {
    setTempAppointments([]);
  };

  const selectChange = appointmentIds => {
    setTempAppointments(appointmentIds.map(id => appointments.find(app => app.id === id)).filter(app => app));
  };

  const rowSelection = {
    onChange: selectChange,
    hideDefaultSelections: true,
    selectedRowKeys: tempAppointments.map(app => app.id),
    getCheckboxProps: record => ({
      disabled: isInvalidPhone(record.phone),
    }),
  };

  const isInvalidPhone = phone => {
    return phone.trim().length !== 10 || phone.trim().substring(0, 2) !== '09';
  };

  const onRow = record => ({
    onClick: () => {
      if (isInvalidPhone(record.phone)) {
        return;
      }
      if (tempAppointments.find(t => t.id === record.id)) {
        setTempAppointments(tempAppointments.filter(t => t.id !== record.id));
      } else {
        setTempAppointments([...tempAppointments, record]);
      }
    },
  });

  const columns = [
    {
      title: '姓名',
      dataIndex: 'patientName',
      width: 140,
      sorter: (a, b) => a.patientName.localeCompare(b.patientName),
    },
    {
      title: '時間',
      dataIndex: 'expectedArrivalTime',
      render: time => moment(time).format('HH:mm'),
      width: 70,
      sorter: (a, b) => a.expectedArrivalTime.localeCompare(b.expectedArrivalTime),
    },
    {
      title: '號碼',
      dataIndex: 'phone',
      width: 130,
      sorter: (a, b) => a.phone.localeCompare(b.phone),
      render: phone => {
        const isInvalid = isInvalidPhone(phone);
        return (
          <PhoneText style={{ color: isInvalid ? O1 : Default }} isInvalid={isInvalid}>
            {phone}
          </PhoneText>
        );
      },
    },
    {
      title: '醫生',
      dataIndex: 'doctor',
      render: doctor => doctor.user.firstName,
      width: 100,
      sortDirections: ['descend', 'ascend'],
      sorter: (a, b) => a.doctor.user.firstName.localeCompare(b.doctor.user.firstName),
    },
    {
      title: '上次寄發',
      dataIndex: 'lastSent',
      render: lastSent => (lastSent ? moment(lastSent).format('YYYY/MM/DD HH:mm') : ''),
      width: 150,
      sortDirections: ['descend', 'ascend'],
      sorter: (a, b) => a.lastSent.localeCompare(b.lastSent),
    },
    {
      title: '預約內容',
      dataIndex: 'note',
      render: note => note,
      width: 250,
      sorter: (a, b) => {
        const aNote = a.note ?? '';
        const bNote = b.note ?? '';
        return aNote.localeCompare(bNote);
      },
    },
  ];

  return (
    <StyledModal
      width={1100}
      centered
      bodyStyle={{ padding: '0' }}
      visible={visible}
      footer={null}
      onCancel={toggleAppointmentModal}
    >
      <HeaderContainer>
        <Header>新增寄送對象</Header>
      </HeaderContainer>
      <DateContainer>
        <TitleText>約診日期：</TitleText>
        <LeftOutlined onClick={() => setDate(moment(date).add(-1, 'days'))} />
        <DatePicker date={date} onDateChange={setDate} readOnly />
        <RightOutlined onClick={() => setDate(moment(date).add(1, 'days'))} />
      </DateContainer>
      <NoPageTable
        selctionPlaceWidth={60}
        minHeight={300}
        size="small"
        scroll={{ y: 300 }}
        rowKey="id"
        pagination={{ pageSize: appointments.length }}
        rowSelection={rowSelection}
        dataSource={appointments}
        columns={columns}
        onRow={onRow}
      />
      {tempAppointments.length > 0 ? (
        <ActionContainer>
          <StyledMediumButton
            className="styled-medium-btn"
            style={{ border: 'solid 1px white', width: '86px', background: 'rgba(255,255,255,0.08)' }}
            type="primary"
            onClick={handleOk}
            shape="round"
          >
            插入
          </StyledMediumButton>
          <SelectionContainer>
            <CloseOutlined onClick={handleCancelAll} />
            <TitleText style={{ marginLeft: '24px' }}>{`已選取${tempAppointments.length}個項目`}</TitleText>
          </SelectionContainer>
        </ActionContainer>
      ) : (
        <div style={{ height: '56px' }} />
      )}
    </StyledModal>
  );
}

const mapStateToProps = ({ smsPageReducer }) => {
  return {
    appointments: smsPageReducer.appointment.appointments,
    visible: smsPageReducer.appointment.visible,
  };
};

const mapDispatchToProps = { getAppointments, toggleAppointmentModal, addContactAppointments };

export default connect(mapStateToProps, mapDispatchToProps)(AppointmentsModal);
