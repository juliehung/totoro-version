import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeSmoking } from '../actions';
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

function SmokingQ(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
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
          <Option>A</Option>
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
          <Option>B</Option>
          <span>無</span>
          <CheckedIcon type="check" selected={props.smoking === 'B'} />
        </OptionContainer>
      </OptionsContainer>
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ smoking: state.questionnairePageReducer.data.patient.smoking });

const mapDispatchToProps = { preChangeSmoking };

export default connect(mapStateToProps, mapDispatchToProps)(SmokingQ);
