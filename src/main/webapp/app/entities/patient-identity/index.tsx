import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PatientIdentity from './patient-identity';
import PatientIdentityDetail from './patient-identity-detail';
import PatientIdentityUpdate from './patient-identity-update';
import PatientIdentityDeleteDialog from './patient-identity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PatientIdentityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PatientIdentityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PatientIdentityDetail} />
      <ErrorBoundaryRoute path={match.url} component={PatientIdentity} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PatientIdentityDeleteDialog} />
  </>
);

export default Routes;
