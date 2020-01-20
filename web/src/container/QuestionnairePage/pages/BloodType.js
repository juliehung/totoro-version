import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeBloodType } from '../actions';
import { Icon } from 'antd';
import { BloodTypeOption } from '../constant_options';
import { Container } from './Name';
import PageControllContainer from '../PageControllContainer';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

export const OptionContainer = styled.div`
  position: relative;
  box-sizing: border-box;
  width: 20%;
  padding: 8px 15px;
  align-items: center;
  margin: 10px 2.5%;
  font-size: 16px;
  color: ${props => (props.selected ? '#1890ff' : '#000')};
  display: flex;
  border: ${props => (props.selected ? '2px solid #1890ff' : '2px solid transparent')};
  outline: ${props => (props.selected ? '2px solid rgb(0, 145, 255,0.33)' : '2px solid transparent')};
  cursor: pointer;
  & > span {
    margin-left: 10px;
  }
  transition: all ease-in-out 200ms;
`;

export const Option = styled.div`
  border-radius: 1px;
  border: ${props => (props.selected ? '1px solid rgb(0, 145, 255)' : '1px solid rgb(151, 151, 151)')};
  background: ${props => (props.selected ? 'rgb(0, 145, 255)' : 'rgb(245, 246, 250)')};
  color: ${props => (props.selected ? '#fff' : '#000')};
  height: 22px;
  width: 22px;
  font-size: 14px;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all ease-in-out 200ms;
`;

export const OptionsContainer = styled.div`
  color: #000;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin: 25px 0;
`;

export const CheckedIcon = styled(Icon)`
  position: absolute;
  right: 5px;
  visibility: ${props => (props.selected ? 'show' : 'hidden')};
`;
//#endregion

function BloodType(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>血型</span>
      </div>
      <OptionsContainer>
        {BloodTypeOption.map(d => {
          const selected = props.bloodType === d.key;
          return (
            <OptionContainer
              key={d.key}
              selected={selected}
              onClick={() => {
                props.preChangeBloodType(d.key);
              }}
            >
              <Option selected={selected}>{d.key}</Option>
              <span>{d.value}</span>
              <CheckedIcon type="check" selected={selected} />
            </OptionContainer>
          );
        })}
      </OptionsContainer>
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ bloodType: state.questionnairePageReducer.data.patient.bloodType });

const mapDispatchToProps = { preChangeBloodType };

export default connect(mapStateToProps, mapDispatchToProps)(BloodType);
