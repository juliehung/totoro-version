import React from 'react';
import ReactDOM from 'react-dom';
import styled from 'styled-components';
import { Popover, Button } from 'antd';
//#region
const Container = styled.div`
  display: flex;
  align-items: center;
`;

const Color = styled.div`
  background-color: ${props => (props.color ? props.color : '#dea')};
  height: 25px;
  width: 25px;
  border-radius: 50%;
  margin-right: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
`;

const PopoverContainer = styled.div`
  width: 160px;
  padding: 15px 8px 2px 16px;
  margin: -12px -16px;
`;

const ColorPlateContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
`;

const StyledButton = styled(Button)`
  padding: 0 !important;
`;

const ColorContainer = styled.div`
  margin-right: 8px;
  margin-bottom: 13px;
  background: ${props => props.color};
  border-radius: 50%;
  width: 16px;
  height: 16px;
`;
//#endregion

export const eventColors = [
  '#1890FF',
  '#FF7A45',
  '#13C2C2',
  '#9254DE',
  '#F759AB',
  '#531DAB',
  '#42734C',
  '#D4B106',
  '#CF1322',
  '#52C41A',
];

export const handleResourceRender = ({ resource, el }, utils) => {
  const div = document.createElement('div');
  const p = document.createElement('p');
  div.appendChild(p);

  const container = el.querySelector('div');
  container.style.display = 'flex';
  container.style.justifyContent = 'center';
  container.style.alignItems = 'center';

  const colorPlate = (
    <PopoverContainer>
      <ColorPlateContainer>
        {eventColors.map(color => (
          <StyledButton
            key={color}
            type="link"
            onClick={async () => {
              utils.colorClick(resource.id, color);
            }}
          >
            <ColorContainer key={color} color={color} />
          </StyledButton>
        ))}
      </ColorPlateContainer>
    </PopoverContainer>
  );

  const id = Object.keys(utils.resourceColor).find(id => id === resource.id);
  let color;
  if (id) {
    color = utils.resourceColor[id];
  }

  const content = (
    <Container>
      <Popover content={colorPlate} trigger="click" placement="right" className="popover-app-detail">
        <Color color={color}>{resource.title[0]}</Color>
      </Popover>
      <span>{resource.title}</span>
    </Container>
  );

  ReactDOM.render(content, el.querySelector('.fc-cell-text'));
};
