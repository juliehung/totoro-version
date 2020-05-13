import React from 'react';
import ReactDOM from 'react-dom';
import styled from 'styled-components';

const ResourceContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px auto;
  & > :first-child {
    width: 35px;
    height: 35px;
    border-radius: 50%;
    background-color: #f1f1f1;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 5px;
    user-select: none;
    object-fit: cover;
  }
  & > span {
    width: 90%;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
`;

export const handleResourceRender = ({ resource, el }) => {
  const content = (
    <ResourceContainer>
      {resource.extendedProps.avatar ? (
        <img alt="avatar" src={`data:image/png;base64,${resource.extendedProps.avatar}`} />
      ) : (
        <div>{resource.title[0]}</div>
      )}
      <span>{resource.title}</span>
    </ResourceContainer>
  );
  ReactDOM.render(content, el);
  return el;
};
