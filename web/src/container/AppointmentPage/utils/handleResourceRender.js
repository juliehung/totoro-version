import React from 'react';
import ReactDOM from 'react-dom';
import styled from 'styled-components';

const ResourceContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px auto;
  & > div {
    width: 35px;
    height: 35px;
    border-radius: 50%;
    background-color: #f1f1f1;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 5px;
  }
`;

export const handleResourceRender = ({ resource, el }) => {
  const content = (
    <ResourceContainer>
      <div>
        <span>{resource.title.charAt(0)}</span>
      </div>
      <span>{resource.title}</span>
    </ResourceContainer>
  );
  ReactDOM.render(content, el);
  return el;
};
