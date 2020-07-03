import React from 'react';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { nextPage, prevPage } from './actions';
import { CSSTransition } from 'react-transition-group';
import { Helmet } from 'react-helmet-async';
import PageControll from './PageControll';
import pages from './pages';

//#region
const Container = styled.div`
  max-width: 700px;
  width: 95%;
  height: 600px;
  max-height: 90%;
  position: relative;
`;

//#endregion

const timeout = 500;

function QutContent(props) {
  const { currentPage } = props;
  const classNames = props.reverse ? 'item-reverse' : 'item';
  return (
    <Container>
      <Helmet>
        <title>填寫初診單</title>
      </Helmet>
      {pages.map(page => (
        <CSSTransition
          timeout={timeout}
          classNames={classNames}
          key={page.key}
          in={currentPage === page.page}
          unmountOnExit
        >
          {page.component}
        </CSSTransition>
      ))}
      <PageControll />
    </Container>
  );
}

const mapStateToProps = ({ questionnairePageReducer }) => ({
  currentPage: questionnairePageReducer.flow.page,
  reverse: questionnairePageReducer.flow.reverse,
});

const mapDispatchToProps = { nextPage, prevPage };

export default connect(mapStateToProps, mapDispatchToProps)(QutContent);
