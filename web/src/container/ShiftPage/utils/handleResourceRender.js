import React from 'react';
import ReactDOM from 'react-dom';
import styled from 'styled-components';

//#region
const Container = styled.div`
  display: flex;
  align-items: center;
  & > div:first-child {
    background-color: #ae1234;
    height: 25px;
    width: 25px;
    border-radius: 50%;
    margin-right: 10px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
  }
`;

//#endregion

export const handleResourceRender = ({ resource, el }) => {
  var div = document.createElement('div');
  var p = document.createElement('p');
  div.appendChild(p);

  console.log(777, resource);

  const container = el.querySelector('div');
  container.style.display = 'flex';
  container.style.justifyContent = 'center';
  container.style.alignItems = 'center';

  const content = (
    <Container>
      <div
        onClick={() => {
          console.log(resource.id);
        }}
      >
        {resource.title[0]}
      </div>
      <span>{resource.title}</span>
    </Container>
  );

  ReactDOM.render(content, el.querySelector('.fc-cell-text'));
};
