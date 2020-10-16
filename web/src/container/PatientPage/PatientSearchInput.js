import React, { useState } from 'react';
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
  const { searchPatient } = props;
  const [searchText, setSearchText] = useState('');

  return (
    <SearchContainer>
      <Input
        size="large"
        placeholder="搜尋病患"
        value={searchText}
        onChange={e => {
          setSearchText(e.target.value);
        }}
        allowClear
      />
      <Button size="large" type="primary" onClick={() => searchPatient(searchText)} icon={<SearchOutlined />} />
    </SearchContainer>
  );
}

export default PatientSearchInput;
