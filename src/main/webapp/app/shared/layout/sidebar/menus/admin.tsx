import React from 'react';
import { NavLink } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';
import { Translate, translate } from 'react-jhipster';

const adminMenuItems = (
  <>
    <NavLink tag={Link} to="/admin/user-management">
      <FontAwesomeIcon icon="user" /> <Translate contentKey="global.menu.admin.userManagement">User management</Translate>
    </NavLink>
    <NavLink tag={Link} to="/admin/metrics">
      <FontAwesomeIcon icon="tachometer-alt" /> <Translate contentKey="global.menu.admin.metrics">Metrics</Translate>
    </NavLink>
    <NavLink tag={Link} to="/admin/health">
      <FontAwesomeIcon icon="heart" /> <Translate contentKey="global.menu.admin.health">Health</Translate>
    </NavLink>
    <NavLink tag={Link} to="/admin/configuration">
      <FontAwesomeIcon icon="list" /> <Translate contentKey="global.menu.admin.configuration">Configuration</Translate>
    </NavLink>
    <NavLink tag={Link} to="/admin/audits">
      <FontAwesomeIcon icon="bell" /> <Translate contentKey="global.menu.admin.audits">Audits</Translate>
    </NavLink>
    {/* jhipster-needle-add-element-to-admin-menu - JHipster will add entities to the admin menu here */}
    <NavLink tag={Link} to="/admin/logs">
      <FontAwesomeIcon icon="tasks" /> <Translate contentKey="global.menu.admin.logs">Logs</Translate>
    </NavLink>
  </>
);

const swaggerItem = (
  <NavLink tag={Link} to="/admin/docs">
    <FontAwesomeIcon icon="book" /> <Translate contentKey="global.menu.admin.apidocs">API</Translate>
  </NavLink>
);

const databaseItem = (
  <NavLink tag="a" href="./h2-console" target="_tab">
    <FontAwesomeIcon icon="hdd" /> <Translate contentKey="global.menu.admin.database">Database</Translate>
  </NavLink>
);

export const AdminMenu = ({ showSwagger, showDatabase }) => (
  <>
    <p style={{ marginTop: '20px', marginBottom: '-2px' }}>{translate('global.menu.admin.main')}</p>
    {adminMenuItems}
    {showSwagger && swaggerItem}
    {showDatabase && databaseItem}
  </>
);
