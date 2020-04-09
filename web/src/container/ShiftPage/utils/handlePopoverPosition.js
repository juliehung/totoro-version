const handlePopoverPosition = jsEvent => {
  const defaultOffset = 0;
  var windowWidth = window.innerWidth;
  var windowHeight = window.innerHeight;
  const popOverWidth = 370;
  const popOverHeight = 500;
  let x = jsEvent.clientX;
  let y = jsEvent.clientY;
  if (jsEvent.clientX + popOverWidth + defaultOffset > windowWidth) {
    x = x - popOverWidth - defaultOffset;
  } else {
    x = x + defaultOffset + 20;
  }

  if (windowHeight > jsEvent.clientY + popOverHeight + defaultOffset) {
    y = y + defaultOffset;
  } else {
    y = y - popOverHeight / 2 - defaultOffset;
  }

  return { x, y };
};

export default handlePopoverPosition;
