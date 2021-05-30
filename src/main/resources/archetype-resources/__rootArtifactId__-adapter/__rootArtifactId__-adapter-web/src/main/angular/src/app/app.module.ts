import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {RouterModule} from '@angular/router';
import {SidenavComponent} from './component/sidenav/sidenav.component';
import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {HomeComponent} from './component/home/home/home.component';
import {AppRoutingModule} from './app-routing.module';
import {OAuthModule, OAuthStorage} from 'angular-oauth2-oidc';
import {ReactiveFormsModule} from '@angular/forms';
import {DatePipe} from '@angular/common';
import {fas} from '@fortawesome/free-solid-svg-icons';
import {TopnavComponent} from './component/topnav/topnav.component';
import {HttpClientModule} from '@angular/common/http';
import {EmptyAsNullDirective} from './directive/empty-as-null.directive';
import {MatPaginatorModule} from '@angular/material/paginator';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatNativeDateModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {DisableControlDirective} from './directive/disable-control.directive';

@NgModule({
  declarations: [
    EmptyAsNullDirective,
    DisableControlDirective,
    AppComponent,
    SidenavComponent,
    TopnavComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    HttpClientModule,
    FontAwesomeModule,
    AppRoutingModule,
    OAuthModule.forRoot(),
    ReactiveFormsModule,
    MatPaginatorModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatAutocompleteModule
  ],
  providers: [
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas);
  }
}
