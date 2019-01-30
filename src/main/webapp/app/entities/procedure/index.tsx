import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Procedure from './procedure';
import ProcedureDetail from './procedure-detail';
import ProcedureUpdate from './procedure-update';
import ProcedureDeleteDialog from './procedure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProcedureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProcedureDetail} />
      <ErrorBoundaryRoute path={match.url} component={Procedure} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProcedureDeleteDialog} />
  </>
);

export default Routes;
