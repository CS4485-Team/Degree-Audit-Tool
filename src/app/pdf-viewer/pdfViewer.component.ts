import { Component } from '@angular/core';

@Component({
  selector: 'pdf-view',
  template: `
  <pdf-viewer [src]="pdfSrc"
              [render-text]="true"
              [original-size]="false"
              style="width: 100%; height: 100vh"
  ></pdf-viewer>
  `
})

export class PDFViewerComponent {
  pdfSrc = "./test.pdf";
}