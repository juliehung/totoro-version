import React, { useRef } from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import SignatureCanvas from 'react-signature-canvas';
import { Button, Icon } from 'antd';
import { changeIsSigEmpty, createQWSign } from '../actions';
import { trimDataUrl } from '../utils/trimDataUrl';

//#region
const Container = styled.div`
  max-width: 600px;
  width: 95%;
  background: transparent;
`;

const HeadContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled.p`
  color: rgb(25, 155, 255);
  font-size: 24px;
  font-weight: 600;
`;

const SubTitle = styled.p`
  color: rgb(130, 137, 145);
  font-size: 14px;
`;

const SigContainer = styled.div`
  border: 1px solid #dadada;
  background: #f5f5f5;
  border-radius: 3px;
  position: relative;
`;

const ClearButton = styled(Button)`
  position: absolute !important;
  left: 10px;
  top: 10px;
`;
//#endregion

function Signature(props) {
  let sigRef = useRef(null);

  const clearSig = () => {
    sigRef.clear();
    props.changeIsSigEmpty(sigRef.isEmpty());
  };

  const onEnd = () => {
    props.changeIsSigEmpty(sigRef.isEmpty());
  };

  const SendWSign = () => {
    const imgURL = trimDataUrl(sigRef.toDataURL());
    props.createQWSign(imgURL);
  };

  return (
    <Container>
      <HeadContainer>
        <div>
          <Title>數位簽章</Title>
          <SubTitle>請簽下您的姓名以及當天日期</SubTitle>
        </div>
        {props.isEmpty ? (
          <Button size="large">
            不簽名直接送出
            <Icon type="check" />
          </Button>
        ) : (
          <Button size="large" type="primary" onClick={SendWSign}>
            送出
            <Icon type="check" />
          </Button>
        )}
      </HeadContainer>
      <SigContainer>
        <ClearButton onClick={clearSig}>清除</ClearButton>
        <SignatureCanvas
          penColor="black"
          minDistance={0.1}
          velocityFilterWeight={0.5}
          canvasProps={{
            width: '598',
            height: '400',
            className: 'sigCanvas',
          }}
          ref={ref => (sigRef = ref)}
          onEnd={onEnd}
        />
      </SigContainer>
    </Container>
  );
}
const mapStateToProps = ({ questionnairePageReducer }) => ({
  visible: questionnairePageReducer.flow.signatureModalVisible,
  isEmpty: questionnairePageReducer.data.isSigEmpty,
});

const mapDispatchToProps = { changeIsSigEmpty, createQWSign };

export default connect(mapStateToProps, mapDispatchToProps)(Signature);
