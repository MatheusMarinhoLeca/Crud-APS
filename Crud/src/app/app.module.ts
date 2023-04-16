import { EstoqueModule } from './estoque/estoque.module';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { AppMaterialModule } from './shared/app-material/app-material.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EstoqueComponent } from './estoque/containers/estoque/estoque.component';
import { SocialLoginModule, SocialAuthServiceConfig } from '@abacritt/angularx-social-login';
import {
  GoogleLoginProvider
} from '@abacritt/angularx-social-login';
import { VitrineListComponent } from './vitrine/components/vitrine-list/vitrine-list.component';
import { VitrineComponent } from './vitrine/containers/vitrine/vitrine.component';
import { VitrineModule } from './vitrine/vitrine.module';
import { CarrinhoModule } from './carrinho/carrinho.module';
import { ComprasModule } from './compras/compras.module';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AppMaterialModule,
    HttpClientModule,
    EstoqueModule,
    VitrineModule,
    CarrinhoModule,
    ComprasModule,
    SocialLoginModule

  ],
  providers: [
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '523473306-7qb6rb134f61ohc222tlrkgeqiif99si.apps.googleusercontent.com'
            )
          }
        ],
        onError: (err) => {
          console.error(err);
        }
      } as SocialAuthServiceConfig,
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
