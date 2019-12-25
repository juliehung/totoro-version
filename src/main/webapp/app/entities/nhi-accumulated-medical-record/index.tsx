import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiAccumulatedMedicalRecord from './nhi-accumulated-medical-record';
import NhiAccumulatedMedicalRecordDetail from './nhi-accumulated-medical-record-detail';
import NhiAccumulatedMedicalRecordUpdate from './nhi-accumulated-medical-record-update';
import NhiAccumulatedMedicalRecordDeleteDialog from './nhi-accumulated-medical-record-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiAccumulatedMedicalRecordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiAccumulatedMedicalRecordUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiAccumulatedMedicalRecordDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiAccumulatedMedicalRecord} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiAccumulatedMedicalRecordDeleteDialog} />
  </>
);

export default Routes;
