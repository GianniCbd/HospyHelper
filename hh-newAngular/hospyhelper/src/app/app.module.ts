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
import { RoomComponent } from './components/childPages/room/room.component';
import { RoomTypeComponent } from './components/childPages/roomType/room-type/room-type.component';
import { AccommodationComponent } from './components/accommodation/accommodation.component';
import { BookingComponent } from './components/childPages/booking/booking.component';
import { EmployeeComponent } from './components/childPages/employee/employee.component';
import { OpexComponent } from './components/childPages/opex/opex.component';
import { PostComponent } from './components/post/post.component';

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
    path: 'accommodation',
    component: AccommodationComponent,
  },
  {
    path: 'booking',
    component: BookingComponent,
  },
  {
    path: 'room',
    component: RoomComponent,
  },
  {
    path: 'roomType',
    component: RoomTypeComponent,
  },
  {
    path: 'employee',
    component: EmployeeComponent,
  },
  {
    path: 'opex',
    component: OpexComponent,
  },
  {
    path: 'post',
    component: PostComponent,
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

    RoomComponent,
    RoomTypeComponent,
    AccommodationComponent,
    BookingComponent,
    EmployeeComponent,
    OpexComponent,
    PostComponent,
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
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
