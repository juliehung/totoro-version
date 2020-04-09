import React from 'react';
import ReactDOM from 'react-dom';
import styled from 'styled-components';
import { Popover, Button } from 'antd';
import totoro from '../../../images/totoro.jpg';

//#region
const Container = styled.div`
  display: flex;
  align-items: center;
`;

const AvatarContainer = styled.div`
  position: relative;
  cursor: pointer;
  margin-right: 10px;

  & > div:first-child {
    height: 40px;
    width: 40px;
    border-radius: 50%;
    overflow: hidden;

    & > img {
      width: 40px;
    }
  }
`;

const Color = styled.div`
  background-color: ${props => (props.color ? props.color : '#1890ff')};
  height: 15px;
  width: 15px;
  border: 3px #fff solid;
  border-radius: 50%;
  position: absolute;
  bottom: 0;
  right: 0;
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
  const container = el.querySelector('div');
  container.style.display = 'flex';
  container.style.alignItems = 'center';
  container.style.minHeight = '60px';

  const fcCellContent = el.querySelector('div.fc-cell-content');
  if (fcCellContent) {
    fcCellContent.style.display = 'flex';
    fcCellContent.style.alignItems = 'center';
  }

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
      <Popover content={colorPlate} trigger="click" placement="right">
        <AvatarContainer>
          <div>
            <img alt="avatar" src={totoro}></img>
          </div>
          <Color color={color}></Color>
        </AvatarContainer>
      </Popover>
      <span>{resource.title}</span>
    </Container>
  );

  ReactDOM.render(content, el.querySelector('.fc-cell-text'));
};
