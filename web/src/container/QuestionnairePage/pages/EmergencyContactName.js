import React from 'react';
import { connect } from 'react-redux';
import { nextPage, changeEmergencyName } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import { StyleRightCircleTwoTone } from './Address';

function EmergencyContactName(props) {
  const onInputChange = e => {
    props.changeEmergencyName(e.target.value);
  };

  const onPressEnter = () => {
    if (props.name && props.name.length !== 0) {
      props.nextPage();
    }
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>緊急聯絡人姓名</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        value={props.name}
        onChange={onInputChange}
        onPressEnter={onPressEnter}
      />
    </Container>
  );
}

const mapStateToProps = state => ({ name: state.questionnairePageReducer.data.patient.emergencyContact.name });

const mapDispatchToProps = { nextPage, changeEmergencyName };

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactName);
