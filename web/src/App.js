import React, { useEffect } from 'react';
import { createStore, applyMiddleware, compose } from 'redux';
import { Provider } from 'react-redux';
import Home from './container/Home';
import rootReducer from './rootReducers';
import createSagaMiddleware from 'redux-saga';
import { rootSaga } from './rootSaga';
import { HashRouter } from 'react-router-dom';
import { CookiesProvider } from 'react-cookie';
import { HelmetProvider } from 'react-helmet-async';
import { initGA } from './ga';
import 'antd/dist/antd.css';

const sagaMiddleware = createSagaMiddleware();
const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(rootReducer, /* preloadedState, */ composeEnhancers(applyMiddleware(sagaMiddleware)));
sagaMiddleware.run(rootSaga);

function App() {
  useEffect(() => {
    initGA();
  }, []);

  return (
    <Provider store={store}>
      <HashRouter>
        <CookiesProvider>
          <HelmetProvider>
            <Home />
          </HelmetProvider>
        </CookiesProvider>
      </HashRouter>
    </Provider>
  );
}

export default App;
