import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';

import { BookFormComponent } from './book-form';
import {ActivatedRoute, provideRouter, Router} from '@angular/router';
import {Book} from '../../models/book.model';
import {of} from 'rxjs';
import {BookService} from '../../services/book.services';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';

describe('BookForm', () => {

  const mockBook: Book = {
    id: 1,
    title: 'Le Petit Prince',
    authors: ['Antoine de Saint-Exupéry'],
    publicationDate: '1943-04-06',
    summary: 'Un conte poétique.',
    pages: 96,
  };
  let component: BookFormComponent;
  let fixture: ComponentFixture<BookFormComponent>;
  let bookServiceSpy: jasmine.SpyObj<BookService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    bookServiceSpy = jasmine.createSpyObj('BookService', ['getBookById', 'addBook', 'updateBook']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [
        BookFormComponent,
        NoopAnimationsModule
      ],
      providers: [
        { provide: BookService, useValue: bookServiceSpy },
        { provide: Router, useValue: routerSpy },
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: { paramMap: new Map() }, // mode création par défaut
            paramMap: of(new Map())
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BookFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the form with default values', () => {
    expect(component.bookForm).toBeTruthy();
    expect(component.bookForm.get('title')?.value).toBe('');
    expect(component.authors.length).toBe(1);
  });

  it('should add an author when addAuthor() is called', () => {
    const initialLength = component.authors.length;
    component.addAuthor();
    expect(component.authors.length).toBe(initialLength + 1);
  });

  it('should remove an author when removeAuthor() is called', () => {
    component.addAuthor();
    const initialLength = component.authors.length;
    component.removeAuthor(0);
    expect(component.authors.length).toBe(initialLength - 1);
  });

  it('should mark form as touched and not submit if invalid', () => {
    component.onSubmit();
    expect(component.bookForm.touched).toBeTrue();
    expect(bookServiceSpy.addBook).not.toHaveBeenCalled();
    expect(bookServiceSpy.updateBook).not.toHaveBeenCalled();
  });

  it('should call addBook in creation mode and navigate afterwards', fakeAsync(() => {
    component.bookForm.setValue({
      title: 'Test Book',
      authors: ['Author 1'],
      publicationDate: new Date(),
      summary: 'A summary',
      pages: 100
    });

    bookServiceSpy.addBook.and.returnValue(of(mockBook));
    component.onSubmit();
    tick();

    expect(bookServiceSpy.addBook).toHaveBeenCalled();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  }));

  it('should load book in edit mode and call updateBook on submit', fakeAsync(() => {
    // Simule un paramètre id
    (TestBed.inject(ActivatedRoute) as any).snapshot.paramMap = new Map([['id', '1']]);

    bookServiceSpy.getBookById.and.returnValue(of(mockBook));

    component.ngOnInit();
    tick();
    fixture.detectChanges();

    expect(component.isEdit).toBeTrue();
    expect(component.bookForm.valid).toBeTrue();

    bookServiceSpy.updateBook.and.returnValue(of(mockBook));
    component.onSubmit();
    tick();
    expect(bookServiceSpy.updateBook).toHaveBeenCalled();
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/']);
  }));
});
