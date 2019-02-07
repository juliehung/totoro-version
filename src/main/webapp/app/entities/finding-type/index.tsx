import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FindingType from './finding-type';
import FindingTypeDetail from './finding-type-detail';
import FindingTypeUpdate from './finding-type-update';
import FindingTypeDeleteDialog from './finding-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FindingTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FindingTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FindingTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={FindingType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FindingTypeDeleteDialog} />
  </>
);

export default Routes;
