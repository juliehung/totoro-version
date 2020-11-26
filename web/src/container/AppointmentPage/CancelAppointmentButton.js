import React, { useState } from 'react';
import { Button } from 'antd';

const buttonStatus = { normal: 'normal', firstClick: 'firstClick', loading: 'loading' };
const buttonText = { normal: '取消預約', firstClick: '再次點擊取消', loading: '正在取消' };

function CancelAppointmentButton(props) {
  const { id, onConfirm } = props;

  const [status, setStatus] = useState(buttonStatus.normal);

  const onClick = () => {
    switch (status) {
      case buttonStatus.normal:
        setStatus(buttonStatus.firstClick);
        break;
      case buttonStatus.firstClick:
        setStatus(buttonStatus.loading);
        onConfirm({ id: id, status: 'CANCEL' });
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
    <Button
      onClick={onClick}
      loading={isLoading}
      type={type}
      danger
      size="small"
      onBlur={onBlur}
      className={props?.className || ''}
    >
      {buttonText[status]}
    </Button>
  );
}

export default CancelAppointmentButton;
