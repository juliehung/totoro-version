import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { preChangeMarriage } from '../actions';
import { Icon } from 'antd';
import { MarriageOption } from '../constant_options';
import { Container } from './Name';
import PageControllContainer from '../PageControllContainer';
import { OptionsContainer, Option, CheckedIcon, OptionContainer } from './BloodType';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

//#endregion

function Marriage(props) {
  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>婚姻</span>
      </div>
      <OptionsContainer>
        {MarriageOption.map(d => {
          const selected = props.marriage === d.key;
          return (
            <OptionContainer
              key={d.key}
              selected={selected}
              onClick={() => {
                props.preChangeMarriage(d.key);
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

const mapStateToProps = state => ({ marriage: state.questionnairePageReducer.data.patient.marriage });

const mapDispatchToProps = { preChangeMarriage };

export default connect(mapStateToProps, mapDispatchToProps)(Marriage);
