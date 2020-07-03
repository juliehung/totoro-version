import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { changeOther, nextPage } from '../actions';
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

function Other(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>牙科治療曾遇到困難</span>
        <Note>(可複選)</Note>
      </div>
      <OptionsContainer>
        {tags
          .filter(t => t.key)
          .filter(t => t.jhi_type === 'OTHER')
          .map(d => (
            <OptionContainer
              key={d.key}
              onClick={() => {
                props.changeOther(d.key);
              }}
              selected={props.other.includes(d.key)}
            >
              <Option selected={props.other.includes(d.key)}>{d.key}</Option>
              <span>{d.value}</span>
              <CheckedIcon type="check" selected={props.other.includes(d.key)} />
            </OptionContainer>
          ))}
      </OptionsContainer>
    </Container>
  );
}

const mapStateToProps = state => ({ other: state.questionnairePageReducer.data.patient.other });

const mapDispatchToProps = { changeOther, nextPage };

export default connect(mapStateToProps, mapDispatchToProps)(Other);
