import React from 'react';
import { Button, Icon } from 'antd';

export default function ConfirmButton(props) {
  return (
    <Button type="primary" onClick={props.nextPage} disabled={props.disabled}>
      <span>
        確認
        <Icon type="check" />
      </span>
    </Button>
  );
}
