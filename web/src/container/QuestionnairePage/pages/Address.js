import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, changeAddress } from '../actions';
import { Container } from './Name';
import { TransparentInput } from './Name';
import ConfirmButton from './ConfirmButton';
import PageControllContainer from '../PageControllContainer';
import { RightCircleTwoTone } from '@ant-design/icons';

//#region

export const StyleRightCircleTwoTone = styled(RightCircleTwoTone)`
  margin-right: 10px;
`;

//#endregion

function Address(props) {
  const onInputChange = e => {
    props.changeAddress(e.target.value);
  };

  const onPressEnter = () => {
    if (props.address && props.address.length !== 0) {
      props.nextPage();
    }
  };

  return (
    <Container>
      <div>
        <StyleRightCircleTwoTone />
        <span>通訊地址</span>
      </div>
      <TransparentInput
        size="large"
        placeholder="請在此鍵入答案"
        onChange={onInputChange}
        value={props.address}
        onPressEnter={onPressEnter}
      />
      <ConfirmButton nextPage={props.nextPage} disabled={!props.address || props.address.length === 0} />
      <PageControllContainer />
    </Container>
  );
}

const mapStateToProps = state => ({ address: state.questionnairePageReducer.data.patient.address });

const mapDispatchToProps = { nextPage, changeAddress };

export default connect(mapStateToProps, mapDispatchToProps)(Address);
