import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from '../../models/book.model';
import {BookService} from '../../services/book.services';
import {FormsModule} from '@angular/forms';
import {Page} from '../../models/page.model';
import {CommonModule, DatePipe} from '@angular/common';
import {Router, RouterModule} from '@angular/router';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatPaginator, MatPaginatorModule} from '@angular/material/paginator';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatIconModule} from '@angular/material/icon';
import {ClickableCellDirective} from '../../directives/clickable-cell.directive';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-book-list',
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
  templateUrl: './book-list.html',
  styleUrl: './book-list.scss'
})
export class BookListComponent implements OnInit {
  page: Page<Book> | null = null;
  pageSize = 20;
  searchTerm = '';
  loading = false;

  displayedColumns: string[] = ['title', 'authors', 'publicationDate', 'pages', 'actions'];
  dataSource = new MatTableDataSource<Book>([]);
  totalElements = 0;


  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private bookService: BookService, private router: Router
  ) {}

  ngOnInit() {
    this.loadBooks();
  }

  loadBooks(pageIndex: number = 0, pageSize: number = 10) {
    this.loading = true;
    this.bookService.searchBooks(this.searchTerm, pageIndex, pageSize).subscribe(data => {
      this.dataSource.data = data.content;
      this.totalElements = data.totalElements;
      this.loading = false;
      console.log(this.dataSource.data);
    });
  }

  onSearchChange() {
    this.paginator.pageIndex = 0;
    this.loadBooks();
  }

  onPageChange(event: any) {
    this.loadBooks(event.pageIndex, event.pageSize);
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
      this.bookService.deleteBook(Number(book.id)).subscribe(() => this.loadBooks());
    }
  }
}
