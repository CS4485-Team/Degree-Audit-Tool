import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { HotTableModule } from '@handsontable/angular';
import { registerAllModules } from 'handsontable/registry';

import { PdfViewerModule } from 'ng2-pdf-viewer'; 

import { AuditComponent } from './audit-page/auditPage.component';
import { HomePageComponent } from './home-page/homePage.component';
import { AuditEditorComponent } from './audit-editor/auditEditor.component';
import { CoursePickerComponent } from './course-picker/coursePicker.component';
import { PDFViewerComponent } from './pdf-viewer/pdfViewer.component';

registerAllModules();

@NgModule({
  declarations: [
    AppComponent,
    AuditComponent,
    HomePageComponent,
    AuditEditorComponent,
    CoursePickerComponent,
    PDFViewerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HotTableModule,
    FormsModule,
    MatCheckboxModule,
    PdfViewerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
