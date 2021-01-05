import React from 'react';
import ReactDOM from 'react-dom';
import { CloseOutlined } from '@ant-design/icons';
import styled from 'styled-components';

const Container = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const HoverSpan = styled.span`
  opacity: 0;
  transition: all ease-in-out 300ms;
`;

export function handleEventRender({ el, event }, { deleteShift }) {
  const HoverSpanRef = React.createRef();
  el.className += ' forTransisiton';
  el.style.borderRadius = '3px';
  el.style.fontSize = '1.1em';

  el.addEventListener('mouseover', () => {
    el.style.boxShadow = `0px 8px 25px -8px ${event.backgroundColor}`;
    el.style.transform = 'translate(5px,-5px)';
    HoverSpanRef.current.style.opacity = 1;
  });

  el.addEventListener('mouseout', () => {
    el.style.boxShadow = `none`;
    el.style.transform = 'translate(0,0)';
    HoverSpanRef.current.style.opacity = 0;
  });
  const content = (
    <Container>
      <span>{event?.extendedProps?.displayText || ''}</span>
      <HoverSpan
        ref={HoverSpanRef}
        onClick={() => {
          deleteShift(event.extendedProps.event);
        }}
      >
        <CloseOutlined style={{ pointerEvents: 'none' }} />
      </HoverSpan>
    </Container>
  );
  ReactDOM.render(content, el.querySelector('.fc-title.fc-sticky'));
}
