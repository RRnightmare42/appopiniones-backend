import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'usuario',
        loadChildren: () => import('./usuario/usuario.module').then(m => m.AppopinionesUsuarioModule),
      },
      {
        path: 'tema',
        loadChildren: () => import('./tema/tema.module').then(m => m.AppopinionesTemaModule),
      },
      {
        path: 'mensaje',
        loadChildren: () => import('./mensaje/mensaje.module').then(m => m.AppopinionesMensajeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AppopinionesEntityModule {}
