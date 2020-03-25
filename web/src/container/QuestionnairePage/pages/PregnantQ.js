import React from 'react';
import { connect } from 'react-redux';
import { preChangePregnant } from '../actions';
import { Container } from './Name';
import { Option, CheckedIcon } from './BloodType';
import { OptionContainer } from './Career';
import PageControllContainer from '../PageControllContainer';
import { OptionsContainer } from './DoDrugQ';
import { StyleRightCircleTwoTone } from './Address';

function PregnantQ(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone type="right-circle" theme="twoTone" />
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
