import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { INhiAccumulatedMedicalRecord } from 'app/shared/model/nhi-accumulated-medical-record.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './nhi-accumulated-medical-record.reducer';

export interface INhiAccumulatedMedicalRecordDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiAccumulatedMedicalRecordDeleteDialog extends React.Component<INhiAccumulatedMedicalRecordDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.nhiAccumulatedMedicalRecordEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { nhiAccumulatedMedicalRecordEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="totoroApp.nhiAccumulatedMedicalRecord.delete.question">
          <Translate
            contentKey="totoroApp.nhiAccumulatedMedicalRecord.delete.question"
            interpolate={{ id: nhiAccumulatedMedicalRecordEntity.id }}
          >
            Are you sure you want to delete this NhiAccumulatedMedicalRecord?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-nhiAccumulatedMedicalRecord" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ nhiAccumulatedMedicalRecord }: IRootState) => ({
  nhiAccumulatedMedicalRecordEntity: nhiAccumulatedMedicalRecord.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiAccumulatedMedicalRecordDeleteDialog);
