import React, { useEffect, useState, Suspense } from 'react';
import moment from 'moment';
import { DatePicker, Modal, Table, Tabs, Input, Menu, Dropdown, Button, Spin } from 'antd';
import { connect, useDispatch } from 'react-redux';
import { BarChart, Bar, XAxis, Rectangle, Tooltip, Cell, ResponsiveContainer } from 'recharts';
import styled, { createGlobalStyle } from 'styled-components';
import { Helmet } from 'react-helmet-async';
import {
  initNhiSalary,
  getNhiSalary,
  getDoctorNhiSalary,
  getValidNhiByYearMonth,
  getNhiOneByDisposalId,
} from './actions';
import { getBaseUrl } from '../../utils/getBaseUrl';
import convertUserToNhiSalary from './utils/convertUserToNhiSalary';
import toRefreshExpandSalary from './utils/toRefreshExpandSalary';
import getCurrentMonthPoint from './utils/getCurrentMonthPoint';
import toRefreshValidNhiData from './utils/toRefreshValidNhiData';
import toRefreshNhiOne from './utils/toRefreshNhiOne';
import CloudDownload from '../../images/icon-cloud-download.svg';
import IconBarChart from '../../images/icon-bar-chart.svg';
import { ReactComponent as ArrowDown } from '../../images/1-2-icon-his-icons-arrow-down-fill.svg';

const NhiDataListRender = React.lazy(() => import('./nhiDataList'));

const { TabPane } = Tabs;
const { Search } = Input;

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

const ModalContainer = styled(Modal)`
  .ant-modal-body {
    padding: 0;
  }
  .cancel-modal-btn {
    width: 64px;
    height: 40px;
    margin: 0 18px 0 0;
    border: solid 1px rgba(255, 255, 255, 0.2);
    box-shadow: none;
    color: #8f9bb3;

    &:hover {
      color: #222b45;
    }
  }
  .submit-modal-btn {
    width: 102px;
    height: 40px;
    margin: 0 0 0 18px;
    border-radius: 20px;
    background-color: #3266ff;

    &:hover {
      background-color: #fff;
      color: #3266ff;
    }
  }
  .ant-btn-primary[disabled] {
    color: rgba(0, 0, 0, 0.25);
    border-color: #d9d9d9;
    background-color: #f5f5f5;
  }
`;

