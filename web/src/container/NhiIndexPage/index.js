import React, { useEffect, useState } from 'react';
import { Table, Tabs } from 'antd';
import { connect, useDispatch } from 'react-redux';
import {
  getNhiSalary,
  getDoctorNhiSalary,
  getDoctorNhiExam,
  getDoctorNhiTx,
  getOdIndexes,
  getToothClean,
  getIndexTreatmentProcedure,
} from './actions';
import moment from 'moment';
import styled, { createGlobalStyle } from 'styled-components';
import { Helmet } from 'react-helmet-async';
import { parseIndexTreatmentProcedureToTableObject } from './utils/parseIndexTreatmentProcedureToTableObject';
import convertUserToNhiSalary from './utils/convertUserToNhiSalary';
import toRefreshExpandSalary from './utils/toRefreshExpandSalary';
import { getBaseUrl } from '../../utils/getBaseUrl';
import CloudDownload from '../../images/icon-cloud-download.svg';
import BarChart from '../../images/icon-bar-chart.svg';

const { TabPane } = Tabs;

const FOCUSINPUT = { startDate: 'startDate', endDate: 'endDate' };

// !TODO hide temperarily 1.2
// const onFilterSerialNumber = (value, record) => {
//   if (value) {
//     return record.serialNumber;
//   } else {
//     return !record.serialNumber;
//   }
// };

// const serialNumber = {
//   title: '申報序號',
//   dataIndex: 'serialNumber',
//   key: 'serialNumber',
//   filters: [
//     { text: '未申報', value: false },
//     { text: '已申報', value: true },
//   ],
//   onFilter: onFilterSerialNumber,
// };

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

const TitleContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 26px 0;

  > div {
    width: 33.33%;
    flex: 1;

    &:nth-child(2) {
      text-align: center;
    }
    &:nth-last-child(1) {
      text-align: right;
      display: flex;
      justify-content: flex-end;
      align-items: center;

      > div {
        padding: 10px 15px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;

        > span {
          padding-left: 10px;
          font-size: 14px;
          font-weight: bold;
          line-height: 1.14;
        }
        &:nth-child(1) {
          > span {
            color: #8f9bb3;
          }
        }
        &:nth-child(2) {
          > span {
            color: #ffffff;
          }
          min-width: 142px;
          height: 40px;
          margin: 0 0 0 22px;
          border-radius: 34px;
          background: #3266ff;
          text-align: center;
        }
      }
    }
    &:not(:last-child) {
      font-size: 30px;
      font-weight: 600;
      line-height: 1.33;
      color: #222b45;
    }
  }
`;

const TabsContainer = styled.div`
  margin: 0 auto;
  border-radius: 8px;
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.05);
  background: #fff;
  overflow: hidden;
  padding-bottom: 20px;
  max-height: 600px;

  .ant-tabs-tab {
    &:hover,
    &:focus,
    &:active,
    .ant-tabs-tab-btn:focus,
    .ant-tabs-tab-remove:focus,
    .ant-tabs-tab-btn:active {
      color: #1890ff !important;
    }
  }

  .ant-tabs-tab-active {
    color: #1890ff !important;

    .ant-tabs-tab-btn {
      color: #1890ff !important;
    }
  }
`;

const TabsWrap = styled(Tabs)`
  .ant-tabs-ink-bar {
    background: transparent;
  }

  .ant-tabs-ink-bar::after {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    left: 50%;
    transform: translateX(-50%);
    background: #1890ff;
  }
`;

const TableContainer = styled(Table)`
  padding: 0 24px 26px 24px;

  .ant-table-header {
    .ant-table-cell {
      background: #f7f9fc !important;
      font-size: 13px !important;
      font-weight: 600;
      line-height: 1.85;
      color: #222b45 !important;
    }
  }
  .ant-table-expanded-row {
    > .ant-table-cell {
      background: #e4eaff !important;
    }
  }
  .ant-table-cell {
    font-size: 13px !important;
    line-height: 1.38;
    color: #222b45 !important;
  }
`;

const TabBarExtraContentContainer = styled.div`
  font-size: 18px;
  font-weight: bold;
  line-height: 1.33;
  color: #222b45;
  padding: 24px;
  margin-right: 20px;
`;
const ExpandTableContainer = styled(Table)`
  padding: 0 24px 26px 24px;
  .ant-table-header {
    .ant-table-cell {
      background: #f7f9fc !important;
      font-size: 13px !important;
      font-weight: 600;
      line-height: 1.85;
      color: #222b45 !important;
    }
  }
  .ant-table-cell {
    background: #e4eaff !important;
  }
