import React, { useState } from 'react';
import { connect } from 'react-redux';
import { Drawer, Input, Button } from 'antd';
import { changeDrawerVisible, searchPatient } from './actions';

//#region
//#endregion

function PatientSearchDrawer(props) {
  const { drawerOpen, changeDrawerVisible, patients, searchPatient } = props;
  const [searchText, setSearchText] = useState('');

  return (
    <Drawer
      title="搜尋病患"
      closable={true}
      onClose={() => {
        changeDrawerVisible(false);
      }}
      visible={drawerOpen}
      placement="top"
      height="80%"
    >
      <div style={{ display: 'flex' }}>
        <Input
          placeholder="用生日搜尋"
          value={searchText}
          onChange={e => {
            setSearchText(e.target.value);
          }}
        />
        <Button type="primary" onClick={() => searchPatient(searchText)}>
          搜尋
        </Button>
      </div>
      <div>
        {patients.map(p => (
          <a key={p.id} href={`/#/patient/${p.id}`} onClick={() => changeDrawerVisible(false)}>
            <div
              style={{
                padding: '10px',
                cursor: 'pointer',
                border: '1px solid #ccc',
                borderRadius: '5px',
                marginTop: '10px',
                marginBottom: '10px',
              }}
            >
              <span>{p.name}, </span>
              <span>{p.birth}, </span>
              <span>{p.medicalId} </span>
            </div>
          </a>
        ))}
      </div>
    </Drawer>
  );
}

const mapStateToProps = ({ patientPageReducer }) => ({
  drawerOpen: patientPageReducer.common.drawerOpen,
  patients: patientPageReducer.searchPatient.patients,
});

const mapDispatchToProps = { changeDrawerVisible, searchPatient };

export default connect(mapStateToProps, mapDispatchToProps)(PatientSearchDrawer);
