import moment from 'moment';

export default function convertPatientImagesToImageGallery(patientImages, currentIndex) {
  if (!patientImages) return undefined;

  const findCurrentImageDate = patientImages.filter((data, index) => currentIndex === index);
  const currentImageDate =
    findCurrentImageDate.length > 0 ? `${moment(findCurrentImageDate[0]?.createdDate).format('YYYY/MM/DD HH:mm')}` : '';

  return {
    currentImageDate,
    patientImages: patientImages.map(images => {
      return {
        ...images,
        original: images.url,
        thumbnail: images.url,
      };
    }),
  };
}
