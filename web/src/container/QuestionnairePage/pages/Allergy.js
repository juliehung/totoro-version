import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { changeAllergy, nextPage } from '../actions';
import { tags } from '../constant_options';
import { Container } from './Name';
import { OptionsContainer, Option, CheckedIcon } from './BloodType';
import { OptionContainer } from './Career';
import { StyleRightCircleTwoTone } from './Address';

//#region

const Note = styled.span`
  font-size: 12px;
  color: #aaa;
`;

//#endregion

function Allergy(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>藥物過敏</span>
        <Note>(可複選)</Note>
      </div>
      <OptionsContainer>
        {tags
          .filter(t => t.jhi_type === 'ALLERGY')
          .map(d => {
            const selected = props.allergy.includes(d.key);
            return (
              <OptionContainer
                key={d.key}
                onClick={() => {
                  props.changeAllergy(d.key);
                }}
                selected={selected}
              >
                <Option selected={selected}>{d.key}</Option>
                <span>{d.value}</span>
                <CheckedIcon selected={selected} />
              </OptionContainer>
            );
          })}
      </OptionsContainer>
    </Container>
  );
}

const mapStateToProps = state => ({ allergy: state.questionnairePageReducer.data.patient.allergy });

const mapDispatchToProps = { changeAllergy, nextPage };

export default connect(mapStateToProps, mapDispatchToProps)(Allergy);