const ModalContentContainer = styled.div`
  background-image: linear-gradient(159deg, #f3f6fa 19%, #e4e9f2 77%);
  width: 100%;
  height: 600px;

  display: flex;
  align-items: center;

  > div:nth-child(1) {
    box-shadow: 8px 0 14px 0 rgba(200, 138, 138, 0.06);
    background-color: rgba(255, 255, 255, 0.95);
    width: 280px;
    margin-left: 28px;
    height: 100%;
    overflow: hidden;
    display: flex;
    flex-direction: column;

    .select-month-input-wrap {
      width: 100%;
      display: flex;
      justify-content: space-between;
      align-items: center;
      text-align: center;
      padding: 12px 24px;
      > div:nth-child(1) {
        font-size: 15px;
        font-weight: 600;
        line-height: 1.6;
        color: #222b45;
        text-align: left;
      }
      > div:nth-child(2) {
        flex: 1;
        display: flex;
        justify-content: flex-end;
        align-items: center;
        cursor: pointer;
        > div {
          width: 87%;
        }
      }
    }

    .search-input-container {
      padding: 6px 24px;

      .search-input-wrap {
        border-radius: 8px;
        border: solid 1px #e4e9f2;
        background: #f7f9fc;
        height: 40px;

        input::-webkit-input-placeholder {
          font-size: 15px;
          line-height: 1.33;
          color: #8f9bb3;
        }
        * {
          background: #f7f9fc;
          font-size: 15px;
          line-height: 1.33;
          color: #222b45;
        }
        .ant-input-suffix > span {
          display: flex;

          &::before {
            border-left: 0;
          }
        }
      }
    }

    .render-modal-nhi-data-container {
      flex: 1;
      overflow: hidden;

      .nhi-data-header-wrap {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 6px 24px;

        > div:nth-child(1) {
          width: 43px;
          .dropdown-click-btn-wrap {
            display: flex;
            justify-content: space-between;
            align-items: center;
            cursor: pointer;

            > div:nth-child(1) {
              width: 20px;
              height: 20px;
              border-radius: 3px;
              background-color: #3266ff;
              text-align: center;
              color: #ffffff;
              position: relative;

              &::before {
                position: absolute;
                background: #fff;
                content: '';
                height: 3px;
                width: 12px;
                left: 50%;
                top: 50%;
              }
              ${props =>
                !props.isDisposalCheckBoxVisible &&
                `&::after {
                  position: absolute;
                  width: 100%;
                  background: #fff;
                  content: '';
                  height: 3px;
                  width: 12px;
                  left: 50%;
                  top: 50%;
                  transition: all 0.3s ease-in-out;
                }`}
              &::before {
                transform: translate(-50%, -50%);
              }
              ${props =>
                !props.isDisposalCheckBoxVisible &&
                `&::after{
                    transform: translate(-50%, -50%) rotate(90deg);
                  }`}
            }
            > div:nth-child(2) {
              flex: 1;
              text-align: center;
              display: flex;
              justify-content: center;
              align-items: center;
              transform: ${props => (props.isDisposalCheckBoxVisible ? 'rotate(180deg)' : 'rotate(0deg)')};
              transition: all 0.3s ease-out;
            }
          }
        }
        > div:nth-child(2) {
          text-align: right;
          flex: 1;
        }
      }

      .render-nhi-data-list-container {
        overflow-y: auto;
        max-height: calc(100% - 40px);
        max-width: 95%;
        margin: 0 auto;
        .lazy-load-wrap {
          padding-left: 24px;
        }
        .render-each-nhi-data-wrap {
          .nhi-data-list-date-header {
            height: 29px;
            background: #9ba1c3;
            font-size: 15px;
            font-weight: 600;
            line-height: 1.6;
            color: #fff;
            padding: 3px 0 2px 14px;
          }
          > div:nth-child(2) {
            padding: 6px 0;
            .render-each-nhi-wrap {
              display: flex;
              justify-content: space-between;
              align-items: flex-start;
              padding: 10px 0;
              position: relative;
              cursor: pointer;

              > div:nth-child(1) {
                width: 20px;
                height: 20px;
                text-align: center;
                position: relative;
                padding-left: 24px;
              }

              > div:nth-child(2) {
                margin-left: 5px;
                padding-left: 24px;
                flex: 1;

                > div:nth-child(1) {
                  font-size: 15px;
                  font-weight: 600;
                  line-height: 1.6;
                  color: #222b45;
                }

                > div:nth-child(2) {
                  font-size: 12px;
                  line-height: 1.33;
                  color: #8f9bb3;
                }
              }

              > div:nth-child(3) {
                font-size: 10px;
                font-weight: bold;
                line-height: 1.2;
                text-align: right;
                color: #8f9bb3;
                padding-right: 24px;
              }

              &::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 100%;
                height: 1px;
                background: #edf1f7;
              }
            }
          }
        }
      }
    }
  }
  > div:nth-child(2) {
    flex: 1;
    padding: 80px 56px;
    height: 100%;

    .render-nhi-one-detail-wrap {
      .nhi-one-label {
        font-size: 13px;
        font-weight: 600;
        line-height: 1.85;
        color: #8f9bb3;
      }
      .nhi-one-info-label {
        font-size: 26px;
        font-weight: bold;
        line-height: 1.23;
        color: #222b45;
      }
      .nhi-one-name {
        font-size: 32px;
        font-weight: 600;
        color: #4a90e2;
      }
      .nhi-one-info-wrap {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        margin-top: 24px;

        > div:not(:first-child) {
          margin-left: 48px;
        }
      }
      .nhi-one-table-wrap {
        margin-top: 24px;
      }
    }
    .nhi-one-loading {
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
`;
const MenuContainer = styled(Menu)`
  min-width: 136px;
  > li {
    padding: 12px 16px;
    font-size: 15px;
    line-height: 1.33;
    color: #222b45;
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

const ChartContainer = styled.div`
  background: #fff;
  width: 100%;
  min-height: 330px;
  margin-bottom: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.1);
  .chart-header-wrap {
    height: 56px;
    font-size: 18px;
    font-weight: bold;
    line-height: 1.33;
    color: #222b45;
    border-bottom: solid 1px #edf1f7;
    padding: 20px;
  }
  .chart-content-wrap.spin-wrap {
    min-height: 300px;
    align-items: center;
  }
  .chart-content-wrap {
    padding: 20px;
    display: flex;
    justify-content: center;
    align-items: flex-start;
    width: 100%;

    > div:nth-child(1) {
      .total-info-wrap {
        padding: 20px;
        > div:nth-child(1) {
          p:nth-child(1) {
            font-size: 36px;
            font-weight: bold;
            line-height: 1.33;
            color: #ffab00;
            margin: 0;
          }
        }
        > div:nth-child(2) {
          margin-top: 24px;
          display: flex;
          justify-content: space-between;
          align-items: center;

          > div > p:nth-child(1) {
            font-size: 22px;
            font-weight: bold;
            line-height: 1.45;
            color: #222b45;
            margin: 0;
          }
          > div:nth-child(2) {
            margin-left: 40px;
          }
        }

        .total-sub-text {
          font-size: 13px;
          font-weight: 600;
          line-height: 1.85;
          color: #8f9bb3;
        }
      }
    }
    > div:nth-child(2) {
      flex: 1;
      height: 100%;
      width: 80%;
      border-left: solid 1px #e5eced;
    }
  }
