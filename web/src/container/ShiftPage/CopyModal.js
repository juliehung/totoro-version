import React from 'react';
import { connect } from 'react-redux';
import { Radio, Modal } from 'antd';
import styled from 'styled-components';
import AlertTrangle from '../../images/alert-triangle-fill.svg';
import { rangeDisplay } from './utils/rangeDisplay';
import { onCopyShift, changeDeleteCurrent, changeCopyModalVisible } from './actions';

//#region
const StyledModal = styled(Modal)`
  & .ant-modal-content {
    border-radius: 8px;
  }

  & .ant-modal-close {
    &:active {
      transform: translateY(2px) translateX(-2px);
    }
  }
  & .ant-modal-close-x {
    width: 32px;
    height: 32px;
    position: absolute;
    top: -10px;
    right: -10px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 5px 20px 0 rgba(0, 0, 0, 0.1);
  }
  & .ant-modal-close-icon {
    display: flex;
    height: 100%;
    justify-content: center;
    align-items: center;
    & > svg {
      fill: black;
    }
  }
`;

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  background: #f8fafb;
  height: 56px;
  padding: 0 24px;
  border-radius: 4px 4px 0 0;
  font-size: 18px;
  font-weight: bold;
  color: #222b45;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
`;

const ContentContainer = styled.div`
  padding: 24px;

  & > :first-child {
    background-color: #fdf1db;
    border-radius: 8px;
    width: 100%;
    padding: 18px 30px;
    & > :first-child {
      margin-right: 14px;
    }
  }
  & > :nth-child(2) {
    margin-top: 30px;
    font-size: 15px;
    color: #8f9bb3;
    font-weight: 600;
    display: flex;
    flex-direction: column;
    & > :first-child {
      margin-bottom: 10px;
    }
  }
`;

const radioStyle = {
  display: 'block',
  height: '30px',
  lineHeight: '30px',
  color: '#222b45',
  fontSize: '13px',
  fontWeight: 600,
  marginBottom: '16px',
};

const FooterContainer = styled.div`
  display: flex;
  justify-content: flex-end;
`;

const Button = styled.button`
  font-size: 14px;
  height: 40px;
  border: 0;
  outline: 0;
  padding-right: 40px !important;
  padding-left: 40px !important;
  border-radius: 34px !important;
  cursor: pointer;
  transition: box-shadow 200ms ease;
  font-weight: bold;
`;

const StyledButton = styled(Button)`
  background-color: #3266ff;
  color: #ffffff;
  &:hover {
    background-color: #3266ff;
    box-shadow: 0 8px 12px -8px rgba(50, 102, 255, 0.3);
  }
  &:active {
    background-color: #244edb;
  }
`;

const NoStyleButton = styled(Button)`
  background-color: rgba(249, 249, 249, 1);
  color: #8f9bb3;
  &:hover {
    background-color: rgba(143, 155, 179, 0.08);
  }
  &:active {
    background-color: rgba(143, 155, 179, 0.16);
  }
`;
//#endregion

function CopyModal(props) {
  const {
    visible,
    range,
    prevRange,
    onCopyShift,
    radioValue,
    changeDeleteCurrent,
    loading,
    changeCopyModalVisible,
    doctor,
    selectAllDoctor,
  } = props;

  const onCancel = () => {
    changeCopyModalVisible(false);
  };

  return (
    <StyledModal
      visible={visible}
      centered
      closable
      bodyStyle={{ padding: '0' }}
      onCancel={onCancel}
      footer={null}
      destroyOnClose
    >
      <HeaderContainer>
        <span>套用上一週班表</span>
      </HeaderContainer>
      <ContentContainer>
        <div>
          <img src={AlertTrangle} alt="alert" />
          <span>
            將複製 <b>{rangeDisplay(prevRange)}</b> 班表到 <b>{rangeDisplay(range)}</b>
          </span>
        </div>
        <div>
          <span>{`您想如何複製到 ${selectAllDoctor ? '全部' : doctor ? doctor.name : ''} 醫師呢？`}</span>
          <Radio.Group
            value={radioValue}
            onChange={e => {
              changeDeleteCurrent(e.target.value);
            }}
          >
            <Radio style={radioStyle} value={'delete'}>
              清除原有，並加入新的
            </Radio>
            <Radio style={radioStyle} value={'remain'}>
              保留原有，並加入新的
            </Radio>
          </Radio.Group>
        </div>
        <FooterContainer>
          <NoStyleButton onClick={onCancel} disabled={loading}>
            取消
          </NoStyleButton>
          <StyledButton onClick={onCopyShift} disabled={loading}>
            確認
          </StyledButton>
        </FooterContainer>
      </ContentContainer>
    </StyledModal>
  );
}

const mapStateToProps = ({ shiftPageReducer }) => ({
  range: shiftPageReducer.copy.range,
  doctor: shiftPageReducer.copy.doctor,
  prevRange: shiftPageReducer.copy.prevRange,
  loading: shiftPageReducer.copy.loading,
  radioValue: shiftPageReducer.copy.deleteCurrent ? 'delete' : 'remain',
  visible: shiftPageReducer.copy.visible,
  selectAllDoctor: shiftPageReducer.copy.selectAllDoctor,
});

const mapDispatchToProps = { onCopyShift, changeDeleteCurrent, changeCopyModalVisible };

export default connect(mapStateToProps, mapDispatchToProps)(CopyModal);
