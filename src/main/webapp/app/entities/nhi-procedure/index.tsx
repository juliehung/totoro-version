import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NHIProcedure from './nhi-procedure';
import NHIProcedureDetail from './nhi-procedure-detail';
import NHIProcedureUpdate from './nhi-procedure-update';
import NHIProcedureDeleteDialog from './nhi-procedure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NHIProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NHIProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NHIProcedureDetail} />
      <ErrorBoundaryRoute path={match.url} component={NHIProcedure} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NHIProcedureDeleteDialog} />
  </>
);

export default Routes;