`;
const TooltipContainer = styled.div`
  border-radius: 16px;
  box-shadow: 0 5px 20px 0 rgba(0, 0, 0, 0.1);
  background: #ffffff;
  padding: 10px 20px 14px;
  > div {
    color: #222b45;
  }
  > div:nth-child(1) {
    font-size: 13px;
    font-weight: 600;
    line-height: 1.85;
  }
  > div:nth-child(2) {
    font-size: 12px;
    line-height: 1.33;
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
const nhiSalaryColumns = [
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
    render: totalDisposal => `共計 ${totalDisposal ? totalDisposal : 0} 單處置`,
  },
];
const nhiOneColumns = [
  {
    title: '健保代碼/中文',
    dataIndex: 'treatmentProcedureName',
    key: 'treatmentProcedureName',
    width: '60%',
  },
  {
    title: '乘數',
    dataIndex: 'multiplier',
    key: 'multiplier',
    width: '10%',
  },
  {
    title: '數量',
    dataIndex: 'quantity',
    key: 'quantity',
    width: '10%',
  },
  {
    title: '乘數',
    dataIndex: 'point',
    key: 'point',
    width: '10%',
  },
  {
    title: '合計',
    dataIndex: 'total',
    key: 'total',
    width: '10%',
  },
];

const expandedRowRender = expandSalary => {
  const expandedRowColumns = [
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
      columns={expandedRowColumns}
      showHeader={false}
      dataSource={expandSalary?.doctorOneSalary}
      pagination={false}
      tableLayout="fixed"
    />
  );
};
const nhiOneRender = ({ nhiOne }) => {
  const {
    patientName,
    patientNationalId,
    doctorName,
    examinationPoint,
    treatmentPoint,
    totalPoint,
    treatmentProcedureArr,
  } = nhiOne;
  return (
    <div className="render-nhi-one-detail-wrap">
      <div className="nhi-one-label">病患姓名</div>
      <div className="nhi-one-name">{patientName}</div>
      <div className="nhi-one-info-wrap">
        <div>
          <div className="nhi-one-label">身分證號</div>
          <div className="nhi-one-info-label">{patientNationalId}</div>
        </div>
        <div>
          <div className="nhi-one-label">主治醫師</div>
          <div className="nhi-one-info-label">{doctorName}</div>
        </div>
        <div>
          <div className="nhi-one-label">診察費</div>
          <div className="nhi-one-info-label">{examinationPoint}</div>
        </div>
        <div>
          <div className="nhi-one-label">診療費</div>
          <div className="nhi-one-info-label">{treatmentPoint}</div>
        </div>
        <div>
          <div className="nhi-one-label">合計點數</div>
          <div className="nhi-one-info-label">{totalPoint}</div>
        </div>
      </div>
      <div className="nhi-one-table-wrap">
        <Table
          dataSource={treatmentProcedureArr}
          columns={nhiOneColumns}
          scroll={{ y: 280 }}
          // loading={!nhiSalary}
          pagination={false}
          bordered={false}
          showHeader={true}
          tableLayout="fixed"
          rowKey={record => `nhi-one-${record._id}`}
        />
      </div>
    </div>
  );
};

