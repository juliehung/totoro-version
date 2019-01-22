import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NHIUnusalContent from './nhi-unusal-content';
import NHIUnusalContentDetail from './nhi-unusal-content-detail';
import NHIUnusalContentUpdate from './nhi-unusal-content-update';
import NHIUnusalContentDeleteDialog from './nhi-unusal-content-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NHIUnusalContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NHIUnusalContentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NHIUnusalContentDetail} />
      <ErrorBoundaryRoute path={match.url} component={NHIUnusalContent} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NHIUnusalContentDeleteDialog} />
  </>
);

export default Routes;
