import './footer.css';

import React from 'react';
import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>
          Made with <span className="heart">&hearts;</span> in Taiwan
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
