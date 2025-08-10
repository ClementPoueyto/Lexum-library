import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable, EMPTY } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { BookService } from '../services/book.services';
import { Book } from '../models/book.model';

@Injectable({
  providedIn: 'root'
})
export class BookResolver implements Resolve<Book> {

  constructor(private bookService: BookService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Book> | Observable<never> {
    const id = route.paramMap.get('id');

    if (!id) {
      // Pas d'ID, on retourne un Observable vide et on redirige vers le menu
      this.router.navigate(['/']);
      return EMPTY;
    }

    return this.bookService.getBookById(+id).pipe(
      catchError(error => {
        // Erreur API, on retourne un Observable vide et on redirige vers le menu
        this.router.navigate(['/']);
        return EMPTY;
      })
    );
  }
}
