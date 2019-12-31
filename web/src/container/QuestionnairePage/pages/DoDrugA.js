import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, prevPage } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { TransparentInput } from './Name';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

function DoDrugA() {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>正在服用的藥物名稱</span>
      </div>
      <TransparentInput size="large" placeholder="請在此鍵入答案" />
    </Container>
  );
}

const mapStateToProps = state => ({ page: state.questionnairePageReducer.flow.page });

const mapDispatchToProps = { nextPage, prevPage };

export default connect(mapStateToProps, mapDispatchToProps)(DoDrugA);