`;
//#endregion

const renderFloat2Position = f => (typeof f === 'number' ? (Math.round(f * 100) / 100).toFixed(2) : '0');
const odColumns = (doctors, filterDoctors) => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'odColumns-did',
    filters: filterDoctors.map(d => ({ value: d?.id, text: d?.firstName })),
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

const toothCleanColumns = (doctors, filterDoctors) => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'did',
    filters: filterDoctors.map(d => ({ value: d?.id, text: d?.firstName })),
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

const doctorNhiExamColumns = (doctors, filterDoctors) => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'doctorNhiExamColumns-did',
    filters: filterDoctors.map(d => ({ value: d?.id, text: d?.firstName })),
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
  // !TODO hide temperarily 1.2
  // serialNumber,
];

const doctorNhiTxColumns = (doctors, filterDoctors) => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'doctorNhiTxColumns-did',
    filters: filterDoctors.map(d => ({ value: d?.id, text: d?.firstName })),
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
  // !TODO hide temperarily 1.2
  // serialNumber,
];

const nhiSalaryColumns = () => [
  {
    title: '排序',
    dataIndex: 'doctorId',
    key: 'doctorId',
    width: 50,
    fixed: 'left',
    sorter: (a, b) => a.doctorId - b.doctorId,
  },
  {
    title: '醫師',
    dataIndex: 'doctorName',
    key: 'doctorName',
    width: 70,
    fixed: 'left',
    sorter: (a, b) => a.doctorId - b.doctorId,
  },
  {
    title: '合計',
    dataIndex: 'total',
    key: 'total',
    sorter: (a, b) => a.total - b.total,
    width: 50,
  },
  {
    title: '感控診察',
    dataIndex: 'infectionExaminationPoint',
    key: 'infectionExaminationPoint',
    sorter: (a, b) => a.infectionExaminationPoint - b.infectionExaminationPoint,
    width: 70,
  },
  {
    title: '未感控診察',
    dataIndex: 'regularExaminationPoint',
    key: 'regularExaminationPoint',
    sorter: (a, b) => a.regularExaminationPoint - b.regularExaminationPoint,
    width: 80,
  },
  {
    title: '診療費',
    dataIndex: 'treatmentPoint',
    key: 'treatmentPoint',
    sorter: (a, b) => a.treatmentPoint - b.treatmentPoint,
    width: 70,
  },
  {
    title: '部分負擔',
    dataIndex: 'copayment',
    key: 'copayment',
    sorter: (a, b) => a.copayment - b.copayment,
    width: 70,
  },
  {
    title: '牙周專科',
    dataIndex: 'perioPoint',
    key: 'perioPoint',
    sorter: (a, b) => a.perioPoint - b.perioPoint,
    width: 70,
  },
  {
    title: '兒童專科',
    dataIndex: 'pedoPoint',
    key: 'pedoPoint',
    sorter: (a, b) => a.pedoPoint - b.pedoPoint,
    width: 70,
  },
  {
    title: '根管專科',
    dataIndex: 'endoPoint',
    key: 'endoPoint',
    sorter: (a, b) => a.endoPoint - b.endoPoint,
    width: 70,
  },
  {
    title: '',
    dataIndex: 'totalDisposal',
    key: 'totalDisposal',
    width: 100,
    render: totalDisposal => `共計 ${!!totalDisposal ? totalDisposal : 0} 單處置`,
  },
];

// !TODO hide temperarily 1.2
// const treatmentProcedureColumn = doctors => [
//   {
//     title: '醫生',
//     dataIndex: 'did',
//     filters: doctors.map(d => ({ value: d?.id, text: d?.firstName })),
//     onFilter: (value, record) => value === record.did,
//     key: 'treatmentProcedure-did',
//     render: did => doctors.find(d => d.id === did)?.firstName ?? did,
//     sorter: {
//       compare: (a, b) => a.did - b.did,
//     },
//   },
//   {
//     title: '檢查點數',
//     dataIndex: 'examinationPoint',
//     key: 'treatmentProcedure-examinationPoint',
//   },
//   {
//     title: '檢查碼',
//     dataIndex: 'examinationCode',
//     key: 'treatmentProcedure-examinationCode',
//   },
//   {
//     title: 'a31',
//     dataIndex: 'a31',
//     key: 'treatmentProcedure-a31',
//   },
//   {
//     title: 'a32',
//     dataIndex: 'a32',
//     key: 'treatmentProcedure-a32',
//   },
//   {
//     ...serialNumber,
//     sorter: {
//       compare: (a, b) => a.serialNumber - b.serialNumber,
//     },
//   },
// ];

// !TODO hide temperarily 1.2
// const treatmentColumn = [
//   {
//     title: 'a71',
//     dataIndex: 'a71',
//     key: 'treatment-a71',
//   },
//   {
//     title: 'a72',
//     dataIndex: 'a72',
//     key: 'treatment-a72',
//   },
//   {
//     title: 'a73',
//     dataIndex: 'a73',
//     key: 'treatment-a73',
//   },
//   {
//     title: 'a74',
//     dataIndex: 'a74',
//     key: 'treatment-a74',
//   },
//   {
//     title: 'a75',
//     dataIndex: 'a75',
//     key: 'treatment-a75',
//   },
//   {
//     title: 'a76',
//     dataIndex: 'a76',
//     key: 'treatment-a76',
//   },
//   {
//     title: 'a77',
//     dataIndex: 'a77',
//     key: 'treatment-a77',
//   },
//   {
//     title: 'a78',
//     dataIndex: 'a78',
//     key: 'treatment-a78',
//   },
// ];

const expandedRowRender = expandSalary => {
  const columns = [
    {
      title: '排序',
      dataIndex: 'doctorId',
      key: 'doctorId',
      width: 50,
      fixed: 'left',
      sorter: (a, b) => a.doctorId - b.doctorId,
    },
    {
      title: '醫師',
      dataIndex: 'doctorName',
      key: 'doctorName',
      width: 70,
      fixed: 'left',
      sorter: (a, b) => a.doctorId - b.doctorId,
    },
    {
      title: '合計',
      dataIndex: 'total',
      key: 'total',
      sorter: (a, b) => a.total - b.total,
      width: 50,
    },
    {
      title: '感控診察',
      dataIndex: 'infectionExaminationPoint',
      key: 'infectionExaminationPoint',
      sorter: (a, b) => a.infectionExaminationPoint - b.infectionExaminationPoint,
      width: 70,
    },
    {
      title: '未感控診察',
      dataIndex: 'regularExaminationPoint',
      key: 'regularExaminationPoint',
      sorter: (a, b) => a.regularExaminationPoint - b.regularExaminationPoint,
      width: 80,
    },
    {
      title: '診療費',
      dataIndex: 'treatmentPoint',
      key: 'treatmentPoint',
      sorter: (a, b) => a.treatmentPoint - b.treatmentPoint,
      width: 70,
    },
    {
      title: '部分負擔',
      dataIndex: 'copayment',
      key: 'copayment',
      sorter: (a, b) => a.copayment - b.copayment,
      width: 70,
    },
    {
      title: '牙周專科',
      dataIndex: 'perioPoint',
      key: 'perioPoint',
      sorter: (a, b) => a.perioPoint - b.perioPoint,
      width: 70,
    },
    {
      title: '兒童專科',
      dataIndex: 'pedoPoint',
      key: 'pedoPoint',
      sorter: (a, b) => a.pedoPoint - b.pedoPoint,
      width: 70,
    },
    {
      title: '根管專科',
      dataIndex: 'endoPoint',
      key: 'endoPoint',
      sorter: (a, b) => a.endoPoint - b.endoPoint,
      width: 70,
    },
    {
      title: '',
      width: 100,
      render: record => (
        <a href={`${getBaseUrl()}#/patient/${expandSalary?.doctorId}`} target="_blank" rel="noopener noreferrer">
          {record?.patientName} 於 {moment(record?.disposalDate).format('YYYY/MM/DD')}
        </a>
      ),
    },
  ];
  return (
    <ExpandTableContainer
      loading={!expandSalary?.doctorOneSalary}
      columns={columns}
      showHeader={false}
      dataSource={expandSalary?.doctorOneSalary}
      pagination={false}
      tableLayout="fixed"
    />
  );
};

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
  getIndexTreatmentProcedure,
  // !TODO hide temperarily 1.2
  groupedIndexTreatmentProcedure,
  getNhiSalary,
  nhiSalary,
  expandNhiSalary,
}) {
  const dispatch = useDispatch();
  const [tabNumb, setTabNumb] = useState(1);
  const [startDate, setStartDate] = useState(moment().startOf('month'));
  const [endDate, setEndDate] = useState(moment());
  const filterDoctors = doctors.filter(({ id }) => odIndexes.map(({ did }) => did).indexOf(id) !== -1);
  doctorNhiExam = doctorNhiExam.map((data, index) => {
    return { ...data, key: `doctorNhiExam-${data.did}-${index}` };
  });
  doctorNhiTx = doctorNhiTx.map((data, index) => {
    return { ...data, key: `doctorNhiTx-${data.did}-${index}` };
  });
  console.log('groupedIndexTreatmentProcedure = ', groupedIndexTreatmentProcedure);

  const calculateHandler = () => {
    if (!startDate || !endDate) return;
    getIndexTreatmentProcedure(startDate, endDate);
  };

  useEffect(() => {
    if (startDate && endDate) {
      dispatch(getNhiSalary(startDate, endDate));
      dispatch(getOdIndexes(startDate, endDate));
      dispatch(getToothClean(startDate, endDate));
      dispatch(getDoctorNhiTx(startDate, endDate));
      dispatch(getDoctorNhiExam(startDate, endDate));
      dispatch(getIndexTreatmentProcedure(startDate, endDate));
    }
  }, [dispatch, startDate, endDate]);

  return (
    <div>
      <Helmet>
        <title>全民健保</title>
      </Helmet>
      <GlobalStyle />
      <TitleContainer>
        <div>健保點數與分析</div>
        <div>109年5月</div>
        <div>
          <div>
            <img src={CloudDownload} alt="cloud download" />
            <span>匯出成 EXCEL</span>
          </div>
          <div>
            <img src={BarChart} alt="icon-bar-chart" />
            <span>選擇計算來源</span>
          </div>
        </div>
      </TitleContainer>
      <div>
        <TabsContainer>
          <TabsWrap
            defaultActiveKey={tabNumb}
            onChange={setTabNumb}
            tabBarExtraContent={{
              left: <TabBarExtraContentContainer>醫師</TabBarExtraContentContainer>,
            }}
          >
            <TabPane tab="點數" key="1">
              <TableContainer
                scroll={{ y: 280 }}
                loading={!nhiSalary}
                columns={nhiSalaryColumns(doctors)}
                dataSource={nhiSalary}
                pagination={false}
                bordered={false}
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.doctorId} ${record.doctorName}`}
                onExpand={(expanded, record) => {
                  if (
                    expanded &&
                    (expandNhiSalary.length === 0 ||
                      (expandNhiSalary.length !== 0 &&
                        expandNhiSalary.filter(expandData => expandData?.doctorId === record.doctorId).length === 0))
                  ) {
                    dispatch(getDoctorNhiSalary(record.doctorId, startDate, endDate));
                  }
                }}
                expandable={{
                  expandedRowRender: record =>
                    expandedRowRender(
                      expandNhiSalary.filter(expandData => expandData?.doctorId === record.doctorId).length !== 0
                        ? expandNhiSalary.filter(expandData => expandData?.doctorId === record.doctorId)[0]
                        : {},
                    ),
                  rowExpandable: record => !!record?.doctorName,
                }}
              />
            </TabPane>
            <TabPane tab="補牙指標" key="2">
              <TableContainer
                scroll={{ y: 280 }}
                columns={odColumns(doctors, filterDoctors)}
                dataSource={odIndexes}
                pagination={false}
                bordered={false}
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.distinctTotalPat}`}
              />
            </TabPane>
            <TabPane tab="洗牙指標" key="3">
              <TableContainer
                scroll={{ y: 280 }}
                columns={toothCleanColumns(doctors, filterDoctors)}
                dataSource={toothClean}
                pagination={false}
                bordered={false}
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `${record.did} ${record.distinctTotalPat}`}
              />
            </TabPane>
            <TabPane tab="診察總覽" key="4">
              <TableContainer
                scroll={{ y: 280 }}
                columns={doctorNhiExamColumns(doctors, filterDoctors)}
                dataSource={doctorNhiExam}
                pagination={false}
                bordered={false}
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `doctorNhiExam - ${record.key}`}
              />
            </TabPane>
            <TabPane tab="診療總覽" key="5">
              <TableContainer
                scroll={{ y: 280 }}
                columns={doctorNhiTxColumns(doctors, filterDoctors)}
                dataSource={doctorNhiTx}
                pagination={false}
                bordered={false}
                showHeader={true}
                tableLayout="fixed"
                rowKey={record => `doctorNhiTx - ${record.key}`}
              />
            </TabPane>
          </TabsWrap>
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
  groupedIndexTreatmentProcedure: parseIndexTreatmentProcedureToTableObject(
    nhiIndexPageReducer.nhiIndex.indexTreatmentProcedure,
  ),
  nhiSalary: convertUserToNhiSalary(nhiIndexPageReducer.nhiIndex.nhiSalary, homePageReducer.user.users),
  expandNhiSalary: toRefreshExpandSalary(nhiIndexPageReducer.nhiIndex.expandNhiSalary),
});

// map to actions
const mapDispatchToProps = {
  getNhiSalary,
  getDoctorNhiSalary,
  getOdIndexes,
  getDoctorNhiExam,
  getDoctorNhiTx,
  getToothClean,
  getIndexTreatmentProcedure,
};

export default connect(mapStateToProps, mapDispatchToProps)(NhiIndexPage);
