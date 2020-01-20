import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangePregnant } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { Option, CheckedIcon } from './BloodType';
import { OptionContainer } from './Career';
import PageControllContainer from '../PageControllContainer';
import { OptionsContainer } from './DoDrugQ';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

function PregnantQ(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>懷孕</span>
      </div>
      <OptionsContainer>
        <OptionContainer
          key="A"
          selected={props.pregnant === 'A'}
          onClick={() => {
            props.preChangePregnant('A');
          }}
        >
          <Option selected={props.pregnant === 'A'}>A</Option>
          <span>有</span>
          <CheckedIcon type="check" selected={props.pregnant === 'A'} />
        </OptionContainer>
        <OptionContainer
          key="B"
          selected={props.pregnant === 'B'}
          onClick={() => {
            props.preChangePregnant('B');
          }}
        >
          <Option selected={props.pregnant === 'B'}>B</Option>
          <span>無</span>
          <CheckedIcon type="check" selected={props.pregnant === 'B'} />
        </OptionContainer>
      </OptionsContainer>
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ pregnant: state.questionnairePageReducer.data.patient.pregnant });

const mapDispatchToProps = { preChangePregnant };

export default connect(mapStateToProps, mapDispatchToProps)(PregnantQ);
