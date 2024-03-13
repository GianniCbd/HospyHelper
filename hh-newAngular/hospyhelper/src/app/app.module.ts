import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login/login.component';
import { RegisterComponent } from './auth/register/register/register.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { Route, RouterModule } from '@angular/router';

import { TokenInterceptor } from './auth/token.interceptor';
import { IntroPageComponent } from './components/intro-page/intro-page.component';
import { ProfiloComponent } from './components/profiloUser/profilo/profilo.component';
import { NostriServiziComponent } from './components/nostri-servizi/nostri-servizi.component';
import { UserDetailsComponent } from './components/profiloUser/user-details/user-details.component';
import { HomePostComponent } from './components/childPages/homePost/home-post/home-post.component';

const routes: Route[] = [
  {
    path: '',
    component: IntroPageComponent,
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: 'profilo',
    component: ProfiloComponent,
  },
  { path: 'profilo/:id', component: UserDetailsComponent },
  {
    path: 'servizi',
    component: NostriServiziComponent,
  },
  {
    path: '**',
    redirectTo: '',
  },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
    HomeComponent,
    IntroPageComponent,
    ProfiloComponent,
    NostriServiziComponent,
    UserDetailsComponent,
    HomePostComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
  ],
  providers: [
    {
      useClass: TokenInterceptor,
      provide: HTTP_INTERCEPTORS,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
