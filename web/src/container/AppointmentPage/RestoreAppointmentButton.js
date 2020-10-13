import React, { useState } from 'react';
import { Button } from 'antd';

const buttonStatus = { normal: 'normal', firstClick: 'firstClick', loading: 'loading' };
const buttonText = { normal: '恢復預約', firstClick: '再次點擊恢復', loading: '正在恢復' };

function RestoreAppointmentButton(props) {
  const { id, onConfirm } = props;

  const [status, setStatus] = useState(buttonStatus.normal);

  const onClick = () => {
    switch (status) {
      case buttonStatus.normal:
        setStatus(buttonStatus.firstClick);
        break;
      case buttonStatus.firstClick:
        setStatus(buttonStatus.loading);
        onConfirm({ id: id, status: 'CONFIRMED' });
        break;
      default:
        break;
    }
  };

  const onBlur = () => {
    if (status === buttonStatus.firstClick) {
      setStatus(buttonStatus.normal);
    }
  };

  const isLoading = status === buttonStatus.loading;
  const type = [buttonStatus.firstClick, buttonStatus.loading].includes(status) ? 'primary' : 'default';

  return (
    <Button onClick={onClick} loading={isLoading} type={type} size="small" onBlur={onBlur}>
      {buttonText[status]}
    </Button>
  );
}

export default RestoreAppointmentButton;
