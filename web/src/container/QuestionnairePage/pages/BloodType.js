import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeBloodType } from '../actions';
import { Icon } from 'antd';
import { BloodTypeOption } from '../constant_options';

import { Container } from './Name';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

const OptionContainer = styled.div`
  display: flex;
  margin: 30px 0;
  flex-wrap: wrap;
`;

const Option = styled.div`
  box-sizing: border-box;
  width: 15%;
  margin: 10px 2.5%;
  font-size: 15px;
  color: #000;
  display: flex;
  border: ${props => (props.selected ? '2px solid #1890ff' : '2px solid #fff')};
  transition: border 300ms ease-in;
  align-items: center;
  cursor: pointer;
  & > span {
    margin-left: 10px;
  }
`;

const Alphabet = styled.div`
  width: 20px;
  height: 20px;
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
`;

//#endregion

function BloodType(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>血型</span>
      </div>
      <OptionContainer>
        {BloodTypeOption.map(d => {
          return (
            <Option
              key={d.key}
              selected={props.bloodType === d.key}
              onClick={() => {
                props.preChangeBloodType(d.key);
              }}
            >
              <Alphabet>{d.key}</Alphabet>
              <span>{d.value}</span>
            </Option>
          );
        })}
      </OptionContainer>
    </Container>
  );
}

const mapStateToProps = state => ({ bloodType: state.questionnairePageReducer.data.bloodType });

const mapDispatchToProps = { preChangeBloodType };

export default connect(mapStateToProps, mapDispatchToProps)(BloodType);
