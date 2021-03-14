import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ITema, Tema } from 'app/shared/model/tema.model';
import { TemaService } from './tema.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUsuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from 'app/entities/usuario/usuario.service';

@Component({
  selector: 'jhi-tema-update',
  templateUrl: './tema-update.component.html',
})
export class TemaUpdateComponent implements OnInit {
  isSaving = false;
  usuarios: IUsuario[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    description: [null, [Validators.required, Validators.minLength(10), Validators.maxLength(200)]],
    category: [null, [Validators.required]],
    photo: [],
    photoContentType: [],
    usuario: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected temaService: TemaService,
    protected usuarioService: UsuarioService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tema }) => {
      this.updateForm(tema);

      this.usuarioService.query().subscribe((res: HttpResponse<IUsuario[]>) => (this.usuarios = res.body || []));
    });
  }

  updateForm(tema: ITema): void {
    this.editForm.patchValue({
      id: tema.id,
      title: tema.title,
      description: tema.description,
      category: tema.category,
      photo: tema.photo,
      photoContentType: tema.photoContentType,
      usuario: tema.usuario,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('appopinionesApp.error', { message: err.message })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tema = this.createFromForm();
    if (tema.id !== undefined) {
      this.subscribeToSaveResponse(this.temaService.update(tema));
    } else {
      this.subscribeToSaveResponse(this.temaService.create(tema));
    }
  }

  private createFromForm(): ITema {
    return {
      ...new Tema(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      category: this.editForm.get(['category'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITema>>): void {
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

  trackById(index: number, item: IUsuario): any {
    return item.id;
  }
}
