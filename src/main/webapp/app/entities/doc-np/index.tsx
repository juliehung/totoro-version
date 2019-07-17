import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DocNp from './doc-np';
import DocNpDetail from './doc-np-detail';
import DocNpUpdate from './doc-np-update';
import DocNpDeleteDialog from './doc-np-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DocNpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DocNpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DocNpDetail} />
      <ErrorBoundaryRoute path={match.url} component={DocNp} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DocNpDeleteDialog} />
  </>
);

export default Routes;
