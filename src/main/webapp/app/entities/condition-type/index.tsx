import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConditionType from './condition-type';
import ConditionTypeDetail from './condition-type-detail';
import ConditionTypeUpdate from './condition-type-update';
import ConditionTypeDeleteDialog from './condition-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConditionTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConditionTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConditionTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConditionType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ConditionTypeDeleteDialog} />
  </>
);

export default Routes;
