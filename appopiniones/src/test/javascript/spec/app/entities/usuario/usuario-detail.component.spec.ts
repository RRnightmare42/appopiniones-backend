import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { AppopinionesTestModule } from '../../../test.module';
import { UsuarioDetailComponent } from 'app/entities/usuario/usuario-detail.component';
import { Usuario } from 'app/shared/model/usuario.model';

describe('Component Tests', () => {
  describe('Usuario Management Detail Component', () => {
    let comp: UsuarioDetailComponent;
    let fixture: ComponentFixture<UsuarioDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ usuario: new Usuario(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppopinionesTestModule],
        declarations: [UsuarioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UsuarioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuarioDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load usuario on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usuario).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
