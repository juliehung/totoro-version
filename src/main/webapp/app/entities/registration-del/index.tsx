import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RegistrationDel from './registration-del';
import RegistrationDelDetail from './registration-del-detail';
import RegistrationDelUpdate from './registration-del-update';
import RegistrationDelDeleteDialog from './registration-del-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RegistrationDelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RegistrationDelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RegistrationDelDetail} />
      <ErrorBoundaryRoute path={match.url} component={RegistrationDel} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RegistrationDelDeleteDialog} />
  </>
);

export default Routes;
