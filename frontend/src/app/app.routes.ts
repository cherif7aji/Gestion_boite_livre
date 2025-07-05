import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { BoiteListComponent } from './components/boite-list/boite-list.component';
import { AddBoiteComponent } from './components/boite-add/boite-add.component';
import { BoiteEditComponent } from './components/boite-edit/boite-edit.component';
import { AuthGuard } from './guards/auth.guard';
import { MapComponent } from './components/map/map.component';
import { BoiteDetailComponent } from './components/boite-detail/boite-detail.component';
import { UserHomeComponent } from './components/user-home/user-home.component';
import { UserListComponent } from './components/user-list/user-list.component';


export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'add', component: AddBoiteComponent, canActivate: [AuthGuard] },
    { path: 'listBoites', component: BoiteListComponent, canActivate: [AuthGuard] },
    { path: 'userHome', component: UserHomeComponent, canActivate: [AuthGuard] },
    { path: 'editBoite/:id', component: BoiteEditComponent, canActivate: [AuthGuard] },
    { path: '', redirectTo: '/login', pathMatch: 'full' }, 
    //{ path: '**', redirectTo: '/login' },
    { path: 'map', component: MapComponent, canActivate: [AuthGuard] },
    { path: 'boite-detail/:id', component: BoiteDetailComponent, canActivate: [AuthGuard]  },
    { path: 'listusers', component: UserListComponent, canActivate: [AuthGuard] },
];
