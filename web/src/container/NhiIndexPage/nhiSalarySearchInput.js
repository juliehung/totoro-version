import React, { useRef } from 'react';
import { Input } from 'antd';
import searchFilteredNhiData from './utils/searchFilteredNhiData';

const { Search } = Input;

function NhiSalarySearchInput(props) {
  const { className, validNhiData = {}, filterValidNhiData = () => {}, onChangeValue = () => {} } = props;
  const ref = useRef(null);

  return (
    <Search
      className={className}
      allowClear={true}
      onSearch={() => {
        if (typeof ref.current.state.value === 'string') {
          onChangeValue(ref.current.state.value.toString());
          filterValidNhiData(searchFilteredNhiData(ref.current.state.value.toString(), validNhiData));
        }
      }}
      placeholder={'搜尋姓名,生日或流水號'}
      ref={ref}
    />
  );
}

export default NhiSalarySearchInput;
