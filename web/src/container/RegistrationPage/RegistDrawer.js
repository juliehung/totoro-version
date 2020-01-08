import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import { Button, Table, Drawer } from 'antd';
import ManPng from '../../static/images/man.png';
import styled from 'styled-components';
import moment from 'moment';
import { changeDrawerVisible } from './actions';

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
      font-family: Muli-Bold;
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
    dataIndex: 'creatDate',
    key: 'creatDate',
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
  const onClose = () => {
    props.changeDrawerVisible(false);
  };

  return (
    <Drawer placement="right" closable={true} width="500px" onClose={onClose} visible={props.drawerVisible}>
      <DrawerContainer>
        <PatientContainer>
          <img src={ManPng} alt={'avatar'}></img>
          <div>
            <span>
              MRN. <span>{props.patient.medicalId}</span>
            </span>
            <span>{props.patient.name}</span>
          </div>
        </PatientContainer>
        <Table columns={columns} dataSource={props.docs} pagination={false} />
        <Link to={`/q/${props.patient.id}`}>
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

const mapDispatchToProps = { changeDrawerVisible };

export default connect(mapStateToProps, mapDispatchToProps)(RegistDrawer);
