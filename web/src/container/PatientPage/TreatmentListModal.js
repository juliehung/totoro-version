import React from 'react';
import { connect } from 'react-redux';
import { Modal, Table } from 'antd';
import styled from 'styled-components';
import moment from 'moment';
import { changeTreatmentListModalVisible } from './actions';
import extractDoctorsFromUser from '../../utils/extractDoctorsFromUser';
import { convertDisposalsToTreatmentsAndPrescriptions, toRocString } from './utils';

const columns = doctors => [
  {
    title: '治療日期',
    dataIndex: 'createdDate',
    key: 'createdDate',
    sorter: (a, b) => moment(a.createdDate) - moment(b.createdDate),
    sortDirections: ['descend', 'ascend'],
    defaultSortOrder: 'descend',
    render: date => toRocString(date),
    fixed: true,
  },
  {
    title: '醫師',
    dataIndex: 'doctor',
    key: 'doctor',
    render: doctor => doctors.find(d => d.id === doctor.id)?.name ?? doctor.id,
    filters: doctors.map(d => ({ text: d.name, value: d.id })),
    onFilter: (value, record) => value === record.doctor.id,
    fixed: true,
  },
  {
    title: '類別',
    dataIndex: 'category',
    key: 'category',
    filters: [
      {
        text: '健保',
        value: '健保',
      },
      {
        text: '自費',
        value: '自費',
      },
      {
        text: '藥品',
        value: '藥品',
      },
    ],
    onFilter: (value, record) => value === record.category,
  },
  {
    title: '牙位(面)',
    dataIndex: 'teeth',
    key: 'teeth',
  },
  {
    title: '處置/項目/處方',
    dataIndex: 'title',
    key: 'title',
    width: '25%',
  },
  {
    title: '國際病碼/項目資訊/處方內容',
    dataIndex: 'content',
    key: 'content',
    width: '220px',
  },
  {
    title: '下次預約',
    dataIndex: 'revisitContent',
    key: 'revisitContent',
    width: '200px',
  },
  {
    title: '預約備註',
    dataIndex: 'revisitComment',
    key: 'revisitComment',
    width: '200px',
  },
  {
    title: '病歷紀錄',
    dataIndex: 'chiefComplaint',
    key: 'chiefComplaint',
    width: '200px',
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

const Container = styled.div`
  height: 500px;
`;

const Title = styled.div`
  font-size: 18px;
  font-weight: bold;
`;

const StyledTable = styled(Table)`
  & .ant-table-thead .ant-table-cell {
    background-color: #f7f9fc;
    &.ant-table-column-sort:hover {
      background-color: #e8f0fc;
    }
  }
`;

//#endregion

function TreatmentListModal(props) {
  const { visible, patient, changeTreatmentListModalVisible, doctors, treatmentsAndPrescriptions } = props;

  const totaltreatmentAndPrescriptionAmount = treatmentsAndPrescriptions?.length ?? 0;

  const title = (
    <Title>
      <span>{patient?.name}</span>
      <span>的 {totaltreatmentAndPrescriptionAmount} 筆治療歷史</span>
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
        <StyledTable
          dataSource={treatmentsAndPrescriptions}
          columns={columns(doctors)}
          pagination={false}
          scroll={{ y: 400, x: 'max-content' }}
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
    treatmentsAndPrescriptions: convertDisposalsToTreatmentsAndPrescriptions(patientPageReducer.disposal.disposals),
  };
};
const mapDispatchToProps = { changeTreatmentListModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(TreatmentListModal);
