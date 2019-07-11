import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { INhiMonthDeclarationDetails } from 'app/shared/model/nhi-month-declaration-details.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './nhi-month-declaration-details.reducer';

export interface INhiMonthDeclarationDetailsDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiMonthDeclarationDetailsDeleteDialog extends React.Component<INhiMonthDeclarationDetailsDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.nhiMonthDeclarationDetailsEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { nhiMonthDeclarationDetailsEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="totoroApp.nhiMonthDeclarationDetails.delete.question">
          <Translate
            contentKey="totoroApp.nhiMonthDeclarationDetails.delete.question"
            interpolate={{ id: nhiMonthDeclarationDetailsEntity.id }}
          >
            Are you sure you want to delete this NhiMonthDeclarationDetails?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-nhiMonthDeclarationDetails" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ nhiMonthDeclarationDetails }: IRootState) => ({
  nhiMonthDeclarationDetailsEntity: nhiMonthDeclarationDetails.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiMonthDeclarationDetailsDeleteDialog);
