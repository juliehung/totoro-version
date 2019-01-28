import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tooth from './tooth';
import ToothDetail from './tooth-detail';
import ToothUpdate from './tooth-update';
import ToothDeleteDialog from './tooth-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ToothUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ToothUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ToothDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tooth} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ToothDeleteDialog} />
  </>
);

export default Routes;
