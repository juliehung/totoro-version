import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeIntroducer } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

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
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>介紹人</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={props.introducer}
        onPressEnter={onPressEnter}
      />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.introducer || props.introducer.length === 0} />
    </Container>
  );
}

const mapStateToProps = state => ({ introducer: state.questionnairePageReducer.data.patient.introducer });

const mapDispatchToProps = { nextPage, changeIntroducer };

export default connect(mapStateToProps, mapDispatchToProps)(Introducer);
