import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeGender } from '../actions';
import { Icon, Card } from 'antd';
import { GenderOption } from '../constant_options';
import { Container } from './Name';

//#region
const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

const CardContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
`;

const StyledCard = styled(Card)`
  box-sizing: border-box;
  margin: 0 20px !important;
  border: ${props => (props.selected ? '2px solid #1890ff' : '2px solid #fff')}!important;
  transition: border 300ms ease-in;
`;

const OptionContainer = styled.div`
  width: 20px;
  height: 20px;
  border: 1px solid #ccc;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const TextContainer = styled.div`
  color: #000;
  display: flex;
  & > span {
    margin-left: 10px;
  }
`;

const StyledImg = styled.img`
  padding: 10px;
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
        {GenderOption.map(g => (
          <StyledCard
            hoverable
            key={g.key}
            cover={<StyledImg alt="example" src={g.image} />}
            selected={props.gender === g.key}
            onClick={() => {
              props.preChangeGender(g.key);
            }}
          >
            <TextContainer>
              <OptionContainer>{g.key}</OptionContainer>
              <span>{g.value}</span>
            </TextContainer>
          </StyledCard>
        ))}
      </CardContainer>
    </Container>
  );
}

const mapStateToProps = state => ({
  page: state.questionnairePageReducer.flow.page,
  gender: state.questionnairePageReducer.data.patient.gender,
});

const mapDispatchToProps = { preChangeGender };

export default connect(mapStateToProps, mapDispatchToProps)(Gender);
