import React, { useEffect, useState, Suspense } from 'react';
import moment from 'moment';
import { DatePicker, Table, Tabs, Menu, Dropdown, Button, Spin } from 'antd';
import { connect, useDispatch } from 'react-redux';
import { BarChart, Bar, XAxis, Rectangle, Tooltip, Cell, ResponsiveContainer } from 'recharts';
import { Helmet } from 'react-helmet-async';
import {
  initNhiSalary,
  getNhiSalary,
  getDoctorNhiSalary,
  getValidNhiByYearMonth,
  getNhiOneByDisposalId,
} from './actions';
import NhiSalarySearchInput from './nhiSalarySearchInput';
import {
  GlobalStyle,
  ModalContainer,
  ModalContentContainer,
  MenuContainer,
  TitleContainer,
  ChartContainer,
  TooltipContainer,
  TabsContainer,
  TabsWrap,
  TableContainer,
  TabBarExtraContentContainer,
  ExpandTableContainer,
} from './styles';
import { getBaseUrl } from '../../utils/getBaseUrl';
import convertUserToNhiSalary from './utils/convertUserToNhiSalary';
import toRefreshExpandSalary from './utils/toRefreshExpandSalary';
import getCurrentMonthPoint from './utils/getCurrentMonthPoint';
import toRefreshValidNhiData from './utils/toRefreshValidNhiData';
import toRefreshNhiOne from './utils/toRefreshNhiOne';
import IconBarChart from '../../images/icon-bar-chart.svg';
import { ReactComponent as ArrowDown } from '../../images/1-2-icon-his-icons-arrow-down-fill.svg';

const NhiDataListRender = React.lazy(() => import('./nhiDataList'));

