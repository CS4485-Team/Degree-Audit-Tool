import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { HotTableModule } from '@handsontable/angular';
import { registerAllModules } from 'handsontable/registry';

import { PdfViewerModule } from 'ng2-pdf-viewer'; 

import { DegreePlanPageComponent } from './degreePlan-page/degreePlanPage.component';
import { HomePageComponent } from './home-page/homePage.component';
import { DegreePlanEditorComponent } from './degreePlan-editor/degreePlanEditor.component';
import { PDFViewerComponent } from './pdf-viewer/pdfViewer.component';
import { StartSelectComponent } from './startSelect-page/startSelect.component';
import { EditConfigurationsComponent } from './editConfigurations-page/editConfigurations.component';

registerAllModules();

@NgModule({
  declarations: [
    AppComponent,
    DegreePlanPageComponent,
    HomePageComponent,
    DegreePlanEditorComponent,
    PDFViewerComponent,
    StartSelectComponent,
    EditConfigurationsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HotTableModule,
    FormsModule,
    PdfViewerModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
