import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VitrineComponent } from './containers/vitrine/vitrine.component';

const routes: Routes = [
  {path:'vitrine',component: VitrineComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VitrineRoutingModule { }
