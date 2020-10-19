import React, { useState } from 'react';
import { connect } from 'react-redux';
import { Modal, Table, Select } from 'antd';
import styled from 'styled-components';
import moment from 'moment';
import { changeTreatmentListModalVisible } from './actions';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { convertDisposalsToTreatmentsAndPrescriptions } from './utils';

const { Option } = Select;

const columns = [
  {
    title: '建立日期',
    dataIndex: 'expectedArrivalTime',
    key: 'expectedArrivalTime',
    sorter: (a, b) => moment(a.expectedArrivalTime) - moment(b.expectedArrivalTime),
    sortDirections: ['descend', 'ascend'],
    defaultSortOrder: 'descend',
  },
  {
    title: '醫師',
    dataIndex: 'doctor',
    key: 'doctor',
  },
  {
    title: '類別',
    dataIndex: 'category',
    key: 'category',
  },
  {
    title: '牙位(面)',
    dataIndex: 'teeth',
    key: 'teeth',
  },
  {
    title: '處置/項目/處方',
    dataIndex: 'disposal',
    key: 'title',
  },
  {
    title: '國際病碼/項目資訊/處方內容',
    dataIndex: 'content',
    key: 'content',
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

function TreatmentListModal(props) {
  const { visible, patient, changeTreatmentListModalVisible, doctors } = props;

  const [selectedDoctorId, setSelectedDoctorId] = useState('all');

  const title = (
    <Title>
      <span>{patient?.name}</span>
      <span> 的處置歷史</span>
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
        changeTreatmentListModalVisible(false);
      }}
    >
      <Container>
        <Sbutitle>
          <span>共{123}筆預約</span>
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
          dataSource={[]}
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
  return {
    patient: patientPageReducer.patient.patient,
    visible: patientPageReducer.disposal.treatmentListModalVisible,
    doctors: extractDoctorsFromUser(homePageReducer.user.users)?.filter(d => d.activated) ?? [],
    treatmentProcedures: convertDisposalsToTreatmentsAndPrescriptions(patientPageReducer.disposal.disposals),
  };
};
const mapDispatchToProps = { changeTreatmentListModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentListModal);
