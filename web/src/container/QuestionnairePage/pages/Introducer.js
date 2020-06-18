import React from 'react';
import { connect } from 'react-redux';
import { nextPage, changeIntroducer } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import { StyleRightCircleTwoTone } from './Address';

function Introducer(props) {
  const onInputChange = e => {
    props.changeIntroducer(e.target.value);
  };

  const onPressEnter = () => {
    if (props.introducer && props.introducer.length !== 0) {
      props.nextPage();
    }
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>介紹人</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={props.introducer}
        onPressEnter={onPressEnter}
      />
    </Container>
  );
}

const mapStateToProps = state => ({ introducer: state.questionnairePageReducer.data.patient.introducer });

const mapDispatchToProps = { nextPage, changeIntroducer };

export default connect(mapStateToProps, mapDispatchToProps)(Introducer);
