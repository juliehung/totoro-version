import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { changeAllergy, nextPage } from '../actions';
import { Icon, Button } from 'antd';
import { tags } from '../constant_options';

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
  width: 20%;
  margin: 10px 2.5%;
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

const Note = styled.span`
  font-size: 12px;
  color: #aaa;
`;
//#endregion

function Allergy(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>藥物過敏</span>
        <Note>(可複選)</Note>
      </div>
      <OptionContainer>
        {tags
          .filter(t => t.jhi_type === 'ALLERGY')
          .map(d => (
            <Option
              key={d.key}
              onClick={() => {
                props.changeAllergy(d.key);
              }}
              selected={props.allergy.includes(d.key)}
            >
              <Alphabet>{d.key}</Alphabet>
              <span>{d.value}</span>
            </Option>
          ))}
      </OptionContainer>
      <Button type="primary" onClick={props.nextPage}>
        <span>
          確認
          <Icon type="check" />
        </span>
      </Button>
    </Container>
  );
}

const mapStateToProps = state => ({ allergy: state.questionnairePageReducer.data.allergy });

const mapDispatchToProps = { changeAllergy, nextPage };

export default connect(mapStateToProps, mapDispatchToProps)(Allergy);
