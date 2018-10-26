import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Registration from './registration';
import RegistrationDetail from './registration-detail';
import RegistrationUpdate from './registration-update';
import RegistrationDeleteDialog from './registration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RegistrationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RegistrationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RegistrationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Registration} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RegistrationDeleteDialog} />
  </>
);

export default Routes;
