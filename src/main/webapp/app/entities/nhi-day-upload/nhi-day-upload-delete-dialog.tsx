import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { INhiDayUpload } from 'app/shared/model/nhi-day-upload.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './nhi-day-upload.reducer';

export interface INhiDayUploadDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiDayUploadDeleteDialog extends React.Component<INhiDayUploadDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.nhiDayUploadEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { nhiDayUploadEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="totoroApp.nhiDayUpload.delete.question">
          <Translate contentKey="totoroApp.nhiDayUpload.delete.question" interpolate={{ id: nhiDayUploadEntity.id }}>
            Are you sure you want to delete this NhiDayUpload?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-nhiDayUpload" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ nhiDayUpload }: IRootState) => ({
  nhiDayUploadEntity: nhiDayUpload.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiDayUploadDeleteDialog);
