import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'startSelect',
  templateUrl: './startSelect.component.html',
  styleUrls: ['./startSelect.component.css']
})

export class StartSelectComponent {
    constructor(private router: Router) {}

    uploadTranscriptFile() {

    }

    uploadStudentObjectFile() {
        
    }
  }