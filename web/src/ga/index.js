import ReactGA from 'react-ga';

const isProduction = process.env.NODE_ENV === 'production';
const TrackingCode = 'UA-153780028-2';

export const initGA = () => {
  if (isProduction) {
    ReactGA.initialize(TrackingCode);
  }
};

export const GApageView = page => {
  if (isProduction) {
    ReactGA.pageview(page);
  }
};

export const GAmodalView = modal => {
  if (isProduction) {
    ReactGA.modalview(modal);
  }
};

export const GAevent = (category, action) => {
  if (isProduction) {
    ReactGA.event({
      category,
      action,
    });
  }
};

export const GAtiming = (categoryName, variableName, valueNum) => {
  if (isProduction) {
    ReactGA.timing({
      category: categoryName,
      variable: variableName,
      value: valueNum,
    });
  }
};

export const GAexception = detail => {
  if (isProduction) {
    ReactGA.exception({
      description: detail,
    });
  }
};
