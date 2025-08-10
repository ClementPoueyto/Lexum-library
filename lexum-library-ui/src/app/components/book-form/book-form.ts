import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators, FormArray, ReactiveFormsModule} from '@angular/forms';
import {ActivatedRoute, Router, RouterLink, RouterModule} from '@angular/router';
import { BookService } from '../../services/book.services';
import { MatInputModule} from '@angular/material/input';
import {
  MatDatepickerModule,
} from '@angular/material/datepicker';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {CommonModule} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {MAT_DATE_LOCALE, MatNativeDateModule} from '@angular/material/core';
import {MatCardModule} from '@angular/material/card';
@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.html',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    RouterModule
  ],
  providers: [ { provide: MAT_DATE_LOCALE, useValue: 'fr-FR' }
  ],
  styleUrls: ['./book-form.scss']
})
export class BookFormComponent implements OnInit {
  bookForm!: FormGroup;
  isEdit = false;
  bookId!: number;

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private route: ActivatedRoute,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.bookForm = this.fb.group({
      title: ['', Validators.required],
      authors: this.fb.array([this.fb.control('', Validators.required)]),
      publicationDate: ['', Validators.required],
      summary: ['', [Validators.required, Validators.minLength(1)]], // à augmenter si besoin
      pages: ['', [Validators.required, Validators.min(1)]]
    });

    const id = this.route?.snapshot?.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.bookId = Number(id);
      this.bookService.getBookById(this.bookId).subscribe(book => {
        this.bookForm.patchValue({
          title: book.title,
          publicationDate: book.publicationDate,
          summary: book.summary,
          pages: book.pages
        });
        this.authors.clear();
        book.authors.forEach(a =>
          this.authors.push(this.fb.control(a, Validators.required))
        );
      });
    }
  }

  get authors() {
    return this.bookForm.get('authors') as FormArray;
  }

  addAuthor() {
    this.authors.push(this.fb.control('', Validators.required));
  }

  removeAuthor(index: number) {
    this.authors.removeAt(index);
  }

  onSubmit() {
    if (this.bookForm.invalid) {
      this.bookForm.markAllAsTouched();
      return;
    }

    if (this.isEdit) {
      this.bookService.updateBook(this.bookId, this.bookForm.value).subscribe(() => {
        this.router.navigate(['/']);
      });
    } else {
      this.bookService.addBook(this.bookForm.value).subscribe(() => {
        this.router.navigate(['/']);
      });
    }
  }
}
