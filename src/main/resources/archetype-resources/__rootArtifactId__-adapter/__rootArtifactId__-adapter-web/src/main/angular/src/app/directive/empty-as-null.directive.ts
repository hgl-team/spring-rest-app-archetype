import {Directive, HostListener, Output, EventEmitter} from '@angular/core';

@Directive({
  selector: '[appEmptyAsNull]'
})
export class EmptyAsNullDirective {
  @Output()
  response = new EventEmitter<any>();

  constructor() { }

  @HostListener('keyup', ['$event.target'])
  onEvent(target: HTMLInputElement): void {
    this.response.emit(target.value === '' ? null : target.value);
  }
}
