import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiProcedure from './nhi-procedure';
import NhiProcedureDetail from './nhi-procedure-detail';
import NhiProcedureUpdate from './nhi-procedure-update';
import NhiProcedureDeleteDialog from './nhi-procedure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiProcedureDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiProcedure} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiProcedureDeleteDialog} />
  </>
);

export default Routes;
