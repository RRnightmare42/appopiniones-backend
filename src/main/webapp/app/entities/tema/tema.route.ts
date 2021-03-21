import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITema, Tema } from 'app/shared/model/tema.model';
import { TemaService } from './tema.service';
import { TemaComponent } from './tema.component';
import { TemaDetailComponent } from './tema-detail.component';
import { TemaUpdateComponent } from './tema-update.component';

@Injectable({ providedIn: 'root' })
export class TemaResolve implements Resolve<ITema> {
  constructor(private service: TemaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITema> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tema: HttpResponse<Tema>) => {
          if (tema.body) {
            return of(tema.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tema());
  }
}

export const temaRoute: Routes = [
  {
    path: '',
    component: TemaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Temas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TemaDetailComponent,
    resolve: {
      tema: TemaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Temas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TemaUpdateComponent,
    resolve: {
      tema: TemaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Temas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TemaUpdateComponent,
    resolve: {
      tema: TemaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Temas',
    },
    canActivate: [UserRouteAccessService],
  },
];
