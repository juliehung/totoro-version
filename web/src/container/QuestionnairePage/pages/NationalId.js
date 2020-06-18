import React from 'react';
import { connect } from 'react-redux';
import { nextPage, changeNationalId } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import { StyleRightCircleTwoTone } from './Address';

function NationalId(props) {
  const onInputChange = e => {
    props.changeNationalId(e.target.value);
  };

  const onPressEnter = () => {
    if (props.nationalId && props.nationalId.length !== 0) {
      props.nextPage();
    }
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>身分證字號</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        onPressEnter={onPressEnter}
        value={props.nationalId}
      />
    </Container>
  );
}

const mapStateToProps = state => ({ nationalId: state.questionnairePageReducer.data.patient.nationalId });

const mapDispatchToProps = { nextPage, changeNationalId };

export default connect(mapStateToProps, mapDispatchToProps)(NationalId);
