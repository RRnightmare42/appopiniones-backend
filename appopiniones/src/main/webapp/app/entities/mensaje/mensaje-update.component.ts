import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMensaje, Mensaje } from 'app/shared/model/mensaje.model';
import { MensajeService } from './mensaje.service';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario/usuario.service';
import { ITema } from 'app/shared/model/tema.model';
import { TemaService } from 'app/entities/tema/tema.service';

type SelectableEntity = IUsuario | ITema;

@Component({
  selector: 'jhi-mensaje-update',
  templateUrl: './mensaje-update.component.html',
})
export class MensajeUpdateComponent implements OnInit {
  isSaving = false;
  usuarios: IUsuario[] = [];
  temas: ITema[] = [];

  editForm = this.fb.group({
    id: [],
    body: [null, [Validators.required, Validators.maxLength(100)]],
    usuario: [],
    tema: [],
  });

  constructor(
    protected mensajeService: MensajeService,
    protected usuarioService: UsuarioService,
    protected temaService: TemaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensaje }) => {
      this.updateForm(mensaje);

      this.usuarioService.query().subscribe((res: HttpResponse<IUsuario[]>) => (this.usuarios = res.body || []));

      this.temaService.query().subscribe((res: HttpResponse<ITema[]>) => (this.temas = res.body || []));
    });
  }

  updateForm(mensaje: IMensaje): void {
    this.editForm.patchValue({
      id: mensaje.id,
      body: mensaje.body,
      usuario: mensaje.usuario,
      tema: mensaje.tema,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mensaje = this.createFromForm();
    if (mensaje.id !== undefined) {
      this.subscribeToSaveResponse(this.mensajeService.update(mensaje));
    } else {
      this.subscribeToSaveResponse(this.mensajeService.create(mensaje));
    }
  }

  private createFromForm(): IMensaje {
    return {
      ...new Mensaje(),
      id: this.editForm.get(['id'])!.value,
      body: this.editForm.get(['body'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
      tema: this.editForm.get(['tema'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMensaje>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
