import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import React from 'react';
import { Translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';

import { DropdownItem, DropdownMenu, DropdownToggle, NavbarBrand, NavItem, NavLink, UncontrolledDropdown } from 'reactstrap';

export const NavDropdown = props => (
  <UncontrolledDropdown nav inNavbar id={props.id}>
    <DropdownToggle nav caret className="d-flex align-items-center">
      <FontAwesomeIcon icon={props.icon} />
      <span>{props.name}</span>
    </DropdownToggle>
    <DropdownMenu right style={props.style}>
      {props.children || <DropdownItem disabled>No Resource</DropdownItem>}
    </DropdownMenu>
  </UncontrolledDropdown>
);

export const Home = () => (
  <NavLink tag={Link} to="/" className="d-flex align-items-center">
    <FontAwesomeIcon icon="home" />
    <span>
      <Translate contentKey="global.menu.home">Home</Translate>
    </span>
  </NavLink>
);
