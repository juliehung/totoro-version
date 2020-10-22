import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Container, Header, Content, Count, Ic } from './component';
import { Table } from 'antd';
import { convertMedicalRecordsToTableObject } from './utils';

//#region
const StyledTable = styled(Table)`
  & .ant-table-thead .ant-table-cell {
    background-color: #f7f9fc;
    &.ant-table-column-sort:hover {
      background-color: #e8f0fc;
    }
  }
`;

//#endregion

const columns = [
  {
    title: '就醫日期',
    dataIndex: 'date',
    key: 'date',
  },
  {
    title: '就醫類別',
    dataIndex: 'medicalCategory',
    key: 'medicalCategory',
  },
  {
    title: '卡號',
    dataIndex: 'cardNumber',
    key: 'cardNumber',
  },
  {
    title: '院所',
    dataIndex: 'clinic',
  },
];

function PatientDetailAccumulatedMedicalRecord(props) {
  const { accumulatedMedicalRecords, loading } = props;

  const accumulatedMedicalRecordsAmount = accumulatedMedicalRecords?.length ?? 0;

  return (
    <Container>
      <Header>
        <div>
          <span>就醫累計資料</span>
          <Count>{accumulatedMedicalRecordsAmount}</Count>
          <Ic />
        </div>
      </Header>
      <Content>
        <StyledTable
          columns={columns}
          dataSource={accumulatedMedicalRecords}
          pagination={{ pageSize: 7, position: ['bottomLeft'], showSizeChanger: false }}
          size="small"
          loading={loading}
        />
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  accumulatedMedicalRecords: convertMedicalRecordsToTableObject(
    patientPageReducer.medicalRecord.nhiAccumulatedMedicalRecords,
  ),
  loading: patientPageReducer.treatmentProcedure.loading,
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailAccumulatedMedicalRecord);
