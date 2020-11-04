import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Button } from 'antd';
import checkCircleFill from './../../images/Check-Circle-Fill.svg';
import { SearchOutlined } from '@ant-design/icons';
import { changeDrawerVisible } from './actions';

//#region
const Container = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

  & > div {
    display: flex;
    flex-direction: column;
    align-items: center;
    & > :first-child {
      margin-bottom: 30px;
    }
    & > :nth-child(2) {
      margin-bottom: 35px;
      display: flex;
      flex-direction: column;
      & > :first-child {
        font-size: 26px;
        font-weight: 600;
        color: #222b45;
      }
      & > :nth-child(2) {
        font-size: 15px;
        color: #8f9bb3;
      }
    }
  }
`;
//#endregion

function PatientNotFound(props) {
  const { changeDrawerVisible } = props;
  return (
    <Container>
      <div>
        <img src={checkCircleFill} alt="search" />
        <div>
          <span>找不到這個病患資訊頁</span>
          <span>請確認病患 ID 是否正確 或重新搜尋病患</span>
        </div>
        <Button
          shape="round"
          icon={<SearchOutlined />}
          style={{ backgroundColor: '#e4e9f2', width: '117px' }}
          onClick={() => {
            changeDrawerVisible(true);
          }}
        >
          搜尋病患
        </Button>
      </div>
    </Container>
  );
}

const mapStateToProps = () => ({});

const mapDispatchToProps = { changeDrawerVisible };

export default connect(mapStateToProps, mapDispatchToProps)(PatientNotFound);
