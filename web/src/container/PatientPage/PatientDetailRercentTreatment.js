import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Container, Header, Content, Count, BlueDottedUnderlineText } from './component';
import { Table } from 'antd';
import { convertTreatmentProcedureToTableObject, toRocString } from './utils';
import { changeTreatmentListModalVisible } from './actions';

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
    title: '日期',
    dataIndex: 'date',
    key: 'date',
    render: date => toRocString(date),
  },
  {
    title: '牙位(面)',
    dataIndex: 'teeth',
    key: 'teeth',
  },
  {
    title: '治療項目',
    dataIndex: 'treatment',
    key: 'treatment',
  },
  {
    title: '醫師',
    dataIndex: 'doctor',
    key: 'doctor',
  },
];

function PatientDetailRercentTreatment(props) {
  const { treatmentProcedures, loading, treatmentProceduresAmount, changeTreatmentListModalVisible } = props;

  return (
    <Container>
      <Header>
        <div>
          <span>近期治療</span>
          <Count>{treatmentProceduresAmount}</Count>
        </div>
        <div
          onClick={() => {
            changeTreatmentListModalVisible(true);
          }}
        >
          <BlueDottedUnderlineText text={'詳細資訊'} />
        </div>
      </Header>
      <Content>
        <StyledTable
          columns={columns}
          dataSource={treatmentProcedures}
          pagination={false}
          loading={loading}
          size="small"
          scroll={{ y: 300 }}
        />
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  treatmentProcedures: convertTreatmentProcedureToTableObject(
    patientPageReducer.treatmentProcedure.recentTreatmentProcedures,
  ),
  treatmentProceduresAmount: patientPageReducer.treatmentProcedure.recentTreatmentProcedures
    ? patientPageReducer.treatmentProcedure.recentTreatmentProcedures.length
    : 0,
  loading: patientPageReducer.treatmentProcedure.loading,
});

const mapDispatchToProps = { changeTreatmentListModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailRercentTreatment);
