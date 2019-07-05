import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RelationshipOptions from './relationship-options';
import RelationshipOptionsDetail from './relationship-options-detail';
import RelationshipOptionsUpdate from './relationship-options-update';
import RelationshipOptionsDeleteDialog from './relationship-options-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RelationshipOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RelationshipOptionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RelationshipOptionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={RelationshipOptions} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RelationshipOptionsDeleteDialog} />
  </>
);

export default Routes;
