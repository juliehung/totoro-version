import React from 'react';
import { connect } from 'react-redux';
import { gotoPage, changeSmokingAmount } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';
import { StyleRightCircleTwoTone } from './Address';

export const checkSmokingAmountValidation = smokingAmount => !smokingAmount || smokingAmount < 0;

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
        <StyleRightCircleTwoTone />
        <span>一天大約幾支菸</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        type="number"
        onChange={onInputChange}
        onPressEnter={onPressEnter}
        value={props.smokingAmount}
      />
      <ConfirmButton
        nextPage={() => {
          props.gotoPage(18);
        }}
        disabled={checkSmokingAmountValidation(props.smokingAmount)}
      />
      <PageControllContainer
        pre={() => {
          props.gotoPage(16);
        }}
        next={() => {
          props.gotoPage(16);
        }}
      />
    </Container>
  );
}

const mapStateToProps = state => ({ smokingAmount: state.questionnairePageReducer.data.patient.smokingAmount });

const mapDispatchToProps = { gotoPage, changeSmokingAmount };

export default connect(mapStateToProps, mapDispatchToProps)(SmokingA);
