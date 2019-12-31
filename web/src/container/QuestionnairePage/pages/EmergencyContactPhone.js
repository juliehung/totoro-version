import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeEmergencyPhone } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

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
        <StyleIcon type="right-circle" theme="twoTone" />
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
    </Container>
  );
}

const mapStateToProps = state => ({ phone: state.questionnairePageReducer.data.emergencyContact.phone });

const mapDispatchToProps = { nextPage, changeEmergencyPhone };

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactPhone);
