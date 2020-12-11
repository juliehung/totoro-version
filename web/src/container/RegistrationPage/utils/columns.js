import React from 'react';
import moment from 'moment';
import styled from 'styled-components';
import { XRAY_VENDORS } from '../../AppointmentPage/constant';
import VisionImg from '../../../component/VisionImg';
import VixWinImg from '../../../component/VixWinImg';

const XrayContainer = styled.div`
  display: flex;
  & > div {
    cursor: pointer;
    margin-right: 10px;
    width: 33px;
    height: 33px;
    border-radius: 50%;
    background: #eee;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: box-shadow 200ms ease-in-out;
    &:hover {
      background-color: rgba(50, 102, 255, 0.2);
      box-shadow: 0 2px 13px 0 rgba(50, 102, 255, 0.3), 0 1px 3px 0 rgba(0, 0, 0, 0.13);
    }
    & > img {
      pointer-events: none;
    }
  }
`;

export const columns = xRayVendors => [
  {
    title: '序位',
    dataIndex: 'rowIndex',
    key: 'rowIndex',
    width: 40,
    fixed: 'left',
  },
  { title: '姓名', dataIndex: 'name', key: 'name', width: 100, fixed: 'left' },
  {
    title: '年齡',
    dataIndex: 'age',
    key: 'age',
    sorter: (a, b) => a.age.replace('Y', '') - b.age.replace('Y', ''),
    width: 50,
  },
  {
    title: '掛號時間',
    dataIndex: 'arrivalTime',
    key: 'arrivalTime',
    defaultSortOrder: 'ascend',
    sorter: (a, b) => moment(a.arrivalTime).unix() - moment(b.arrivalTime).unix(),
    width: 70,
    render: date => moment(date).format('HH:mm'),
  },
  {
    title: '預約時間',
    dataIndex: 'expectedArrivalTime',
    key: 'expectedArrivalTime',
    sorter: (a, b) => moment(a.expectedArrivalTime).unix() - moment(b.expectedArrivalTime).unix(),
    width: 70,
    render: date => moment(date).format('HH:mm'),
  },
  {
    title: '掛號類別',
    dataIndex: 'type',
    key: 'type',
    filters: [
      { text: '健保', value: '健' },
      { text: '自費', value: '自' },
    ],
    filterMultiple: false,
    onFilter: (value, record) => record?.type.indexOf(value) !== -1,
    width: 60,
  },
  { title: '醫師', dataIndex: 'doctor', key: 'doctor', width: 70 },
  { title: '治療事項', dataIndex: 'subject', key: 'subject', ellipsis: true, width: 200 },
  {
    title: 'X光片',
    dataIndex: 'xray',
    key: 'xray',
    width: 100,
    render: () => {
      return (
        <XrayContainer>
          {xRayVendors?.[XRAY_VENDORS.vision] === 'true' && (
            <div className={`xray XRAYVENDORS_${XRAY_VENDORS.vision}`}>
              <VisionImg width="23" />
            </div>
          )}
          {xRayVendors?.[XRAY_VENDORS.vixwin] === 'true' && (
            <div className={`xray XRAYVENDORS_${XRAY_VENDORS.vixwin}`}>
              <VixWinImg width="23" />
            </div>
          )}
        </XrayContainer>
      );
    },
  },
];
