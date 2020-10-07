import React from 'react';
import { connect } from 'react-redux';
import { Container, Header, Content, Count, BlueDottedUnderlineText } from './component';
import { Table } from 'antd';
import { convertTreatmentProcedureToTableObject } from './utils';

//#region
//#endregion

const columns = [
  {
    title: '日期',
    dataIndex: 'date',
    key: 'date',
  },
  {
    title: '牙位',
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
  const { treatmentProcedures, loading, treatmentProceduresAmount } = props;

  return (
    <Container>
      <Header>
        <div>
          <span>近期治療</span>
          <Count>{treatmentProceduresAmount}</Count>
        </div>
        <BlueDottedUnderlineText text={'詳細資訊'} />
      </Header>
      <Content>
        <Table columns={columns} dataSource={treatmentProcedures} pagination={false} loading={loading} size="small" />
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  treatmentProcedures: convertTreatmentProcedureToTableObject(
    patientPageReducer.treatmentProcedure.treatmentProcedures,
  ),
  treatmentProceduresAmount: patientPageReducer.treatmentProcedure.treatmentProcedures
    ? patientPageReducer.treatmentProcedure.treatmentProcedures.length
    : 0,
  loading: patientPageReducer.treatmentProcedure.loading,
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailRercentTreatment);
