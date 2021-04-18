import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TlgCanvasComponent } from './tlg-canvas.component';

describe('TlgCanvasComponent', () => {
  let component: TlgCanvasComponent;
  let fixture: ComponentFixture<TlgCanvasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TlgCanvasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TlgCanvasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
