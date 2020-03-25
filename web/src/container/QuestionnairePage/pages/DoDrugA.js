import React from 'react';
import { connect } from 'react-redux';
import { gotoPage, changeDrug } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';
import { StyleRightCircleTwoTone } from './Address';

function DoDrugA(props) {
  const onInputChange = e => {
    props.changeDrug(e.target.value);
  };

  const onPressEnter = () => {
    if (props.drug && props.drug.length !== 0) {
      props.gotoPage(17);
    }
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>正在服用的藥物名稱</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={props.drug}
        onPressEnter={onPressEnter}
      />
      <ConfirmButton
        nextPage={() => {
          props.gotoPage(17);
        }}
        disabled={!props.drug || props.drug.length === 0}
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

const mapStateToProps = state => ({ drug: state.questionnairePageReducer.data.patient.drug });

const mapDispatchToProps = { gotoPage, changeDrug };

export default connect(mapStateToProps, mapDispatchToProps)(DoDrugA);
