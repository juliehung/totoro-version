const handlePopoverPosition = (position, popoverSize) => {
  const defaultOffsetY = 20;
  const defaultOffsetX = 15;
  const windowWidth = window.innerWidth;
  const windowHeight = window.innerHeight;
  const popOverWidth = popoverSize.width;
  const popOverHeight = popoverSize.height;
  let x = position.right;
  let y = position.top;

  let vertical;
  let horizontal;
  if (x + popOverWidth + defaultOffsetX > windowWidth) {
    x = position.left - popOverWidth - defaultOffsetX;
    horizontal = 'left';
  } else {
    x = x + defaultOffsetX;
    horizontal = 'right';
  }

  if (windowHeight > y + popOverHeight + defaultOffsetY) {
    y = y + defaultOffsetY;
    vertical = 'top';
  } else if (y < popOverHeight) {
    y = y - popOverHeight / 2;
    vertical = 'center';
  } else {
    vertical = 'bottom';
    y = y - popOverHeight + defaultOffsetY;
  }

  return { x, y, vertical, horizontal };
};

export default handlePopoverPosition;
