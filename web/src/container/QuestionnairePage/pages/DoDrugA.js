import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { gotoPage, changeDrug } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

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
        <StyleIcon type="right-circle" theme="twoTone" />
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
