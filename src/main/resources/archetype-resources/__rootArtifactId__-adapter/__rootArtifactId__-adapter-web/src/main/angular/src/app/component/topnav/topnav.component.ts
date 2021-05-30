import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {AuthorizationService} from '../../service/authorization/authorization.service';

@Component({
  selector: 'app-topnav',
  templateUrl: './topnav.component.html',
  styleUrls: ['./topnav.component.css']
})
export class TopnavComponent implements OnInit, OnDestroy {
  loginSubsctiption: Subscription;
  nombreUsuario: string;

  constructor(
    private authorizationService: AuthorizationService) {
    this.loginSubsctiption = authorizationService.getAuthorization.subscribe(value => this.onLogin(value));
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.loginSubsctiption.unsubscribe();
  }

  logout(): void {
    this.authorizationService.logout();
  }

  onLogin(loggedIn: boolean): void {
    if (loggedIn) {
      this.nombreUsuario = this.authorizationService.fullName;
    }
  }

}
