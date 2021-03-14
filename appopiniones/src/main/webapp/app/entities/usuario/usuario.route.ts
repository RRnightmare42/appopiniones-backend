import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsuario, Usuario } from 'app/shared/model/usuario.model';
import { UsuarioService } from './usuario.service';
import { UsuarioComponent } from './usuario.component';
import { UsuarioDetailComponent } from './usuario-detail.component';
import { UsuarioUpdateComponent } from './usuario-update.component';

@Injectable({ providedIn: 'root' })
export class UsuarioResolve implements Resolve<IUsuario> {
  constructor(private service: UsuarioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsuario> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usuario: HttpResponse<Usuario>) => {
          if (usuario.body) {
            return of(usuario.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Usuario());
  }
}

export const usuarioRoute: Routes = [
  {
    path: '',
    component: UsuarioComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Usuarios',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsuarioDetailComponent,
    resolve: {
      usuario: UsuarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Usuarios',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsuarioUpdateComponent,
    resolve: {
      usuario: UsuarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Usuarios',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsuarioUpdateComponent,
    resolve: {
      usuario: UsuarioResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Usuarios',
    },
    canActivate: [UserRouteAccessService],
  },
];
