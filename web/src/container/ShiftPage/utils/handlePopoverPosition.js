const handlePopoverPosition = jsEvent => {
  const defaultOffset = 10;
  var windowWidth = window.innerWidth;
  var windowHeight = window.innerHeight;
  const popOverWidth = 370;
  const popOverHeight = 250;
  let x = jsEvent.clientX;
  let y = jsEvent.clientY;
  if (jsEvent.clientX + popOverWidth + defaultOffset + 30 > windowWidth) {
    x = x - popOverWidth - defaultOffset;
  } else {
    x = x + defaultOffset;
  }

  if (jsEvent.clientY + popOverHeight + defaultOffset + 30 > windowHeight) {
    y = y - popOverHeight - defaultOffset;
  } else {
    y = y + defaultOffset;
  }

  return { x, y };
};

export default handlePopoverPosition;
