import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeCareer } from '../actions';
import { CareerOption } from '../constant_options';
import { Container } from './Name';
import { OptionsContainer, Option, CheckedIcon } from './BloodType';
import { StyleRightCircleTwoTone } from './Address';

//#region

export const OptionContainer = styled.div`
  position: relative;
  box-sizing: border-box;
  width: 28%;
  padding: 8px 15px;
  align-items: center;
  margin: 10px 2.5%;
  font-size: 16px;
  color: ${props => (props.selected ? '#1890ff' : '#000')};
  display: flex;
  border: ${props => (props.selected ? '3px solid #1890ff' : '2px solid transparent')};
  box-shadow: ${props => (props.selected ? '0px 0px 0 2px rgb(0, 145, 255, 0.33)' : '0px transparent')};
  border-radius: 2px;
  cursor: pointer;
  & > span {
    margin-left: 10px;
  }
  transition: all ease-in-out 200ms;
`;

//#endregion

function Career(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>職業</span>
      </div>
      <OptionsContainer>
        {CareerOption.map(d => {
          const selected = props.career === d.key;
          return (
            <OptionContainer
              key={d.key}
              selected={selected}
              onClick={() => {
                props.preChangeCareer(d.key);
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

const mapStateToProps = state => ({ career: state.questionnairePageReducer.data.patient.career });

const mapDispatchToProps = { preChangeCareer };

export default connect(mapStateToProps, mapDispatchToProps)(Career);
