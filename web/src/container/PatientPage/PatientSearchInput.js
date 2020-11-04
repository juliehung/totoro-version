import React, { useRef, useEffect } from 'react';
import styled from 'styled-components';
import { Input, Button } from 'antd';

import { SearchOutlined } from '@ant-design/icons';

//#region
const SearchContainer = styled.div`
  display: flex;
  max-width: 756px;
  width: 90%;
`;

//#endregion

function PatientSearchInput(props) {
  const { searchPatient, getRegistrationToday, drawerVisible } = props;
  const ref = useRef(null);

  useEffect(() => {
    if (!drawerVisible) {
      ref.current.state.value = '';
    }
  }, [drawerVisible]);

  return (
    <SearchContainer>
      <Input
        size="large"
        placeholder="搜尋病患"
        allowClear
        ref={ref}
        onPressEnter={() => searchPatient(ref.current.state.value)}
        onChange={e => {
          if (e.type === 'click') {
            getRegistrationToday();
          }
        }}
      />
      <Button
        size="large"
        type="primary"
        onClick={() => searchPatient(ref.current.state.value)}
        icon={<SearchOutlined />}
      />
    </SearchContainer>
  );
}

export default PatientSearchInput;
