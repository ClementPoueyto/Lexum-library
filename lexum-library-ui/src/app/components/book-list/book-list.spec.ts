import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { BookListComponent } from './book-list';
import { BookService } from '../../services/book.services';
import { Router } from '@angular/router';
import { of, Subject } from 'rxjs';
import { Page } from '../../models/page.model';
import { Book } from '../../models/book.model';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { BookListStateService } from '../../services/book-list-state.service';

describe('BookListComponent', () => {
  let component: BookListComponent;
  let fixture: ComponentFixture<BookListComponent>;
  let bookServiceSpy: jasmine.SpyObj<BookService>;
  let routerSpy: jasmine.SpyObj<Router>;
  let paginatorStateServiceSpy: jasmine.SpyObj<BookListStateService>;
  let paginatorPageSubject: Subject<PageEvent>;

  const mockBooks: Book[] = [
    { id: 1, title: 'Book 1', authors: ['Author 1'], publicationDate: '2024-01-01', pages: 100, summary: '' },
    { id: 2, title: 'Book 2', authors: ['Author 2'], publicationDate: '2024-02-01', pages: 200, summary: '' },
  ];
  const mockPage: Page<Book> = {
    content: mockBooks,
    totalElements: 2,
    totalPages: 1,
    size: 10,
    number: 0,
    pageable: { pageNumber: 0, paged: true, pageSize: 10, offset: 0, unpaged: false },
    last: true,
    first: true,
    numberOfElements: 2,
    empty: false
  };

  beforeEach(async () => {
    bookServiceSpy = jasmine.createSpyObj('BookService', ['searchBooks', 'deleteBook']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    paginatorStateServiceSpy = jasmine.createSpyObj('BookListStateService', ['getState', 'setState']);

    paginatorStateServiceSpy.getState.and.returnValue({
      pageIndex: 0,
      pageSize: 10,
      totalElements: 2,
      searchTerm: ''
    });

    paginatorPageSubject = new Subject<PageEvent>();

    await TestBed.configureTestingModule({
      imports: [
        BookListComponent,
        NoopAnimationsModule
      ],
      providers: [
        { provide: BookService, useValue: bookServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: BookListStateService, useValue: paginatorStateServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BookListComponent);
    component = fixture.componentInstance;

    component.paginator = {
      pageIndex: 0,
      pageSize: 10,
      length: 2,
      page: paginatorPageSubject.asObservable(),
      _subscribePage: paginatorPageSubject.subscribe.bind(paginatorPageSubject)
    } as unknown as MatPaginator;

    bookServiceSpy.searchBooks.and.returnValue(of(mockPage));
  });

  it('should load books on ngAfterViewInit', () => {
    component.ngAfterViewInit();

    expect(paginatorStateServiceSpy.getState).toHaveBeenCalled();
    expect(bookServiceSpy.searchBooks).toHaveBeenCalledWith('', 0, 10);
    expect(component.dataSource.data.length).toBe(2);
    expect(component.totalElements).toBe(2);
    expect(component.paginator.length).toBe(2);
  });

  it('should reload books on paginator page event', () => {
    component.ngAfterViewInit();

    const pageEvent: PageEvent = { pageIndex: 1, pageSize: 10, length: 2 };

    paginatorPageSubject.next(pageEvent);

    expect(paginatorStateServiceSpy.setState).toHaveBeenCalledWith({
      pageIndex: 1,
      pageSize: 10,
      totalElements: component.paginator.length
    });

    expect(bookServiceSpy.searchBooks).toHaveBeenCalledWith('', 1, 10);
  });

  it('should reload books on search change', () => {
    component.ngAfterViewInit();
    component.searchTerm = 'test';
    component.onSearchChange();

    expect(component.paginator.pageIndex).toBe(0);
    expect(bookServiceSpy.searchBooks).toHaveBeenCalledWith('test', 0, 10);
    expect(paginatorStateServiceSpy.setState).toHaveBeenCalledWith(jasmine.objectContaining({ searchTerm: 'test' }));
  });

  it('should load books on page change', () => {
    component.ngAfterViewInit();

    component.onPageChange({ pageIndex: 1, pageSize: 20, length: 20 });
    expect(bookServiceSpy.searchBooks).toHaveBeenCalledWith('', 1, 20);
  });

  it('should navigate to viewBook', () => {
    component.viewBook(mockBooks[0]);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/books', 1]);
  });

  it('should navigate to editBook', () => {
    component.editBook(mockBooks[0]);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/books', 1, 'edit']);
  });

  it('should delete a book after confirmation', fakeAsync(() => {
    spyOn(window, 'confirm').and.returnValue(true);
    bookServiceSpy.deleteBook.and.returnValue(of(void 0));

    component.deleteBook(mockBooks[0]);
    tick();

    expect(bookServiceSpy.deleteBook).toHaveBeenCalledWith(1);
    expect(bookServiceSpy.searchBooks).toHaveBeenCalled();
  }));

  it('should not delete a book if confirmation is cancelled', () => {
    spyOn(window, 'confirm').and.returnValue(false);
    component.deleteBook(mockBooks[0]);
    expect(bookServiceSpy.deleteBook).not.toHaveBeenCalled();
  });
});
