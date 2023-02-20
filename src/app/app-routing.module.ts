import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuditComponent } from './audit-page/auditPage.component';
import { HomePageComponent } from './home-page/homePage.component';


const routes: Routes = [
  { path: '', redirectTo: '/home-page', pathMatch: 'full' },
  { path: 'home-page', component: HomePageComponent },
  { path: 'audit',  component: AuditComponent },
  { path: 'home-page', redirectTo: '/home-page',  component: AuditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
