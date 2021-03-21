import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AppopinionesSharedModule } from 'app/shared/shared.module';
import { AppopinionesCoreModule } from 'app/core/core.module';
import { AppopinionesAppRoutingModule } from './app-routing.module';
import { AppopinionesHomeModule } from './home/home.module';
import { AppopinionesEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AppopinionesSharedModule,
    AppopinionesCoreModule,
    AppopinionesHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AppopinionesEntityModule,
    AppopinionesAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class AppopinionesAppModule {}
