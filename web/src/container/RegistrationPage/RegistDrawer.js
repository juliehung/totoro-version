import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Button, Table, Drawer } from 'antd';
import ManPng from '../../static/images/man.png';
import WomanPng from '../../static/images/woman.png';
import DefaultPng from '../../static/images/default.png';
import styled from 'styled-components';
import moment from 'moment';
import { changeDrawerVisible, getDoc } from './actions';
import { getBaseUrl } from '../../utils/getBaseUrl';
import personFill from '../../images/person-fill.svg';
import { parsePatientNameWithVipMark } from '../../utils/patientHelper';

//#region
const DrawerContainer = styled.div`
  margin: 20px 0px;
`;

const PatientContainer = styled.div`
  display: flex;
  align-items: center;
  & > img {
    margin-right: 20px;
    width: 48px;
    height: 48px;
  }
  & > div {
    display: flex;
    flex-direction: column;
    & > span:first-child {
      color: rgba(0, 0, 0, 0.5);
      font-size: 16px;
      font-weight: italic;
    }
    & > span:not(:first-child) {
      color: rgb(0, 0, 0);
      font-size: 24px;
      font-weight: bold;
    }
  }
`;

const PatientPageButtonContainer = styled.div`
  margin: 25px 0 20px;
  padding-bottom: 25px;
  border-bottom: 1px solid #edf1f7;
`;

const Title = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  font-size: 15px;
  font-weight: 600;
  color: #8f9bb3;
`;
//#endregion

const columns = changeDrawerVisible => [
  {
    title: '產生日期',
    dataIndex: 'createDate',
    key: 'createDate',
    render: date => {
      const momentDate = moment(date);
      const year = momentDate.year() - 1911;
      return `${year}-${momentDate.format('MM-DD HH:mm')}`;
    },
  },
  {
    title: '操作',
    dataIndex: 'id',
    key: 'id',
    render: id => (
      <a
        href={`${getBaseUrl()}#/q/history/${id}`}
        target="_blank"
        rel="noopener noreferrer"
        onClick={() => {
          changeDrawerVisible(false);
        }}
      >
        檢視
      </a>
    ),
  },
];

function RegistDrawer(props) {
  const { changeDrawerVisible, getDoc, drawerVisible, patient } = props;

  useEffect(() => {
    if (drawerVisible) {
      getDoc(patient.id);
    }
  }, [getDoc, drawerVisible, patient]);

  const onClose = () => {
    changeDrawerVisible(false);
  };

  const avatar = patient.gender === 'MALE' ? ManPng : patient.gender === 'FEMALE' ? WomanPng : DefaultPng;

  return (
    <Drawer placement="right" closable={true} width="375px" onClose={onClose} visible={drawerVisible}>
      <DrawerContainer>
        <PatientContainer>
          <img src={avatar} alt={'avatar'} />
          <div>
            <span>
              MRN. <span>{patient.medicalId}</span>
            </span>
            <span>{parsePatientNameWithVipMark(patient?.patientVipPatient, patient.name)}</span>
          </div>
        </PatientContainer>
        <PatientPageButtonContainer>
          <a href={`${getBaseUrl()}#/patient/${patient.id}`} target="_blank" rel="noopener noreferrer">
            <Button shape="round" type="primary">
              <div style={{ display: 'flex' }}>
                <img src={personFill} alt="icon" />
                <span>查看病患資訊</span>
              </div>
            </Button>
          </a>
        </PatientPageButtonContainer>
        <Title>
          <span>病歷表首頁</span>
          <a
            href={`${getBaseUrl()}#/q/${patient.id}`}
            target="_blank"
            rel="noopener noreferrer"
            onClick={() => {
              changeDrawerVisible(false);
            }}
          >
            <Button shape="round">新增填寫</Button>
          </a>
        </Title>
        <Table columns={columns(changeDrawerVisible)} dataSource={props.docs} pagination={false} />
      </DrawerContainer>
    </Drawer>
  );
}

const mapStateToProps = ({ registrationPageReducer }) => ({
  drawerVisible: registrationPageReducer.drawer.visible,
  patient: registrationPageReducer.drawer.patient,
  docs: registrationPageReducer.drawer.docs,
});

const mapDispatchToProps = { changeDrawerVisible, getDoc };

export default connect(mapStateToProps, mapDispatchToProps)(RegistDrawer);
