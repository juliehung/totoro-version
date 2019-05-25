import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { INhiDayUploadDetails } from 'app/shared/model/nhi-day-upload-details.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './nhi-day-upload-details.reducer';

export interface INhiDayUploadDetailsDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiDayUploadDetailsDeleteDialog extends React.Component<INhiDayUploadDetailsDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.nhiDayUploadDetailsEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { nhiDayUploadDetailsEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="totoroApp.nhiDayUploadDetails.delete.question">
          <Translate contentKey="totoroApp.nhiDayUploadDetails.delete.question" interpolate={{ id: nhiDayUploadDetailsEntity.id }}>
            Are you sure you want to delete this NhiDayUploadDetails?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-nhiDayUploadDetails" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ nhiDayUploadDetails }: IRootState) => ({
  nhiDayUploadDetailsEntity: nhiDayUploadDetails.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiDayUploadDetailsDeleteDialog);
