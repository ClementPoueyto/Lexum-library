import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookDetailsComponent } from './book-details';
import { Book } from '../../models/book.model';
import {registerLocaleData} from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import {HttpClientTestingModule} from '@angular/common/http/testing';

registerLocaleData(localeFr, 'fr-FR');

describe('BookDetailsComponent', () => {
  let component: BookDetailsComponent;
  let fixture: ComponentFixture<BookDetailsComponent>;

  const mockBook: Book = {
    id: 1,
    title: 'Le Petit Prince',
    authors: ['Antoine de Saint-Exupéry'],
    publicationDate: '1943-04-06',
    summary: 'Un conte poétique.',
    pages: 96,
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookDetailsComponent, HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({ book: mockBook })
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BookDetailsComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should set the book from the route resolver data', () => {
    fixture.detectChanges(); // déclenche ngOnInit et la souscription
    expect(component.book).toEqual(mockBook);
  });

  it('should render book details in the template', () => {
    fixture.detectChanges();

    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('mat-card-title')?.textContent).toContain(mockBook.title);
    expect(compiled.querySelector('mat-card-subtitle')?.textContent).toContain(mockBook.authors[0]);
    expect(compiled.querySelector('mat-card-content')?.textContent).toContain(mockBook.summary);
  });
});
