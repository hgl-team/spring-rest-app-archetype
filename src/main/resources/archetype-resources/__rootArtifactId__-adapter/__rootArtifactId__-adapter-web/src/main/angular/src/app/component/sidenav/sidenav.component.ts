import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthorizationService} from '../../service/authorization/authorization.service';
import {MenuService} from '../../service/menu/menu.service';
import {Subscription} from 'rxjs';
import {faArrowLeft, faArrowRight} from '@fortawesome/free-solid-svg-icons';
import {Menu} from '../../model/menu';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit, OnDestroy {
  private subscripcionLogin: Subscription;
  menus: Menu[];
  hide: boolean;

  constructor(
    private authorizationService: AuthorizationService,
    private menuService: MenuService
  ) {
    this.subscripcionLogin = authorizationService.getAuthorization.subscribe(value => this.onLoginEvent(value));
    this.hide = false;
  }

  ngOnDestroy(): void {
    this.subscripcionLogin.unsubscribe();
  }

  ngOnInit(): void { }

  onLoginEvent(loggedIn: boolean): void {
    if (loggedIn) {
      this.menuService.getMenus().subscribe(value => {
        this.menus = value;
      });
    }
  }

  getMenuId(nombre: string): string {
    return `${nombre.replace(/\s/g, '').toLowerCase()}`;
  }

  toggleSidenav(): void {
    this.hide = !this.hide;
  }

  get sidenavStatusClass(): string {
    return (this.hide) ? 'sidenav-container-hide' : '';
  }
}
