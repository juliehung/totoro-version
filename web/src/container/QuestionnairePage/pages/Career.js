import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeCareer } from '../actions';
import { Icon } from 'antd';
import { CareerOption } from '../constant_options';

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
  width: 30%;
  margin: 10px 1.5%;
  font-size: 15px;
  color: #000;
  display: flex;
  align-items: center;
  border: ${props => (props.selected ? '2px solid #1890ff' : '2px solid #fff')};
  transition: border 300ms ease-in;
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

function Career(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>職業</span>
      </div>
      <OptionContainer>
        {CareerOption.map(d => (
          <Option
            key={d.key}
            selected={props.career === d.key}
            onClick={() => {
              props.preChangeCareer(d.key);
            }}
          >
            <Alphabet>{d.key}</Alphabet>
            <span>{d.value}</span>
          </Option>
        ))}
      </OptionContainer>
    </Container>
  );
}

const mapStateToProps = state => ({ career: state.questionnairePageReducer.data.career });

const mapDispatchToProps = { preChangeCareer };

export default connect(mapStateToProps, mapDispatchToProps)(Career);
