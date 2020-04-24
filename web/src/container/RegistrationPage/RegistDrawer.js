import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Button, Table, Drawer } from 'antd';
import ManPng from '../../static/images/man.png';
import WomanPng from '../../static/images/woman.png';
import DefaultPng from '../../static/images/default.png';
import styled from 'styled-components';
import moment from 'moment';
import { changeDrawerVisible, getDoc } from './actions';

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

const StyledButton = styled(Button)`
  margin: 20px auto;
`;

//#endregion

const columns = [
  {
    title: '產生日期',
    dataIndex: 'createDate',
    key: 'createDate',
    render: date => moment(date).format('YYYY-MM-DD HH:mm'),
  },
  {
    title: '操作',
    dataIndex: 'id',
    key: 'id',
    render: id => <Link to={`/q/history/${id}`}>檢視</Link>,
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
            <span>{patient.name}</span>
          </div>
        </PatientContainer>
        <Table columns={columns} dataSource={props.docs} pagination={false} />
        <Link to={`/q/${patient.id}`}>
          <StyledButton type="primary">新增病歷首頁</StyledButton>
        </Link>
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
