import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMensaje } from 'app/shared/model/mensaje.model';

@Component({
  selector: 'jhi-mensaje-detail',
  templateUrl: './mensaje-detail.component.html',
})
export class MensajeDetailComponent implements OnInit {
  mensaje: IMensaje | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensaje }) => (this.mensaje = mensaje));
  }

  previousState(): void {
    window.history.back();
  }
}
