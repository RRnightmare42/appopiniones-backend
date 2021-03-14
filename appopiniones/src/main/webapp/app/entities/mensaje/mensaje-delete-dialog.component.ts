import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMensaje } from 'app/shared/model/mensaje.model';
import { MensajeService } from './mensaje.service';

@Component({
  templateUrl: './mensaje-delete-dialog.component.html',
})
export class MensajeDeleteDialogComponent {
  mensaje?: IMensaje;

  constructor(protected mensajeService: MensajeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mensajeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mensajeListModification');
      this.activeModal.close();
    });
  }
}
