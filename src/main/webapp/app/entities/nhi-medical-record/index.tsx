import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiMedicalRecord from './nhi-medical-record';
import NhiMedicalRecordDetail from './nhi-medical-record-detail';
import NhiMedicalRecordUpdate from './nhi-medical-record-update';
import NhiMedicalRecordDeleteDialog from './nhi-medical-record-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiMedicalRecordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiMedicalRecordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiMedicalRecordDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiMedicalRecord} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiMedicalRecordDeleteDialog} />
  </>
);

export default Routes;
