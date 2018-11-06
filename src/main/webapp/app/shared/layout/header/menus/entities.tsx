import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/entity/patient">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.patient" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/appointment">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.appointment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/patient">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.patient" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/appointment">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.appointment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/appointment">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.appointment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/patient">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.patient" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/appointment">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.appointment" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/patient">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.patient" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/registration">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;
      <Translate contentKey="global.menu.entities.registration" />
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
