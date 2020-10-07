import React, { useState } from 'react';
import { connect } from 'react-redux';
import { Modal, Table, Badge, Select } from 'antd';
import styled from 'styled-components';
import moment from 'moment';
import { changeAppointmentListModalVisible } from './actions';
import { convertAppointmentToCardObject } from './utils';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';

const { Option } = Select;

const appointmentStatus = { comming: 1, done: 2, cancel: 3, noShow: 4 };

const columns = [
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
  },
  {
    title: '主治醫師',
    dataIndex: 'doctor',
    key: 'doctor',
    render: doctor => {
      return doctor?.firstName;
    },
  },
  {
    title: '備註',
    dataIndex: 'note',
    key: 'note',
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
`;

const Container = styled.div`
  height: 500px;
`;

const Title = styled.div`
  font-size: 18px;
  font-weight: bold;
`;

const Sbutitle = styled.div`
  margin-bottom: 10px;
  display: flex;
  justify-content: space-between;
  color: #ccc;
`;

const StyledSelect = styled(Select)`
  width: 250px;
`;
//#endregion

function AppointmentListModal(props) {
  const { visible, appointments, patient, changeAppointmentListModalVisible, doctors } = props;
  const [selectedDoctorId, setSelectedDoctorId] = useState('all');

  const title = (
    <Title>
      <span>{patient?.name}</span>
      <span> 的預約紀錄</span>
    </Title>
  );

  const filteredAppointments =
    selectedDoctorId === 'all' ? appointments : appointments?.filter(a => a.doctor.id === selectedDoctorId) ?? [];

  const filteredAppointmentAmount = filteredAppointments?.length ?? 0;

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
        <Sbutitle>
          <span>共{filteredAppointmentAmount}筆預約</span>
          <StyledSelect placeholder="請選擇醫師" value={selectedDoctorId} onChange={setSelectedDoctorId}>
            {[
              <Option key={'all'} value={'all'}>
                全診所醫師
              </Option>,
              ...doctors.map(d => (
                <Option key={d.id} value={d.id}>
                  {d.name}
                </Option>
              )),
            ]}
          </StyledSelect>
        </Sbutitle>
        <Table
          dataSource={filteredAppointments}
          columns={columns}
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
    doctors: extractDoctorsFromUser(homePageReducer.user.users)?.filter(d => d.activated) ?? [],
  };
};
const mapDispatchToProps = { changeAppointmentListModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(AppointmentListModal);
