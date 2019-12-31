import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeName } from '../actions';
import { Icon, Input } from 'antd';
import { withRouter } from 'react-router-dom';
import ConfirmButton from './ConfirmButton';

//#region

export const Container = styled.div`
  font-size: 20px;
  color: #1890ff;
`;

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

export const TransparentInput = styled(Input)`
  margin: 20px 0 !important;
  border: none !important;
  border-color: transparent !important;
  background: transparent !important;
  &:focus {
    box-shadow: none !important;
  }
`;
//#endregion

function Name(props) {
  const onInputChange = e => {
    props.changeName(e.target.value);
  };

  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>姓名*</span>
      </div>
      <TransparentInput size="large" placeholder="請在此鍵入答案" onChange={onInputChange} value={props.name} />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.name || props.name.length === 0} />
    </Container>
  );
}

const mapStateToProps = state => ({ name: state.questionnairePageReducer.data.name });

const mapDispatchToProps = { nextPage, changeName };

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Name));
