import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NHIProcedureType from './nhi-procedure-type';
import NHIProcedureTypeDetail from './nhi-procedure-type-detail';
import NHIProcedureTypeUpdate from './nhi-procedure-type-update';
import NHIProcedureTypeDeleteDialog from './nhi-procedure-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NHIProcedureTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NHIProcedureTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NHIProcedureTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={NHIProcedureType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NHIProcedureTypeDeleteDialog} />
  </>
);

export default Routes;
