import React, { useState } from 'react';
import { Button } from 'antd';

const buttonStatus = { normal: 'normal', loading: 'loading' };
const buttonText = { normal: '恢復預約', loading: '正在恢復' };

function RestoreAppointmentButton(props) {
  const { id, onConfirm } = props;

  const [status, setStatus] = useState(buttonStatus.normal);

  const onClick = () => {
    setStatus(buttonStatus.loading);
    onConfirm({ id: id, status: 'CONFIRMED' });
  };

  const isLoading = status === buttonStatus.loading;

  return (
    <Button onClick={onClick} loading={isLoading} size="small" className={props?.className || ''}>
      {buttonText[status]}
    </Button>
  );
}

export default RestoreAppointmentButton;
