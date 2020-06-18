import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { changeDisease, nextPage } from '../actions';
import { tags } from '../constant_options';
import { Container } from './Name';
import { OptionsContainer, Option, CheckedIcon, OptionContainer } from './BloodType';
import { StyleRightCircleTwoTone } from './Address';

//#region

const Note = styled.span`
  font-size: 12px;
  color: #aaa;
`;
//#endregion

function Disease(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>疾病歷史</span>
        <Note>(可複選)</Note>
      </div>
      <OptionsContainer>
        {tags
          .filter(t => t.jhi_type === 'DISEASE')
          .map(d => {
            const selected = props.disease.includes(d.key);
            return (
              <OptionContainer
                key={d.key}
                onClick={() => {
                  props.changeDisease(d.key);
                }}
                selected={selected}
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

const mapStateToProps = state => ({ disease: state.questionnairePageReducer.data.patient.disease });

const mapDispatchToProps = { changeDisease, nextPage };

export default connect(mapStateToProps, mapDispatchToProps)(Disease);
