import React from 'react';
import vixwin from '../images/VixWin2000@4x.png';

export default function (props) {
  const width = props.width ?? 20;

  return <img src={vixwin} alt={'vixwin_icon'} width={width} />;
}
