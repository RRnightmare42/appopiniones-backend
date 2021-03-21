import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMensaje, Mensaje } from 'app/shared/model/mensaje.model';
import { MensajeService } from './mensaje.service';
import { MensajeComponent } from './mensaje.component';
import { MensajeDetailComponent } from './mensaje-detail.component';
import { MensajeUpdateComponent } from './mensaje-update.component';

@Injectable({ providedIn: 'root' })
export class MensajeResolve implements Resolve<IMensaje> {
  constructor(private service: MensajeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMensaje> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mensaje: HttpResponse<Mensaje>) => {
          if (mensaje.body) {
            return of(mensaje.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Mensaje());
  }
}

export const mensajeRoute: Routes = [
  {
    path: '',
    component: MensajeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Mensajes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MensajeDetailComponent,
    resolve: {
      mensaje: MensajeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mensajes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MensajeUpdateComponent,
    resolve: {
      mensaje: MensajeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mensajes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MensajeUpdateComponent,
    resolve: {
      mensaje: MensajeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Mensajes',
    },
    canActivate: [UserRouteAccessService],
  },
];
