import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Calendar from './calendar';
import CalendarDetail from './calendar-detail';
import CalendarUpdate from './calendar-update';
import CalendarDeleteDialog from './calendar-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CalendarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CalendarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CalendarDetail} />
      <ErrorBoundaryRoute path={match.url} component={Calendar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CalendarDeleteDialog} />
  </>
);

export default Routes;
