import {Component, OnDestroy} from '@angular/core';
import {Router} from '@angular/router';
import {AuthorizationService} from './service/authorization/authorization.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnDestroy {
  title = 'angular';
  user = false;
  private authSubscription: Subscription;

  constructor(
    private router: Router,
    private authorizationService: AuthorizationService,
  ) {
    this.authSubscription = authorizationService.getAuthorization.subscribe(value => {
      this.onLoginEvent(value);
    });
  }

  ngOnDestroy(): void {
    this.authSubscription.unsubscribe();
  }

  logout(): void {
    this.authorizationService.logout();
  }

  private onLoginEvent(loggedIn: boolean): void {
    if (loggedIn) {
      console.log('Logged in');
    } else {
      console.log('Logged out');
    }
    this.user = loggedIn;
  }
}
