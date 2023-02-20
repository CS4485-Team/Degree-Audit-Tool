import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HotTableModule } from '@handsontable/angular';
import { registerAllModules } from 'handsontable/registry';

import { AuditComponent } from './audit-page/auditPage.component';
import { HomePageComponent } from './home-page/homePage.component';
import { AuditEditorComponent } from './audit-editor/auditEditor.component';

registerAllModules();

@NgModule({
  declarations: [
    AppComponent,
    AuditComponent,
    HomePageComponent,
    AuditEditorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HotTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
