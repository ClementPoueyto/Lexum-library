import { Routes } from '@angular/router';
import { BookListComponent } from './components/book-list/book-list';
import {BookFormComponent} from './components/book-form/book-form';
import {BookDetailsComponent} from './components/book-details/book-details';
import {BookResolver} from './resolvers/book.resolver';


export const routes: Routes = [
  { path: '', component: BookListComponent},
  { path: 'books/new', component: BookFormComponent },
  { path: 'books/:id', component: BookDetailsComponent , resolve: { book: BookResolver } },
  { path: 'books/:id/edit', component: BookFormComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' },

];
