import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NHICategory from './nhi-category';
import NHICategoryDetail from './nhi-category-detail';
import NHICategoryUpdate from './nhi-category-update';
import NHICategoryDeleteDialog from './nhi-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NHICategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NHICategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NHICategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={NHICategory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NHICategoryDeleteDialog} />
  </>
);

export default Routes;
