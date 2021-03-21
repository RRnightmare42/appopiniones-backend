import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppopinionesSharedModule } from 'app/shared/shared.module';
import { TemaComponent } from './tema.component';
import { TemaDetailComponent } from './tema-detail.component';
import { TemaUpdateComponent } from './tema-update.component';
import { TemaDeleteDialogComponent } from './tema-delete-dialog.component';
import { temaRoute } from './tema.route';

@NgModule({
  imports: [AppopinionesSharedModule, RouterModule.forChild(temaRoute)],
  declarations: [TemaComponent, TemaDetailComponent, TemaUpdateComponent, TemaDeleteDialogComponent],
  entryComponents: [TemaDeleteDialogComponent],
})
export class AppopinionesTemaModule {}
