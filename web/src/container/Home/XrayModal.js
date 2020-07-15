import React from 'react';
import { Modal, Button } from 'antd';
import styled from 'styled-components';

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
  padding: 0 15px;
  border-radius: 4px 4px 0 0;
  font-size: 15px;
  font-weight: bold;
  color: #222b45;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
`;

const ContentContainer = styled.div`
  padding: 16px 60px 24px;
`;

const FooterContainer = styled.div`
  margin-top: 36px;
  display: flex;
  justify-content: center;
`;

const TextContainer = styled.div`
  color: #222b45;
  font-size: 15px;
  & > :first-child {
    font-weight: 600;
  }
  & > div {
    margin-bottom: 19px;
  }
`;

const BlueText = styled.span`
  color: #3266ff;
`;

//#endregion

function XrayModal(props) {
  const { visible, changeVisible } = props;

  const onCancel = () => {
    changeVisible(false);
  };

  const onOk = async () => {
    window.open('dentall://');
  };

  return (
    <StyledModal
      visible={visible}
      zIndex={10000}
      centered
      closable
      bodyStyle={{ padding: '0' }}
      onCancel={onCancel}
      footer={null}
      destroyOnClose
    >
      <HeaderContainer>
        <span>如何開啟 X 光</span>
      </HeaderContainer>
      <ContentContainer>
        <TextContainer>
          <div>
            <span>請依照指示開啟介接應用程式：</span>
          </div>
          <div>
            <span>
              1. 點擊下方按鈕「<BlueText>立即啟用</BlueText>」
            </span>
          </div>
          <div>
            <span>
              2. 進入新頁面後，點擊按鈕「<BlueText>開啟「totoro-middleman</BlueText>」
            </span>
          </div>
          <div>
            <span>3. 上述步驟完成後，再次點擊 X 光按鈕</span>
          </div>
        </TextContainer>
        <FooterContainer>
          <Button onClick={onOk} shape="round" type="primary" size="large">
            立即啟用
          </Button>
        </FooterContainer>
      </ContentContainer>
    </StyledModal>
  );
}

export default XrayModal;
