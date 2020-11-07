import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { TlgCanvasComponent } from './tlg-canvas/tlg-canvas.component';

@NgModule({
    declarations: [
            AppComponent,
            TlgCanvasComponent
    ],
    imports: [
            BrowserModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
