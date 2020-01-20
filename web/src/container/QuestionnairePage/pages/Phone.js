import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changePhone } from '../actions';
import { Icon } from 'antd';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';

//#region

const StyleIcon = styled(Icon)`
  margin-right: 10px;
`;
//#endregion

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
        <StyleIcon type="right-circle" theme="twoTone" />
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
