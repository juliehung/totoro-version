import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CalendarSetting from './calendar-setting';
import CalendarSettingDetail from './calendar-setting-detail';
import CalendarSettingUpdate from './calendar-setting-update';
import CalendarSettingDeleteDialog from './calendar-setting-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CalendarSettingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CalendarSettingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CalendarSettingDetail} />
      <ErrorBoundaryRoute path={match.url} component={CalendarSetting} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CalendarSettingDeleteDialog} />
  </>
);

export default Routes;
