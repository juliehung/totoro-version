import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { INhiMonthDeclaration } from 'app/shared/model/nhi-month-declaration.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './nhi-month-declaration.reducer';

export interface INhiMonthDeclarationDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NhiMonthDeclarationDeleteDialog extends React.Component<INhiMonthDeclarationDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.nhiMonthDeclarationEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { nhiMonthDeclarationEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="totoroApp.nhiMonthDeclaration.delete.question">
          <Translate contentKey="totoroApp.nhiMonthDeclaration.delete.question" interpolate={{ id: nhiMonthDeclarationEntity.id }}>
            Are you sure you want to delete this NhiMonthDeclaration?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-nhiMonthDeclaration" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ nhiMonthDeclaration }: IRootState) => ({
  nhiMonthDeclarationEntity: nhiMonthDeclaration.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NhiMonthDeclarationDeleteDialog);
