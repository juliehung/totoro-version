import React, { useState } from 'react';
import { Table } from 'antd';
import { connect } from 'react-redux';
import { getOdIndexes, getOdIndexesFail, getOdIndexesSuccess } from './actions';
import moment from 'moment';
import styled from 'styled-components';

const MainFrameStyle = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
`;
const BodyFrameStyle = styled.div`
  width: 1024px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const InputZoneStyle = styled.div`
  margin: 8px 0 0 0;
`;
const DisplayZoneStyle = styled.div`
  margin: 8px 0 0 0;
`;

let tableIndex = 0;
const renderFloat2Position = f => (typeof f === 'number' ? (Math.round(f * 100) / 100).toFixed(2) : '0');
const odColumns = doctors => [
  {
    title: '醫生',
    dataIndex: 'did',
    key: tableIndex++,
    render: did => doctors.find(d => d.id === did)?.firstName ?? did,
  },
  {
    title: '顆數',
    dataIndex: 'totalTooth',
    key: tableIndex++,
  },
  {
    title: '人數',
    dataIndex: 'distinctTotalPat',
    key: tableIndex++,
  },
  {
    title: '總人數',
    dataIndex: 'totalPat',
    key: tableIndex++,
  },
  {
    title: '面數',
    dataIndex: 'totalSurface',
    key: tableIndex++,
  },
  {
    title: '平均一顆面數',
    dataIndex: 'toothPeopleRate',
    key: tableIndex++,
    render: renderFloat2Position,
  },
  {
    title: '補牙患者平均填補總面數',
    dataIndex: 'surfacePeopleRate',
    key: tableIndex++,
    render: renderFloat2Position,
  },
  {
    title: '平均每人填補顆數',
    dataIndex: 'toothPeopleRate',
    key: tableIndex++,
    render: renderFloat2Position,
  },
];

function NhiIndexPage({ odIndexes, doctors, getOdIndexes }) {
  const [begin, setBegin] = useState(moment().startOf('month').format('YYYY-MM-DD'));
  const [end, setEnd] = useState(moment().format('YYYY-MM-DD'));

  return (
    <MainFrameStyle>
      <BodyFrameStyle>
        <InputZoneStyle>
          <span>起始日: </span>
          <input type="date" value={begin} onChange={e => setBegin(e.target.value)} />
        </InputZoneStyle>
        <InputZoneStyle>
          <span>結束日: </span>
          <input type="date" value={end} onChange={e => setEnd(e.target.value)} />
        </InputZoneStyle>
        <InputZoneStyle>
          <input type="button" value="計算" onClick={() => getOdIndexes(begin, end)} />
        </InputZoneStyle>
        <DisplayZoneStyle>
          <Table columns={odColumns(doctors)} dataSource={odIndexes} pagination={false} bordered showHeader={true} />
        </DisplayZoneStyle>
      </BodyFrameStyle>
    </MainFrameStyle>
  );
}

const mapStateToProps = ({ nhiIndexPageReducer, homePageReducer }) => ({
  odIndexes: nhiIndexPageReducer.nhiIndex.odIndexes,
  doctors: homePageReducer.user.users,
});

// map to actions
const mapDispatchToProps = { getOdIndexes, getOdIndexesSuccess, getOdIndexesFail };

export default connect(mapStateToProps, mapDispatchToProps)(NhiIndexPage);
