import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {OAuthService} from 'angular-oauth2-oidc';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  authorizationSubject: BehaviorSubject<boolean>;
  authorizationObservable: Observable<boolean>;

  constructor(
    private oAuthService: OAuthService,
    private router: Router
  ) {
    this.authorizationSubject = new BehaviorSubject<boolean>(false);
    this.authorizationObservable = this.authorizationSubject.asObservable();
    this.oAuthService.redirectUri = `${environment.config.oauth.redirectUri}`;
    this.oAuthService.issuer = `${environment.config.oauth.issuer}`;
    this.oAuthService.clientId = `${environment.config.oauth.clientId}`;
    this.oAuthService.scope = `${environment.config.oauth.scope}`;
    this.oAuthService.oidc = true;
    this.oAuthService.sessionChecksEnabled = true;
    this.oAuthService.useSilentRefresh = true;
    this.oAuthService.silentRefreshTimeout = 1000;
    this.oAuthService.timeoutFactor = 0.25;
    this.oAuthService.silentRefreshRedirectUri = `${environment.config.oauth.silentRefreshUri}`;
    this.oAuthService.loadDiscoveryDocument(`${environment.config.oauth.discoveryDocumentUrl}`)
      .then(() => this.maintain())
      .catch(() => this.login(undefined));
    this.oAuthService.setupAutomaticSilentRefresh();
  }

  public maintain(): Promise<void> {
    const val = this.oAuthService.hasValidAccessToken();
    return this.oAuthService.tryLogin({})
      .then(() => {
        if (!this.oAuthService.hasValidAccessToken()) {
          this.authorizationSubject.next(false);
          return this.oAuthService.silentRefresh()
            .then(() => {
              this.authorizationSubject.next(true);
              return Promise.resolve();
            });
        } else {
          this.authorizationSubject.next(true);
          return Promise.resolve();
        }
      });
  }

  public logout(): void {
    this.oAuthService.logOut();
    this.oAuthService.logOut();
  }

  public tryLogin(): Promise<boolean> {
    return this.oAuthService.tryLogin();
  }

  public login<T>(value: T): Promise<T> {
    this.oAuthService.initImplicitFlow();
    return Promise.resolve(value);
  }

  public get getAuthorization(): Observable<boolean>{
    return this.authorizationObservable;
  }

  public get accessToken(): string {
    return this.oAuthService.getAccessToken();
  }

  public get fullName(): string {
    const claims: any = this.oAuthService.getIdentityClaims();
    return claims.name;
  }
}
