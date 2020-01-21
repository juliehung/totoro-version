import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeDoDrug } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { Option, CheckedIcon } from './BloodType';
import { OptionContainer } from './Career';
import PageControllContainer from '../PageControllContainer';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

export const OptionsContainer = styled.div`
  color: #000;
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  margin: 25px 0;
`;

//#endregion

function DoDrugQ(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>服用藥物中</span>
      </div>
      <OptionsContainer>
        <OptionContainer
          key="A"
          selected={props.doDrug === 'A'}
          onClick={() => {
            props.preChangeDoDrug('A');
          }}
        >
          <Option selected={props.doDrug === 'A'}>A</Option>
          <span>有</span>
          <CheckedIcon type="check" selected={props.doDrug === 'A'} />
        </OptionContainer>
        <OptionContainer
          key="B"
          selected={props.doDrug === 'B'}
          onClick={() => {
            props.preChangeDoDrug('B');
          }}
        >
          <Option selected={props.doDrug === 'B'}>B</Option>
          <span>無</span>
          <CheckedIcon type="check" selected={props.doDrug === 'B'} />
        </OptionContainer>
      </OptionsContainer>
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ doDrug: state.questionnairePageReducer.data.patient.doDrug });

const mapDispatchToProps = { preChangeDoDrug };

export default connect(mapStateToProps, mapDispatchToProps)(DoDrugQ);