const CustomCursor = ({ x, y, width, height, left, payload }) => {
  const windowInnerWidth = window.innerWidth;
  const reWidth = width > 18 ? 18 : width;
  let xPosition = x - left - 2 + reWidth / 2;
  if (1280 <= windowInnerWidth && windowInnerWidth < 1440) {
    xPosition += 6;
  } else if (1440 <= windowInnerWidth && windowInnerWidth < 1680) {
    xPosition += 7;
  } else if (1680 <= windowInnerWidth) {
    xPosition += 10;
  }
  if (windowInnerWidth > 1680) {
    xPosition = x - left - 3 + width / 2;
  }
  return (
    <Rectangle
      radius={[9.2, 9.2, 9.2, 9.2]}
      fill={payload[0]?.payload?.color}
      fillOpacity={0.2}
      x={xPosition}
      y={y}
      width={reWidth}
      height={height}
    />
  );
};
const CustomContent = props =>
  props?.payload[0]?.payload ? (
    <TooltipContainer>
      <div>
        {moment(props?.payload[0]?.payload?.disposalDate).year() - 1911}/
        {moment(props?.payload[0]?.payload?.disposalDate).format('MM/DD')}
      </div>
      <div>合計 {props?.payload[0]?.payload?.total || 0} 點</div>
    </TooltipContainer>
  ) : null;

