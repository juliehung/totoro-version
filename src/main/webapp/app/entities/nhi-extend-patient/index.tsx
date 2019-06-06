import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiExtendPatient from './nhi-extend-patient';
import NhiExtendPatientDetail from './nhi-extend-patient-detail';
import NhiExtendPatientUpdate from './nhi-extend-patient-update';
import NhiExtendPatientDeleteDialog from './nhi-extend-patient-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiExtendPatientUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiExtendPatientUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiExtendPatientDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiExtendPatient} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiExtendPatientDeleteDialog} />
  </>
);

export default Routes;
