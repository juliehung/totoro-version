import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProcedureType from './procedure-type';
import ProcedureTypeDetail from './procedure-type-detail';
import ProcedureTypeUpdate from './procedure-type-update';
import ProcedureTypeDeleteDialog from './procedure-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProcedureTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProcedureTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProcedureTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProcedureType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProcedureTypeDeleteDialog} />
  </>
);

export default Routes;
