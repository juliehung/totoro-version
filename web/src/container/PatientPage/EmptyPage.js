import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import emptyIcon from '../../images/empty-icon.svg';
import { Button } from 'antd';
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

    & > * {
      margin-bottom: 20px;
    }
  }
`;
//#endregion

function PatientDetail(props) {
  const { changeDrawerVisible } = props;

  return (
    <Container>
      <div>
        <img src={emptyIcon} alt={'empty'} />
        <Button
          shape="round"
          icon={<SearchOutlined />}
          style={{ backgroundColor: '#e4e9f2' }}
          onClick={() => {
            changeDrawerVisible(true);
          }}
        >
          搜尋病患
        </Button>
      </div>
      <br />
    </Container>
  );
}

const mapStateToProps = () => ({});

const mapDispatchToProps = { changeDrawerVisible };

export default connect(mapStateToProps, mapDispatchToProps)(PatientDetail);
