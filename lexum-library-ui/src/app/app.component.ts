import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, MatToolbarModule, MatButtonModule, MatIconModule],
  styleUrls: ['./app.scss'],
  template: `
    <mat-toolbar color="primary" class="mat-elevation-z4">
      <span class="toolbar-title">Catalogue de livres</span>
      <span class="spacer"></span>
      <nav>
        <a mat-button routerLink="/" routerLinkActive="active-link" [routerLinkActiveOptions]="{ exact: true }">
          <mat-icon>home</mat-icon> Accueil
        </a>
        <a mat-button routerLink="/books/new" routerLinkActive="active-link">
          <mat-icon>add</mat-icon> Ajouter un livre
        </a>
      </nav>
    </mat-toolbar>

    <main class="main-content">
      <router-outlet></router-outlet>
    </main>
  `
})
export class AppComponent {}
