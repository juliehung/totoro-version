import React from 'react';
import { connect } from 'react-redux';
import { nextPage, changeEmergencyPhone } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';
import { StyleRightCircleTwoTone } from './Address';

function EmergencyContactPhone(props) {
  const onInputChange = e => {
    props.changeEmergencyPhone(e.target.value);
  };

  const onPressEnter = () => {
    if (props.phone && props.phone.length !== 0) {
      props.nextPage();
    }
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>緊急聯絡人電話</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        value={props.phone}
        onChange={onInputChange}
        onPressEnter={onPressEnter}
      />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.phone || props.phone.length === 0} />
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ phone: state.questionnairePageReducer.data.patient.emergencyContact.phone });

const mapDispatchToProps = { nextPage, changeEmergencyPhone };

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactPhone);
