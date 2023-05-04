import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'pdf-view',
  templateUrl: './pdfViewer.component.html'
})

export class PDFViewerComponent {
  pdfSrc = "./output/DegreePlan.pdf";

  constructor(private router: Router) {}

  continue() {
    
  }

  back() {
    this.router.navigate(['/degreePlan']);
  }
}