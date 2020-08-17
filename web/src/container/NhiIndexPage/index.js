import React, { useState } from 'react';
import { DatePicker, Table, Tabs, Button } from 'antd';
import { connect } from 'react-redux';
import { getDoctorNhiExam, getDoctorNhiTx, getOdIndexes } from './actions';
import moment from 'moment';
import styled from 'styled-components';
import { Helmet } from 'react-helmet-async';

const { RangePicker } = DatePicker;

const { TabPane } = Tabs;

//#region
const TabsContainer = styled.div`
  width: 90%;
  margin: 0 auto;
`;

const InputDiv = styled.div`
  margin: 35px 0px 10px;
  display: flex;
  justify-content: center;
`;
//#endregion

const renderFloat2Position = f => (typeof f === 'number' ? (Math.round(f * 100) / 100).toFixed(2) : '0');
const odColumns = doctors => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'odColumns-did',
    filters: doctors.map(d => ({ value: d?.id, text: d?.firstName })),
    onFilter: (value, record) => value === record.did,
    render: did => doctors.find(d => d.id === did)?.firstName ?? did,
  },
  {
    title: '顆數',
    dataIndex: 'totalTooth',
    key: 'odColumns-totalTooth',
  },
  {
    title: '人數',
    dataIndex: 'distinctTotalPat',
    key: 'odColumns-distinctTotalPat',
  },
  {
    title: '總人數',
    dataIndex: 'totalPat',
    key: 'odColumns-totalPat',
  },
  {
    title: '面數',
    dataIndex: 'totalSurface',
    key: 'odColumns-totalSurface',
  },
  {
    title: '平均一顆面數',
    dataIndex: 'toothPeopleRate',
    key: 'odColumns-toothPeopleRate',
    render: renderFloat2Position,
  },
  {
    title: '補牙患者平均填補總面數',
    dataIndex: 'surfacePeopleRate',
    key: 'odColumns-surfacePeopleRate',
    render: renderFloat2Position,
  },
  {
    title: '平均每人填補顆數',
    dataIndex: 'toothPeopleRate',
    key: 'odColumns-toothPeopleRate',
    render: renderFloat2Position,
  },
];

const doctorNhiExamColumns = doctors => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'doctorNhiExamColumns-did',
    filters: doctors.map(d => ({ value: d?.id, text: d?.firstName })),
    onFilter: (value, record) => value === record.did,
    render: did => doctors.find(d => d.id === did)?.firstName ?? did,
  },
  {
    title: '診察代碼',
    dataIndex: 'nhiExamCode',
    key: 'doctorNhiExamColumns-nhiExamCode',
  },
  {
    title: '診察點數',
    dataIndex: 'nhiExamPoint',
    key: 'doctorNhiExamColumns-nhiExamPoint',
  },
  {
    title: '總數',
    dataIndex: 'totalNumber',
    key: 'doctorNhiExamColumns-totalNumber',
  },
  {
    title: '總點數',
    dataIndex: 'totalPoint',
    key: 'doctorNhiExamColumns-totalPoint',
  },
];

const doctorNhiTxColumns = doctors => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'doctorNhiTxColumns-did',
    filters: doctors.map(d => ({ value: d?.id, text: d?.firstName })),
    onFilter: (value, record) => value === record.did,
    render: did => doctors.find(d => d.id === did)?.firstName ?? did,
  },
  {
    title: '診療代碼',
    dataIndex: 'nhiTxCode',
    key: 'doctorNhiTxColumns-nhiTxCode',
  },
  {
    title: '診療名稱',
    dataIndex: 'nhiTxName',
    key: 'doctorNhiTxColumns-nhiTxName',
  },
  {
    title: '診療點數',
    dataIndex: 'nhiTxPoint',
    key: 'doctorNhiTxColumns-nhiTxPoint',
  },
  {
    title: '總數',
    dataIndex: 'totalNumber',
    key: 'doctorNhiTxColumns-totalNumber',
  },
  {
    title: '總點數',
    dataIndex: 'totalPoint',
    key: 'doctorNhiTxColumns-did',
  },
];

function NhiIndexPage({
  doctors,
  odIndexes,
  doctorNhiExam,
  doctorNhiTx,
  getOdIndexes,
  getDoctorNhiExam,
  getDoctorNhiTx,
}) {
  const [range, setRange] = useState([moment().startOf('month'), moment()]);
  const [tabNumb, setTabNumb] = useState(1);

  const calculateHandler = () => {
    if (!range) return;
    const begin = range[0];
    const end = range[1];
    getOdIndexes(begin, end);
    getDoctorNhiExam(begin, end);
    getDoctorNhiTx(begin, end);
  };

  return (
    <div>
      <Helmet>
        <title>健保</title>
      </Helmet>
      <div>
        <InputDiv>
          <RangePicker placeholder={['起始日', '結束日']} onChange={setRange} value={range} allowClear={false} />
          <Button value="計算" onClick={calculateHandler} disabled={!range} type="primary">
            計算
          </Button>
        </InputDiv>
        <TabsContainer>
          <Tabs defaultActiveKey={tabNumb} onChange={setTabNumb}>
            <TabPane tab="補牙指標" key="1">
              <Table
                columns={odColumns(doctors)}
                dataSource={odIndexes}
                pagination
                bordered
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.distinctTotalPat}`}
                y={'100%'}
              />
            </TabPane>
            <TabPane tab="診察總覽" key="2">
              <Table
                columns={doctorNhiExamColumns(doctors)}
                dataSource={doctorNhiExam}
                pagination
                bordered
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.nhiExamCode}`}
                y={'100%'}
              />
            </TabPane>
            <TabPane tab="診療總覽" key="3">
              <Table
                columns={doctorNhiTxColumns(doctors)}
                dataSource={doctorNhiTx}
                // pagination
                bordered
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.nhiTxCode}`}
                y={'100%'}
              />
            </TabPane>
          </Tabs>
        </TabsContainer>
      </div>
    </div>
  );
}

const mapStateToProps = ({ nhiIndexPageReducer, homePageReducer }) => ({
  odIndexes: nhiIndexPageReducer.nhiIndex.odIndexes,
  doctorNhiExam: nhiIndexPageReducer.nhiIndex.doctorNhiExam,
  doctorNhiTx: nhiIndexPageReducer.nhiIndex.doctorNhiTx,
  doctors: homePageReducer.user.users,
});

// map to actions
const mapDispatchToProps = {
  getOdIndexes,
  getDoctorNhiExam,
  getDoctorNhiTx,
};

export default connect(mapStateToProps, mapDispatchToProps)(NhiIndexPage);
