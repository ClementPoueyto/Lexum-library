import { Directive, HostListener, HostBinding, Input } from '@angular/core';
import { Router } from '@angular/router';

@Directive({
  selector: '[appClickableCell]'
})
export class ClickableCellDirective {
  @Input() bookId!: number;

  @HostBinding('style.cursor') cursor = 'pointer';

  constructor(private router: Router) {}

  @HostListener('click', ['$event'])
  onClick(event: MouseEvent) {
    if (this.bookId != null) {
      this.router.navigate(['/books', this.bookId]);
    }
  }
}
