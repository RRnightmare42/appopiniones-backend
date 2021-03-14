import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppopinionesSharedModule } from 'app/shared/shared.module';
import { MensajeComponent } from './mensaje.component';
import { MensajeDetailComponent } from './mensaje-detail.component';
import { MensajeUpdateComponent } from './mensaje-update.component';
import { MensajeDeleteDialogComponent } from './mensaje-delete-dialog.component';
import { mensajeRoute } from './mensaje.route';

@NgModule({
  imports: [AppopinionesSharedModule, RouterModule.forChild(mensajeRoute)],
  declarations: [MensajeComponent, MensajeDetailComponent, MensajeUpdateComponent, MensajeDeleteDialogComponent],
  entryComponents: [MensajeDeleteDialogComponent],
})
export class AppopinionesMensajeModule {}
