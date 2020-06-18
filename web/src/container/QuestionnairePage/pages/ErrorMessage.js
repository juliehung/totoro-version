import React from 'react';
import styled from 'styled-components';

const ErrorText = styled.span`
  opacity: 0.61;
  border-radius: 5px;
  background-color: #d0021b;
  padding: 5px 16px;
  font-size: 14px;
  color: #fff;
  user-select: none;
`;

function ErrorMessage(props) {
  const { errorText } = props;
  return (
    <div className="errorMessage">
      <ErrorText>{errorText}</ErrorText>
      <br />
    </div>
  );
}

export default ErrorMessage;
