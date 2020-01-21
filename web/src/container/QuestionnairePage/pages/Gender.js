import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeGender } from '../actions';
import { Icon } from 'antd';
import { GenderOption } from '../constant_options';
import { Container } from './Name';
import PageControllContainer from '../PageControllContainer';

//#region
const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

const CardContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
`;

const StyledCard = styled.div`
  width: 30%;
  font-size: 16px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  align-items: center;
  border: ${props => (props.selected ? '3px solid #1890ff' : '2px solid transparent')};
  box-shadow: ${props => (props.selected ? '0px 0px 0 2px rgb(0, 145, 255, 0.33)' : '0px transparent')};
  border-radius: 2px;
  cursor: pointer;
  transition: all ease-in-out 200ms;
`;

export const OptionContainer = styled.div`
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

export const TextContainer = styled.div`
  color: #000;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  & > span {
    margin-left: 10px;
  }
`;

const StyledImg = styled.img`
  max-width: 120px;
  width: 80%;
  height: auto;
  margin: 25px 40px;
`;

//#endregion

function Gender(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>性別</span>
      </div>
      <CardContainer>
        {GenderOption.map(g => {
          const selected = props.gender === g.key;
          return (
            <StyledCard
              key={g.key}
              selected={selected}
              onClick={() => {
                props.preChangeGender(g.key);
              }}
            >
              <StyledImg alt="example" src={g.image} />
              <TextContainer>
                <OptionContainer selected={selected}>{g.key}</OptionContainer>
                <span>{g.value}</span>
              </TextContainer>
            </StyledCard>
          );
        })}
      </CardContainer>
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({
  page: state.questionnairePageReducer.flow.page,
  gender: state.questionnairePageReducer.data.patient.gender,
});

const mapDispatchToProps = { preChangeGender };

export default connect(mapStateToProps, mapDispatchToProps)(Gender);
