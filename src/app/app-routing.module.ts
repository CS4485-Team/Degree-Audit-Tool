import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DegreePlanPageComponent } from './degreePlan-page/degreePlanPage.component';
import { HomePageComponent } from './home-page/homePage.component';
import { PDFViewerComponent } from './pdf-viewer/pdfViewer.component';


const routes: Routes = [
  { path: '', redirectTo: '/home-page', pathMatch: 'full' },
  { path: 'home-page', component: HomePageComponent },
  { path: 'degreePlan',  component: DegreePlanPageComponent },
  { path: 'viewPdf', component: PDFViewerComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
