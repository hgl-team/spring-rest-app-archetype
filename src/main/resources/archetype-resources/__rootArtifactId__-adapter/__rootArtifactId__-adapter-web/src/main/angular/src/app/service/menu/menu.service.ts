import {Injectable, OnDestroy} from '@angular/core';
import {Observable, of, Subscription} from 'rxjs';
import {Menu} from '../../model/menu';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthorizationService} from '../authorization/authorization.service';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MenuService implements OnDestroy {
  private loginSubscription: Subscription;
  private invalidado: boolean;
  private usuario: string;
  private menu: Menu[];

  constructor(
    private httpClient: HttpClient,
    private authorizationService: AuthorizationService
  ) {
    this.invalidado = true;
    this.loginSubscription = authorizationService.getAuthorization.subscribe(value => { this.invalidado = true; });
  }

  ngOnDestroy(): void {
    this.loginSubscription.unsubscribe();
  }

  public getMenus(): Observable<Menu[]> {
    let obs: Observable<Menu[]>;

    if (!this.invalidado && this.usuario) {
      obs = of(this.menu);
    } else {
      const headers = new HttpHeaders({
        Authorization: `Bearer ${this.authorizationService.accessToken}`
      });
      obs = this.httpClient.get<Menu[]>(`${environment.config.menu.url}/api/menu`, {
        headers
      });
      obs.subscribe(value => {
        this.menu = value;
        this.invalidado = false;
      });
    }

    return obs;
  }
}
