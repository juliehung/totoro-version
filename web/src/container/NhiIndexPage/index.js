import React, { useState } from 'react';
import { Table, Tabs, Button, Popover } from 'antd';
import { connect } from 'react-redux';
import { getDoctorNhiExam, getDoctorNhiTx, getOdIndexes, getToothClean } from './actions';
import moment from 'moment';
import styled, { createGlobalStyle } from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { DayPickerRangeController } from 'react-dates';

const { TabPane } = Tabs;

const FOCUSINPUT = { startDate: 'startDate', endDate: 'endDate' };

const onFilterSerialNumber = (value, record) => {
  if (value) {
    return record.serialNumber;
  } else {
    return !record.serialNumber;
  }
};

const serialNumber = {
  title: '申報序號',
  dataIndex: 'serialNumber',
  key: 'serialNumber',
  filters: [
    { text: '未申報', value: false },
    { text: '已申報', value: true },
  ],
  onFilter: onFilterSerialNumber,
};

//#region
const GlobalStyle = createGlobalStyle`
  .CalendarDay__selected,
  .CalendarDay__selected:active,
  .CalendarDay__selected:hover {
    background: #3266ff !important;
    border: 1px transparent solid !important;
    color: #fff !important;
    font-weight:bold !important;
  }

  .CalendarDay__selected_span {
    background: rgba(50, 102, 255, 0.1) !important;
    border: 1px double rgba(50, 102, 255, 0.1) !important;
    color: #444 !important;
    font-weight:bold !important;
  }

  .CalendarDay__hovered_span,
  .CalendarDay__hovered_span:hover {
    background: rgba(50, 102, 255, 0.1) !important;
    border: 1px double rgba(50, 102, 255, 0.1) !important;
    color: #444 !important;
    font-weight:bold !important;

  }
`;

const TabsContainer = styled.div`
  width: 90%;
  margin: 0 auto;
`;

const InputDiv = styled.div`
  margin: 35px 0px 10px;
  display: flex;
  justify-content: center;
  align-items: center;

  & > span {
    font-size: 18px;
    border: 1px solid #ccc;
    padding: 2px 5px;
  }
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

const toothCleanColumns = doctors => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'did',
    filters: doctors.map(d => ({ value: d?.id, text: d?.firstName })),
    onFilter: (value, record) => value === record.did,
    render: did => doctors.find(d => d.id === did)?.firstName ?? did,
  },
  { title: '總件數', dataIndex: 'totalTime', key: 'timePatRate' },
  {
    title: '總人數',
    dataIndex: 'totalPat',
    key: 'totalPat',
  },
  { title: '洗牙人數比率', dataIndex: 'timePatRate', key: 'timePatRate', render: t => renderFloat2Position(t) },
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
  serialNumber,
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
  serialNumber,
];

function NhiIndexPage({
  doctors,
  odIndexes,
  doctorNhiExam,
  doctorNhiTx,
  getOdIndexes,
  getDoctorNhiExam,
  getDoctorNhiTx,
  getToothClean,
  toothClean,
}) {
  const [tabNumb, setTabNumb] = useState(1);
  const [startDate, setStartDate] = useState(moment().startOf('month'));
  const [endDate, setEndDate] = useState(moment());
  const [focusedInput, setFocusedInput] = useState(FOCUSINPUT.startDate);

  const calculateHandler = () => {
    if (!startDate || !endDate) return;
    getOdIndexes(startDate, endDate);
    getDoctorNhiExam(startDate, endDate);
    getDoctorNhiTx(startDate, endDate);
    getToothClean(startDate, endDate);
  };

  const toRocDate = date => {
    if (date?._isAMomentObject) {
      const year = date.year() - 1911;
      const dateString = date.format('MMM Do');
      return `${year}年${dateString}`;
    }
    return date;
  };

  return (
    <div>
      <Helmet>
        <title>健保</title>
      </Helmet>
      <GlobalStyle />
      <div>
        <InputDiv>
          <Popover
            trigger="click"
            placement="bottom"
            content={
              <DayPickerRangeController
                startDate={startDate}
                endDate={endDate}
                focusedInput={focusedInput}
                onFocusChange={focusedInput => {
                  setFocusedInput(!focusedInput ? FOCUSINPUT.startDate : focusedInput);
                }}
                onDatesChange={({ startDate, endDate }) => {
                  setStartDate(startDate);
                  setEndDate(endDate);
                }}
                numberOfMonths={2}
                hideKeyboardShortcutsPanel
                renderMonthElement={data => {
                  const date = data.month;
                  const year = date.year() - 1911;
                  const month = date.format('MMM');
                  return year + '年' + month;
                }}
              />
            }
          >
            <span type="primary">
              {toRocDate(startDate)} → {toRocDate(endDate)}
            </span>
          </Popover>
          <Button value="計算" onClick={calculateHandler} disabled={!startDate || !endDate} type="primary">
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
              />
            </TabPane>
            <TabPane tab="洗牙指標" key="2">
              <Table
                columns={toothCleanColumns(doctors)}
                dataSource={toothClean}
                pagination
                bordered
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.distinctTotalPat}`}
              />
            </TabPane>
            <TabPane tab="診察總覽" key="3">
              <Table
                columns={doctorNhiExamColumns(doctors)}
                dataSource={doctorNhiExam}
                pagination
                bordered
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.nhiExamCode}`}
              />
            </TabPane>
            <TabPane tab="診療總覽" key="4">
              <Table
                columns={doctorNhiTxColumns(doctors)}
                dataSource={doctorNhiTx}
                pagination
                bordered
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.nhiTxCode}`}
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
  toothClean: nhiIndexPageReducer.nhiIndex.toothClean,
  doctors: homePageReducer.user.users,
});

// map to actions
const mapDispatchToProps = {
  getOdIndexes,
  getDoctorNhiExam,
  getDoctorNhiTx,
  getToothClean,
};

export default connect(mapStateToProps, mapDispatchToProps)(NhiIndexPage);
