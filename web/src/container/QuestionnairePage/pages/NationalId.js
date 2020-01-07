import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeNationalId } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

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
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>身分證字號*</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        onPressEnter={onPressEnter}
        value={props.nationalId}
      />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.nationalId || props.nationalId.length === 0} />
    </Container>
  );
}

const mapStateToProps = state => ({ nationalId: state.questionnairePageReducer.data.patient.nationalId });

const mapDispatchToProps = { nextPage, changeNationalId };

export default connect(mapStateToProps, mapDispatchToProps)(NationalId);
