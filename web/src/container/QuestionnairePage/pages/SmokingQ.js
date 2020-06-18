import React from 'react';
import { connect } from 'react-redux';
import { preChangeSmoking } from '../actions';
import { Container } from './Name';
import { Option, CheckedIcon } from './BloodType';
import { OptionContainer } from './Career';
import { OptionsContainer } from './DoDrugQ';
import { StyleRightCircleTwoTone } from './Address';

function SmokingQ(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>吸菸</span>
      </div>
      <OptionsContainer>
        <OptionContainer
          key="A"
          selected={props.smoking === 'A'}
          onClick={() => {
            props.preChangeSmoking('A');
          }}
        >
          <Option selected={props.smoking === 'A'}>A</Option>
          <span>有</span>
          <CheckedIcon type="check" selected={props.smoking === 'A'} />
        </OptionContainer>
        <OptionContainer
          key="B"
          selected={props.smoking === 'B'}
          onClick={() => {
            props.preChangeSmoking('B');
          }}
        >
          <Option selected={props.smoking === 'B'}>B</Option>
          <span>無</span>
          <CheckedIcon type="check" selected={props.smoking === 'B'} />
        </OptionContainer>
      </OptionsContainer>
    </Container>
  );
}

const mapStateToProps = state => ({ smoking: state.questionnairePageReducer.data.patient.smoking });

const mapDispatchToProps = { preChangeSmoking };

export default connect(mapStateToProps, mapDispatchToProps)(SmokingQ);
