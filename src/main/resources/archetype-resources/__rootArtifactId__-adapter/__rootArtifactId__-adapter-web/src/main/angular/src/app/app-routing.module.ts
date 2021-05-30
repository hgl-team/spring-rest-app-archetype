import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './component/home/home/home.component';
import {AuthorizationGuard} from './authorization-guard';
const routes: Routes = [
  { path: '', component: HomeComponent , canActivate: [ AuthorizationGuard ] },
  { path: '**', redirectTo: '' }
];

export const AppRoutingModule = RouterModule.forRoot(routes);
