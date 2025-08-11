import {AfterViewInit, ChangeDetectorRef, Component, ViewChild} from '@angular/core';
import {MatPaginator, MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import { Book } from '../../models/book.model';
import { BookService } from '../../services/book.services';
import { BookListStateService } from '../../services/book-list-state.service';
import {Router, RouterModule} from '@angular/router';
import {CommonModule, DatePipe} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatIconModule} from '@angular/material/icon';
import {ClickableCellDirective} from '../../directives/clickable-cell.directive';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.html',
  styleUrls: ['./book-list.scss'],
  imports: [
  CommonModule,
  FormsModule,
  DatePipe,
  CommonModule,
  FormsModule,
  MatTableModule,
  MatPaginatorModule,
  MatFormFieldModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatIconModule,
  ClickableCellDirective,
  RouterModule,
  MatButton
],
})
export class BookListComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  displayedColumns: string[] = ['title', 'authors', 'publicationDate', 'pages', 'actions'];

  dataSource = new MatTableDataSource<Book>([]);
  totalElements = 0;
  loading = false;
  searchTerm = '';
  pageIndex = 0;
  pageSize = 10;

  constructor(
    private bookService: BookService,
    private paginatorStateService: BookListStateService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngAfterViewInit() {
    const state = this.paginatorStateService.getState();

    this.paginator.pageIndex = state.pageIndex ?? 0;
    this.paginator.pageSize = state.pageSize ?? 10;
    this.paginator.length = state.totalElements ?? 0;
    this.searchTerm = state.searchTerm;
    this.loadBooks(this.paginator.pageIndex, this.paginator.pageSize);

    this.paginator.page.subscribe(event => {
      this.paginatorStateService.setState({
        pageIndex: event.pageIndex,
        pageSize: event.pageSize,
        totalElements: this.paginator.length
      });

      this.loadBooks(event.pageIndex, event.pageSize);
    });
  }

  loadBooks(pageIndex: number, pageSize: number) {
    this.loading = true;
    this.bookService.searchBooks(this.searchTerm, pageIndex, pageSize).subscribe(data => {
      this.dataSource.data = data.content; // données de la page
      this.totalElements = data.totalElements; // total éléments dans toute la collection (ex: 23)
      this.paginator.length = this.totalElements;
      this.loading = false;
    });
  }


  onSearchChange() {
    this.paginator.pageIndex = 0;
    this.pageIndex = 0;
    this.paginatorStateService.setState({
      searchTerm: this.searchTerm,
    })
    this.loadBooks(this.pageIndex, this.pageSize);

  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadBooks(this.pageIndex, this.pageSize);
  }

  viewBook(book: Book) {
    console.log('Consultation du livre', book);
    this.router.navigate(['/books', book.id]);
  }

  editBook(book: Book) {
    console.log('Modification du livre', book);
    this.router.navigate(['/books', book.id, 'edit']);
  }

  deleteBook(book: Book) {
    if (confirm(`Supprimer le livre "${book.title}" ?`)) {
      console.log('Suppression du livre', book);
      this.bookService.deleteBook(Number(book.id)).subscribe(() => this.loadBooks(this.pageIndex, this.pageSize));
    }
  }
}