function NhiIndexPage({
  nhiTableLoading,
  doctors,
  odIndexes,
  toothClean,
  initNhiSalary,
  getNhiSalary,
  nhiSalary,
  expandNhiSalary,
  totalPointLoading,
  totalPointByDisposalDate,
  validNhiYearMonths,
  getValidNhiByYearMonth,
  validNhiDataLoading,
  validNhiData,
  getNhiOneByDisposalId,
  nhiOneLoading,
  nhiOne,
}) {
  const dispatch = useDispatch();
  const [tabNumb, setTabNumb] = useState(1);
  const [startDate, setStartDate] = useState(moment().startOf('month'));
  const [endDate, setEndDate] = useState(moment());
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isDisposalCheckBoxVisible, setDisposalCheckBoxVisible] = useState(false);
  const [checkedModalData, updateCheckedModalData] = useState([]);
  const [nhiFirstId, updateNhiFirstId] = useState(Object.values(validNhiData)?.[0]?.[0]?.disposalId);
  const [currentNhiOne, updateCurrentNhiOne] = useState(null);
  const filterDoctors = doctors.filter(({ id }) => odIndexes.map(({ did }) => did).indexOf(id) !== -1);
  const getAllDisposalId = Object.entries(validNhiData)
    .map(([, list]) => list.map(({ disposalId }) => disposalId))
    .flat(Infinity);
  const getAllSerialNumber = Object.entries(validNhiData)
    .map(([, list]) => list.map(({ nhiExtendDisposal }) => nhiExtendDisposal?.serialNumber))
    .flat(Infinity)
    .filter(d => d !== '');

  useEffect(() => {
    dispatch(initNhiSalary(moment().startOf('month'), moment()));
  }, [dispatch, initNhiSalary]);
  useEffect(() => {
    if (Object.values(validNhiData)?.[0]) {
      updateNhiFirstId(Object.values(validNhiData)?.[0]?.[0]?.disposalId);
    }
  }, [validNhiData]);
  useEffect(() => {
    if (nhiFirstId) {
      dispatch(getNhiOneByDisposalId(nhiFirstId));
    }
  }, [dispatch, nhiFirstId, getNhiOneByDisposalId]);
  const disposalCheckBoxMenu = ({ getAllDisposalId, updateCheckedModalData, setDisposalCheckBoxVisible }) => (
    <MenuContainer>
      <Menu.Item
        onClick={() => {
          setDisposalCheckBoxVisible(!isDisposalCheckBoxVisible);
          setTimeout(() => {
            if (getAllDisposalId.length !== checkedModalData.length) {
              updateCheckedModalData(getAllDisposalId);
            } else {
              updateCheckedModalData([]);
            }
          }, 1000);
        }}
      >
        <div>全選</div>
      </Menu.Item>
      <Menu.Item
        onClick={() => {
          setDisposalCheckBoxVisible(!isDisposalCheckBoxVisible);
          setTimeout(() => {
            if (getAllSerialNumber.length !== checkedModalData.length) {
              updateCheckedModalData(getAllSerialNumber);
            } else {
              updateCheckedModalData([]);
            }
          }, 1000);
        }}
      >
        <div>專案流水號</div>
      </Menu.Item>
    </MenuContainer>
  );

  return (
    <div>
      <Helmet>
        <title>全民健保</title>
      </Helmet>
      <GlobalStyle />
      <ModalContainer
        centered={true}
        title="勾選計算項目"
        visible={isModalVisible}
        width={'100%'}
        closable={false}
        footer={[
          <Button className="cancel-modal-btn" key="back" onClick={() => setIsModalVisible(false)}>
            取消
          </Button>,
          <Button
            className="submit-modal-btn"
            key="submit"
            type="primary"
            disabled={checkedModalData.length === 0}
            onClick={() => {
              if (checkedModalData.length !== 0) {
                setIsModalVisible(false);
                dispatch(
                  getNhiSalary(
                    startDate,
                    endDate,
                    getAllDisposalId.filter(id => checkedModalData.indexOf(id) === -1),
                  ),
                );
              }
            }}
          >
            完成
          </Button>,
        ]}
      >
        <ModalContentContainer isDisposalCheckBoxVisible={isDisposalCheckBoxVisible}>
          <div>
            <div className="select-month-input-wrap">
              <div>選擇月份:</div>
              <div>
                <DatePicker
                  style={{ borderRadius: '8px', color: '#222b45' }}
                  onChange={(date, dateString) => {
                    updateCheckedModalData([]);
                    setStartDate(date);
                    setEndDate(moment(date).endOf('month'));
                    setTimeout(() => dispatch(getValidNhiByYearMonth(moment(dateString).format('YYYYMM'))), 700);
                  }}
                  picker="month"
                  value={moment(startDate)}
                  allowClear={false}
                  disabledDate={current =>
                    current &&
                    !current.isBetween(
                      moment(validNhiYearMonths[0]?.yearMonth).endOf('day'),
                      moment(validNhiYearMonths[validNhiYearMonths.length - 1]?.yearMonth).endOf('day'),
                      'day',
                      [],
                    )
                  }
                />
              </div>
            </div>

            <div className="search-input-container">
              <Search
                className="search-input-wrap"
                allowClear
                onSearch={() => {}}
                placeholder={'搜尋姓名,生日或流水號'}
              />
            </div>

            <div className="render-modal-nhi-data-container">
              <div className="nhi-data-header-wrap">
                <div>
                  <Dropdown
                    visible={isDisposalCheckBoxVisible}
                    overlay={() =>
                      disposalCheckBoxMenu({ getAllDisposalId, updateCheckedModalData, setDisposalCheckBoxVisible })
                    }
                    placement="bottomLeft"
                    trigger={'click'}
                  >
                    <div
                      className="dropdown-click-btn-wrap"
                      onClick={() => setDisposalCheckBoxVisible(!isDisposalCheckBoxVisible)}
                    >
                      <div />
                      <div>
                        <ArrowDown />
                      </div>
                    </div>
                  </Dropdown>
                </div>
                <div>共幾項</div>
              </div>
              <div className="render-nhi-data-list-container">
                {validNhiDataLoading ? (
                  <div className="lazy-load-wrap">Loading...</div>
                ) : validNhiData && Object.keys(validNhiData).length > 0 ? (
                  <Suspense fallback={<div className="lazy-load-wrap">Loading...</div>}>
                    <NhiDataListRender
                      validNhiData={validNhiData}
                      checkedModalData={checkedModalData}
                      updateCheckedModalData={updateCheckedModalData}
                      onNhiDataOneSelect={nhiFirstId => dispatch(getNhiOneByDisposalId(nhiFirstId))}
                      nhiOne={nhiOne}
                      currentNhiOne={currentNhiOne}
                      updateCurrentNhiOne={updateCurrentNhiOne}
                    />
                  </Suspense>
                ) : null}
              </div>
            </div>
          </div>

          <div>
            {validNhiDataLoading || nhiOneLoading ? (
              <Spin className="nhi-one-loading" />
            ) : nhiOne ? (
              nhiOneRender({ nhiOne })
            ) : null}
          </div>
        </ModalContentContainer>
      </ModalContainer>

      <TitleContainer>
        <div>健保點數與分析</div>
        <div>{`${startDate.year() - 1911}年${startDate.format('MM')}`}月</div>
        <div>
          <div>
            <img src={CloudDownload} alt="cloud download" />
            <span>匯出成 EXCEL</span>
          </div>
          <div onClick={() => setIsModalVisible(true)}>
            <img src={IconBarChart} alt="icon-bar-chart" />
            <span>選擇計算來源</span>
          </div>
        </div>
      </TitleContainer>
      <ChartContainer>
        <div className="chart-header-wrap">院所</div>
        {!totalPointLoading &&
        totalPointByDisposalDate &&
        totalPointByDisposalDate?.totalPointByDisposalDate?.length > 0 ? (
          <div className="chart-content-wrap">
            <div>
              <div className="total-info-wrap">
                <div>
                  <p>{totalPointByDisposalDate?.totalPoint}</p>
                  <p className="total-sub-text">合計</p>
                </div>
                <div>
                  <div>
                    <p>{totalPointByDisposalDate?.examinationTotalPoint}</p>
                    <p className="total-sub-text">診察</p>
                  </div>
                  <div>
                    <p>{totalPointByDisposalDate?.treatmentTotalPoint}</p>
                    <p className="total-sub-text">診療</p>
                  </div>
                </div>
              </div>
            </div>
            <div>
              {totalPointByDisposalDate?.totalPointByDisposalDate && (
                <ResponsiveContainer width="100%" height={300}>
                  <BarChart data={totalPointByDisposalDate?.totalPointByDisposalDate}>
                    <XAxis dataKey="name" padding={{ top: 10 }} axisLine={false} tickLine={false} />
                    <Bar dataKey="total" radius={[9.2, 9.2, 9.2, 9.2]} maxBarSize={18}>
                      {totalPointByDisposalDate?.totalPointByDisposalDate.map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={totalPointByDisposalDate?.colors[index]} />
                      ))}
                    </Bar>
                    <Tooltip
                      cursor={<CustomCursor />}
                      // content={obj => {
                      //   const style = {
                      //     background: 'rgba(255, 255, 255, 0.3)',
                      //   };
                      //   return <TooltipContainer style={style}>some</TooltipContainer>;
                      // }}
                      content={<CustomContent />}
                    />
                  </BarChart>
                </ResponsiveContainer>
              )}
            </div>
          </div>
        ) : (
          <Spin className="chart-content-wrap spin-wrap" />
        )}
      </ChartContainer>
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
                loading={nhiTableLoading}
                columns={nhiSalaryColumns}
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
                loading={nhiTableLoading}
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
                loading={nhiTableLoading}
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
          </TabsWrap>
        </TabsContainer>
      </div>
    </div>
  );
}

