import { EstoqueModule } from './estoque/estoque.module';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AppMaterialModule } from './shared/app-material/app-material.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EstoqueComponent } from './estoque/containers/estoque/estoque.component';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AppMaterialModule,
    HttpClientModule,
    EstoqueModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
