import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiMonthDeclarationDetails from './nhi-month-declaration-details';
import NhiMonthDeclarationDetailsDetail from './nhi-month-declaration-details-detail';
import NhiMonthDeclarationDetailsUpdate from './nhi-month-declaration-details-update';
import NhiMonthDeclarationDetailsDeleteDialog from './nhi-month-declaration-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiMonthDeclarationDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiMonthDeclarationDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiMonthDeclarationDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiMonthDeclarationDetails} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiMonthDeclarationDetailsDeleteDialog} />
  </>
);

export default Routes;
