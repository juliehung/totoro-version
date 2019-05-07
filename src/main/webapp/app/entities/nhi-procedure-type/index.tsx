import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiProcedureType from './nhi-procedure-type';
import NhiProcedureTypeDetail from './nhi-procedure-type-detail';
import NhiProcedureTypeUpdate from './nhi-procedure-type-update';
import NhiProcedureTypeDeleteDialog from './nhi-procedure-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiProcedureTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiProcedureTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiProcedureTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiProcedureType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiProcedureTypeDeleteDialog} />
  </>
);

export default Routes;
