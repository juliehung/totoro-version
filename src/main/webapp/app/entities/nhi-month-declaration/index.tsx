import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NhiMonthDeclaration from './nhi-month-declaration';
import NhiMonthDeclarationDetail from './nhi-month-declaration-detail';
import NhiMonthDeclarationUpdate from './nhi-month-declaration-update';
import NhiMonthDeclarationDeleteDialog from './nhi-month-declaration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NhiMonthDeclarationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NhiMonthDeclarationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NhiMonthDeclarationDetail} />
      <ErrorBoundaryRoute path={match.url} component={NhiMonthDeclaration} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NhiMonthDeclarationDeleteDialog} />
  </>
);

export default Routes;
