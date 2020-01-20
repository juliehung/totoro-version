import React from 'react';
import moment from 'moment';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeBirth } from '../actions';
import { Icon, Input } from 'antd';
import { Container } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;

export const StyledInput = styled(Input)`
  margin: 20px 0 !important;
  font-size: 24px !important;
`;

//#endregion

function Birth(props) {
  const onChange = e => {
    props.changeBirth(e.target.value);
  };

  return (
    <Container>
      <div>
        <StyleIcon type="right-circle" theme="twoTone" />
        <span>生日*</span>
      </div>
      <StyledInput
        type="date"
        size="large"
        value={props.birth}
        onChange={onChange}
        max={moment().format('YYYY-MM-DD')}
        min={moment()
          .add(-150, 'year')
          .format('YYYY-MM-DD')}
      />
      <br />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.birth} />
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ birth: state.questionnairePageReducer.data.patient.birth });

const mapDispatchToProps = { nextPage, changeBirth };

export default connect(mapStateToProps, mapDispatchToProps)(Birth);
