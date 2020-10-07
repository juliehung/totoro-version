import React, { useState } from 'react';
import { connect } from 'react-redux';
import { Container, Header, Content, Count, Ic } from './component';
import { Switch, Table } from 'antd';
import { convertNhiExtentPatientToTableObject } from './utils';

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
    key: 'name',
  },
  {
    title: '治療項目',
    dataIndex: 'nhiCode',
  },
];

function PatientDetailHelthICCardTreatmentRecord(props) {
  const { nhiMedicalRecords, loading } = props;
  const [dentalSwitch, setDentalSwitch] = useState(false);

  const filteredNhiMedicalRecords = dentalSwitch ? nhiMedicalRecords.filter(n => n.isDental) : nhiMedicalRecords;
  const nhiMedicalRecordsAmount = filteredNhiMedicalRecords?.length ?? 0;

  return (
    <Container>
      <Header>
        <div>
          <span>IC健保卡內容</span>
          <Count>{nhiMedicalRecordsAmount}</Count>
          <Ic />
        </div>
        <div>
          <Switch
            size="small"
            checked={dentalSwitch}
            onChange={() => {
              setDentalSwitch(!dentalSwitch);
            }}
          />
          <span>只顯示牙科</span>
        </div>
      </Header>
      <Content>
        <Table
          columns={columns}
          dataSource={filteredNhiMedicalRecords}
          pagination={{ pageSize: 7, position: ['bottomLeft'], showSizeChanger: false }}
          size="small"
          loading={loading}
        />
      </Content>
    </Container>
  );
}

const mapStateToProps = ({ patientPageReducer, homePageReducer }) => ({
  nhiMedicalRecords: convertNhiExtentPatientToTableObject(
    patientPageReducer.nhiExtendPatient.nhiExtendPatient,
    homePageReducer.nhiProcedure.nhiProcedure,
  ),
  loading: patientPageReducer.nhiExtendPatient.loading,
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetailHelthICCardTreatmentRecord);
