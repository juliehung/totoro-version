import React from 'react';
import examvision from '../images/eXamvision-54.png';

export default function (props) {
  const width = props.width ?? 20;

  return <img src={examvision} alt={'examvision_icon'} width={width} />;
}
