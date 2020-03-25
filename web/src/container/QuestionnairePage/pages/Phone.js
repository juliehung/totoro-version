import React from 'react';
import { connect } from 'react-redux';
import { nextPage, changePhone } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';
import { StyleRightCircleTwoTone } from './Address';

function Phone(props) {
  const onInputChange = e => {
    props.changePhone(e.target.value);
  };

  const onPressEnter = () => {
    if (props.phone && props.phone.length !== 0) {
      props.nextPage();
    }
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>聯絡電話*</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={props.phone}
        onPressEnter={onPressEnter}
      />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.phone || props.phone.length === 0} />
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ phone: state.questionnairePageReducer.data.patient.phone });

const mapDispatchToProps = { nextPage, changePhone };

export default connect(mapStateToProps, mapDispatchToProps)(Phone);
