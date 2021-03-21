import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITema } from 'app/shared/model/tema.model';
import { TemaService } from './tema.service';

@Component({
  templateUrl: './tema-delete-dialog.component.html',
})
export class TemaDeleteDialogComponent {
  tema?: ITema;

  constructor(protected temaService: TemaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.temaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('temaListModification');
      this.activeModal.close();
    });
  }
}
