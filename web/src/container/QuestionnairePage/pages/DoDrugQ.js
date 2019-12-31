import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeDoDrug } from '../actions';
import { Icon } from 'antd';

import { Container } from './Name';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

const OptionContainer = styled.div`
  display: flex;
  margin: 30px 0;
  flex-wrap: wrap;
`;

const Option = styled.div`
  box-sizing: border-box;
  width: 20%;
  margin: 10px 2.5%;
  font-size: 15px;
  color: #000;
  display: flex;
  border: ${props => (props.selected ? '2px solid #1890ff' : '2px solid #fff')};
  transition: border 300ms ease-in;
  cursor: pointer;
  & > span {
    margin-left: 10px;
  }
`;

const Alphabet = styled.div`
  width: 20px;
  height: 20px;
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
`;

//#endregion

function DoDrugQ(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>服用藥物中</span>
      </div>
      <OptionContainer>
        <Option
          key="A"
          selected={props.doDrug === 'A'}
          onClick={() => {
            props.preChangeDoDrug('A');
          }}
        >
          <Alphabet>A</Alphabet>
          <span>有</span>
        </Option>
        <Option
          key="B"
          selected={props.doDrug === 'B'}
          onClick={() => {
            props.preChangeDoDrug('B');
          }}
        >
          <Alphabet>B</Alphabet>
          <span>無</span>
        </Option>
      </OptionContainer>
    </Container>
  );
}

const mapStateToProps = state => ({ doDrug: state.questionnairePageReducer.data.doDrug });

const mapDispatchToProps = { preChangeDoDrug };

export default connect(mapStateToProps, mapDispatchToProps)(DoDrugQ);
