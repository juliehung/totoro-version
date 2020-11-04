import React from 'react';
import { connect } from 'react-redux';
import { Modal, Table, Badge } from 'antd';
import styled from 'styled-components';
import moment from 'moment';
import { changeAppointmentListModalVisible } from './actions';
import { convertAppointmentToCardObject } from './utils';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';

const appointmentStatus = { comming: 1, done: 2, cancel: 3, noShow: 4 };

const columns = doctors => [
  {
    title: '預約日期',
    dataIndex: 'expectedArrivalTime',
    key: 'expectedArrivalTime',
    sorter: (a, b) => moment(a.expectedArrivalTime) - moment(b.expectedArrivalTime),
    sortDirections: ['descend', 'ascend'],
    defaultSortOrder: 'descend',
  },
  {
    title: '需時',
    dataIndex: 'requiredTreatmentTime',
    key: 'requiredTreatmentTime',
    width: '10%',
  },
  {
    title: '主治醫師',
    dataIndex: 'doctor',
    key: 'doctor',
    render: doctor => doctor?.firstName ?? '',
    filters: doctors.map(d => ({ text: d.name, value: d.id })),
    onFilter: (value, record) => record?.doctor?.id === value,
  },
  {
    title: '備註',
    dataIndex: 'note',
    key: 'note',
    width: '40%',
  },
  {
    title: '狀態',
    dataIndex: 'status',
    key: 'status',
    render: status => {
      let renderObject = status;
      switch (status) {
        case appointmentStatus.comming:
          renderObject = (
            <span>
              <Badge status="processing" text="即將到來" />
            </span>
          );
          break;
        case appointmentStatus.done:
          renderObject = (
            <span>
              <Badge status="default" text="掛號完成" />
            </span>
          );
          break;
        case appointmentStatus.cancel:
          renderObject = (
            <span>
              <Badge status="warning" text="取消預約" />
            </span>
          );
          break;
        case appointmentStatus.noShow:
          renderObject = (
            <span>
              <Badge status="error" text="未到" />
            </span>
          );
          break;
        default:
          break;
      }

      return renderObject;
    },
  },
];

//#region
const StyledModal = styled(Modal)`
  & .ant-modal-content {
    border-radius: 8px;
  }
  & .ant-modal-header {
    border-radius: 8px 8px 0 0;
  }
  & .ant-modal-body {
    border-radius: 0 0 8px 8px;
  }
  & .ant-modal-close {
    &:active {
      transform: translateY(2px) translateX(-2px);
    }
  }
  & .ant-modal-close-x {
    width: 32px;
    height: 32px;
    position: absolute;
    top: -10px;
    right: -10px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 5px 20px 0 rgba(0, 0, 0, 0.1);
  }
  & .ant-modal-close-icon {
    display: flex;
    height: 100%;
    justify-content: center;
    align-items: center;
    & > svg {
      fill: black;
    }
  }
  & .ant-modal-header {
    background-color: #f8fafb;
  }
`;

const StyledTable = styled(Table)`
  & .ant-table-thead .ant-table-cell {
    background-color: #f7f9fc;
    &.ant-table-column-sort:hover {
      background-color: #e8f0fc;
    }
  }
`;

const Container = styled.div`
  height: 500px;
`;

const Title = styled.div`
  font-size: 18px;
  font-weight: bold;
  color: #222b45;
`;

//#endregion

function AppointmentListModal(props) {
  const { visible, appointments, patient, changeAppointmentListModalVisible, doctors } = props;

  const totalAppointmentAmount = appointments?.length ?? 0;

  const title = (
    <Title>
      <span>
        {patient?.name} 的 {totalAppointmentAmount} 筆預約紀錄
      </span>
    </Title>
  );

  return (
    <StyledModal
      centered
      visible={visible}
      width="80%"
      footer={false}
      title={title}
      maskClosable={false}
      onCancel={() => {
        changeAppointmentListModalVisible(false);
      }}
    >
      <Container>
        <StyledTable
          dataSource={appointments}
          columns={columns(doctors)}
          pagination={false}
          scroll={{ y: 400 }}
          rowKey={record => record.id}
          showSorterTooltip={false}
        />
      </Container>
    </StyledModal>
  );
}

const mapStateToProps = ({ patientPageReducer, homePageReducer }) => {
  const appointments = convertAppointmentToCardObject(
    patientPageReducer.appointment.appointment,
    homePageReducer.user.users,
  );

  const appointmentsWithStatus = appointments.map(a => {
    if (a.isCancel) {
      return { ...a, status: appointmentStatus.cancel };
    } else if (a.isRegistration) {
      return { ...a, status: appointmentStatus.done };
    } else if (a.isFuture) {
      return { ...a, status: appointmentStatus.comming };
    } else if (!a.isRegistration) {
      return { ...a, status: appointmentStatus.noShow };
    }
    return a;
  });

  return {
    appointments: appointmentsWithStatus,
    patient: patientPageReducer.patient.patient,
    visible: patientPageReducer.appointment.appointmentListModalVisible,
    doctors: extractDoctorsFromUser(homePageReducer.user.users) ?? [],
  };
};
const mapDispatchToProps = { changeAppointmentListModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(AppointmentListModal);
