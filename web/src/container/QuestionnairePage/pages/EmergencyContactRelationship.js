import React from 'react';
import { connect } from 'react-redux';
import { preChangeEmergencyRelationship } from '../actions';
import { RelationshipOption } from '../constant_options';
import { Container } from './Name';
import PageControllContainer from '../PageControllContainer';
import { OptionsContainer, Option, CheckedIcon, OptionContainer } from './BloodType';
import { StyleRightCircleTwoTone } from './Address';

function EmergencyContactRelationship(props) {
  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>緊急聯絡人關係</span>
      </div>
      <OptionsContainer>
        {RelationshipOption.map(d => {
          const selected = d.key === props.emergencyContactRelationship;
          return (
            <OptionContainer
              key={d.key}
              selected={selected}
              onClick={() => {
                props.preChangeEmergencyRelationship(d.key);
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

const mapStateToProps = state => ({
  emergencyContactRelationship: state.questionnairePageReducer.data.patient.emergencyContact.relationship,
});

const mapDispatchToProps = { preChangeEmergencyRelationship };

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactRelationship);
