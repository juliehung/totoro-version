const handlePopoverPosition = jsEvent => {
  const defaultOffset = 20;
  var windowWidth = window.innerWidth;
  const popOverWidth = 370;
  let x = jsEvent.clientX;
  let y = jsEvent.clientY;
  if (jsEvent.clientX + popOverWidth + defaultOffset + 30 > windowWidth) {
    x = x - popOverWidth - defaultOffset;
  } else {
    x = x + defaultOffset;
  }
  y = y + 10;
  return { x, y };
};

export default handlePopoverPosition;
