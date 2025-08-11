import { Injectable } from '@angular/core';

export interface BookListState {
  searchTerm: string;
  pageIndex: number;
  pageSize: number;
  totalElements: number;
}

@Injectable({
  providedIn: 'root'
})
export class BookListStateService {
  private state: BookListState = {
    searchTerm: '',
    pageIndex: 0,
    pageSize: 20,
    totalElements: 0,
  };

  getState(): BookListState {
    return this.state;
  }

  setState(state: Partial<BookListState>): void {
    this.state = { ...this.state, ...state };
  }

  resetState(): void {
    this.state = {
      searchTerm: '',
      pageIndex: 0,
      pageSize: 10,
      totalElements: 0,
    };
  }
}
