import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { AppopinionesTestModule } from '../../../test.module';
import { TemaDetailComponent } from 'app/entities/tema/tema-detail.component';
import { Tema } from 'app/shared/model/tema.model';

describe('Component Tests', () => {
  describe('Tema Management Detail Component', () => {
    let comp: TemaDetailComponent;
    let fixture: ComponentFixture<TemaDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ tema: new Tema(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppopinionesTestModule],
        declarations: [TemaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TemaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TemaDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load tema on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tema).toEqual(jasmine.objectContaining({ id: 123 }));
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
