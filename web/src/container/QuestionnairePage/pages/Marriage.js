import React from 'react';
import { connect } from 'react-redux';
import { preChangeMarriage } from '../actions';
import { MarriageOption } from '../constant_options';
import { Container } from './Name';
import { OptionsContainer, Option, CheckedIcon, OptionContainer } from './BloodType';
import { StyleRightCircleTwoTone } from './Address';

function Marriage(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>婚姻</span>
      </div>
      <OptionsContainer>
        {MarriageOption.map(d => {
          const selected = props.marriage === d.key;
          return (
            <OptionContainer
              key={d.key}
              selected={selected}
              onClick={() => {
                props.preChangeMarriage(d.key);
              }}
            >
              <Option selected={selected}>{d.key}</Option>
              <span>{d.value}</span>
              <CheckedIcon type="check" selected={selected} />
            </OptionContainer>
          );
        })}
      </OptionsContainer>
    </Container>
  );
}

const mapStateToProps = state => ({ marriage: state.questionnairePageReducer.data.patient.marriage });

const mapDispatchToProps = { preChangeMarriage };

export default connect(mapStateToProps, mapDispatchToProps)(Marriage);
