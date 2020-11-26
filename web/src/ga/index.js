import ReactGA from 'react-ga';

class GAHelper {
  constructor(production) {
    const TrackingCode = production ? 'UA-153780028-2' : 'UA-153780028-3';
    ReactGA.initialize(TrackingCode);
  }

  pageView = page => {
    ReactGA.pageview(page ? page : window.location.href);
  };

  modalView = modal => {
    ReactGA.modalview(modal);
  };

  event = (category, action) => {
    ReactGA.event({
      category,
      action,
    });
  };

  timing = (categoryName, variableName, valueNum) => {
    ReactGA.timing({
      category: categoryName,
      variable: variableName,
      value: valueNum,
    });
  };

  exception = detail => {
    ReactGA.exception({
      description: detail,
    });
  };
}

const GAHelperInstance = new GAHelper(process.env.NODE_ENV === 'production');
Object.freeze(GAHelperInstance);

export default GAHelperInstance;
