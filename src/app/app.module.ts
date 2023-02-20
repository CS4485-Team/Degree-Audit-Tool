import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { HomePageComponent } from './home-page/homePage.component';
import { AuditComponent } from './audit-page/auditPage.component';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    HomePageComponent,
    AppComponent,
    AuditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
