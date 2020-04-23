import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { RedEnvelopeOutlined } from '@ant-design/icons';

const RootContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
`;

const BoneContainer = styled.div`
  display: flex;
  margin: 32px;
  align-items: center;
  justify-content: center;
`;

function EventCardEmpty(props) {
  return ( 
    <RootContainer>
      <BoneContainer>
        <RedEnvelopeOutlined style={{ fontSize: '100px' }}/>
      </BoneContainer>
    </RootContainer>
  );
}
const mapStateToProps = () => ({});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(EventCardEmpty);