const { TabPane } = Tabs;

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
const endoColumns = (doctors, filterDoctors) => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: 'did',
    filters: filterDoctors.map(d => ({ value: d?.id, text: d?.firstName })),
    onFilter: (value, record) => value === record.did,
    render: did => doctors.find(d => d.id === did)?.firstName ?? did,
  },
  { title: '根管未完成率', dataIndex: 'uncompletedRate', key: 'uncompletedRate', render: t => `${t * 100}%` },
];
const nhiSalaryColumns = [
  {
    title: '排序',
    dataIndex: 'doctorId',
    key: 'doctorId',
    fixed: 'left',
    sorter: (a, b) => a.doctorId - b.doctorId,
  },
  {
    title: '醫師',
    dataIndex: 'doctorName',
    key: 'doctorName',
    fixed: 'left',
    sorter: (a, b) => a.doctorId - b.doctorId,
  },
  {
    title: '合計',
    dataIndex: 'total',
    key: 'total',
    sorter: (a, b) => a.total - b.total,
  },
  {
    title: '感控診察',
    dataIndex: 'infectionExaminationPoint',
    key: 'infectionExaminationPoint',
    sorter: (a, b) => a.infectionExaminationPoint - b.infectionExaminationPoint,
  },
  {
    title: '未感控診察',
    dataIndex: 'regularExaminationPoint',
    key: 'regularExaminationPoint',
    sorter: (a, b) => a.regularExaminationPoint - b.regularExaminationPoint,
  },
  {
    title: '診療費',
    dataIndex: 'treatmentPoint',
    key: 'treatmentPoint',
    sorter: (a, b) => a.treatmentPoint - b.treatmentPoint,
  },
  {
    title: '部分負擔',
    dataIndex: 'copayment',
    key: 'copayment',
    sorter: (a, b) => a.copayment - b.copayment,
  },
  {
    title: '牙周專科',
    dataIndex: 'perioPoint',
    key: 'perioPoint',
    sorter: (a, b) => a.perioPoint - b.perioPoint,
  },
  {
    title: '兒童專科',
    dataIndex: 'pedoPoint',
    key: 'pedoPoint',
    sorter: (a, b) => a.pedoPoint - b.pedoPoint,
  },
  {
    title: '根管專科',
    dataIndex: 'endoPoint',
    key: 'endoPoint',
    sorter: (a, b) => a.endoPoint - b.endoPoint,
  },
  {
    title: '',
    dataIndex: 'totalDisposal',
    key: 'totalDisposal',
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
      fixed: 'left',
      sorter: (a, b) => a.doctorId - b.doctorId,
    },
    {
      title: '醫師',
      dataIndex: 'doctorName',
      key: 'doctorName',
      fixed: 'left',
      sorter: (a, b) => a.doctorId - b.doctorId,
    },
    {
      title: '合計',
      dataIndex: 'total',
      key: 'total',
      sorter: (a, b) => a.total - b.total,
    },
    {
      title: '感控診察',
      dataIndex: 'infectionExaminationPoint',
      key: 'infectionExaminationPoint',
      sorter: (a, b) => a.infectionExaminationPoint - b.infectionExaminationPoint,
    },
    {
      title: '未感控診察',
      dataIndex: 'regularExaminationPoint',
      key: 'regularExaminationPoint',
      sorter: (a, b) => a.regularExaminationPoint - b.regularExaminationPoint,
    },
    {
      title: '診療費',
      dataIndex: 'treatmentPoint',
      key: 'treatmentPoint',
      sorter: (a, b) => a.treatmentPoint - b.treatmentPoint,
    },
    {
      title: '部分負擔',
      dataIndex: 'copayment',
      key: 'copayment',
      sorter: (a, b) => a.copayment - b.copayment,
    },
    {
      title: '牙周專科',
      dataIndex: 'perioPoint',
      key: 'perioPoint',
      sorter: (a, b) => a.perioPoint - b.perioPoint,
    },
    {
      title: '兒童專科',
      dataIndex: 'pedoPoint',
      key: 'pedoPoint',
      sorter: (a, b) => a.pedoPoint - b.pedoPoint,
    },
    {
      title: '根管專科',
      dataIndex: 'endoPoint',
      key: 'endoPoint',
      sorter: (a, b) => a.endoPoint - b.endoPoint,
    },
    {
      title: '',
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
  endoIndexes,
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
  const [currentValidNhiData, filterValidNhiData] = useState(validNhiData);
  const [searchValue, onChangeValue] = useState('');
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
  useEffect(() => {
    if (searchValue.length === 0) {
      filterValidNhiData(validNhiData);
    }
  }, [validNhiData, searchValue]);

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
                setTimeout(() => {
                  dispatch(
                    getNhiSalary(
                      startDate,
                      endDate,
                      getAllDisposalId.filter(id => checkedModalData.indexOf(id) === -1),
                    ),
                  );
                }, 500);
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
                    onChangeValue('');
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
              <NhiSalarySearchInput
                validNhiData={validNhiData}
                filterValidNhiData={filterValidNhiData}
                className="search-input-wrap"
                onChangeValue={onChangeValue}
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
                <div>已選 {checkedModalData.length} 項</div>
              </div>
              <div className="render-nhi-data-list-container">
                {validNhiDataLoading ? (
                  <div className="lazy-load-wrap">Loading...</div>
                ) : currentValidNhiData && Object.keys(currentValidNhiData).length > 0 ? (
                  <Suspense fallback={<div className="lazy-load-wrap">Loading...</div>}>
                    <NhiDataListRender
                      validNhiData={currentValidNhiData}
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
          <div />
          {/*<img src={CloudDownload} alt="cloud download" />*/}
          {/*<span>匯出成 EXCEL</span>*/}
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
                    <Tooltip cursor={<CustomCursor />} content={<CustomContent />} />
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
            <TabPane tab="根管未完成率指標" key="4">
              <TableContainer
                loading={nhiTableLoading}
                scroll={{ y: 280 }}
                columns={endoColumns(doctors, filterDoctors)}
                dataSource={endoIndexes}
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
  endoIndexes: nhiIndexPageReducer.nhiIndex.endoIndexes,
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
