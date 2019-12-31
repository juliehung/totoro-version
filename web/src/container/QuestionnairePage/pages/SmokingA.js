import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changeSmokingAmount } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

function SmokingA(props) {
  const onInputChange = e => {
    props.changeSmokingAmount(e.target.value);
  };

  const onPressEnter = () => {
    if (props.smokingAmount && props.smokingAmount.length !== 0) {
      props.gotoPage(18);
    }
  };

  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>一天大約幾支菸</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        type="number"
        onChange={onInputChange}
        onPressEnter={onPressEnter}
      />
      <ConfirmButton
        nextPage={() => {
          props.gotoPage(18);
        }}
        disabled={!props.smokingAmount || props.smokingAmount.length === 0}
      />
    </Container>
  );
}

const mapStateToProps = state => ({ smokingAmount: state.questionnairePageReducer.data.smokingAmount });

const mapDispatchToProps = { gotoPage, changeSmokingAmount };

export default connect(mapStateToProps, mapDispatchToProps)(SmokingA);