const mapStateToProps = ({ nhiIndexPageReducer, homePageReducer }) => ({
  nhiTableLoading: nhiIndexPageReducer.common.loading,
  odIndexes: nhiIndexPageReducer.nhiIndex.odIndexes,
  toothClean: nhiIndexPageReducer.nhiIndex.toothClean,
  doctors: homePageReducer.user.users,
  nhiSalary: convertUserToNhiSalary(nhiIndexPageReducer.nhiIndex.nhiSalary, homePageReducer.user.users),
  expandNhiSalary: toRefreshExpandSalary(nhiIndexPageReducer.nhiIndex.expandNhiSalary),
  totalPointLoading: nhiIndexPageReducer.nhiIndex.totalPointLoading,
  totalPointByDisposalDate: getCurrentMonthPoint(nhiIndexPageReducer.nhiIndex.totalPointByDisposalDate),
  validNhiYearMonths: nhiIndexPageReducer.nhiIndex.validNhiYearMonths,
  validNhiDataLoading: nhiIndexPageReducer.nhiIndex.validNhiDataLoading,
  validNhiData: toRefreshValidNhiData(nhiIndexPageReducer.nhiIndex.validNhiData, homePageReducer.user.users),
  nhiOneLoading: nhiIndexPageReducer.nhiIndex.nhiOneLoading,
  nhiOne: toRefreshNhiOne(
    nhiIndexPageReducer.nhiIndex.nhiOne,
    nhiIndexPageReducer.nhiIndex.validNhiData,
    homePageReducer.user.users,
  ),
});

// map to actions
const mapDispatchToProps = {
  initNhiSalary,
  getNhiSalary,
  getDoctorNhiSalary,
  getValidNhiByYearMonth,
  getNhiOneByDisposalId,
};

export default connect(mapStateToProps, mapDispatchToProps)(NhiIndexPage);
