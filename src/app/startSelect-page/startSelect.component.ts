import { Component } from '@angular/core';
import { Router } from '@angular/router';

declare const electron: any;

@Component({
  selector: 'startSelect',
  templateUrl: './startSelect.component.html',
  styleUrls: ['./startSelect.component.css']
})

export class StartSelectComponent {
    inputFileName: string | null = "";

    constructor(private router: Router) {}

    // allow the user to continue to the degree plan creation page without
    //  uploading one of the prepopulation files
    continueScratch() {
      this.router.navigateByUrl("/degreePlan", {state: {preload: []}})
    }

    // upload a student transcript file to prepopulate the degree plan table.
    //  only accepts pdf files as input
    uploadTranscriptFile() {
      const inputNode: HTMLInputElement = document.createElement("input");
      inputNode.type = "file";
      inputNode.accept = ".pdf";
      inputNode.id = "fileInput";

      const updateInputFileName = (event: any) => {
        this.inputFileName = event.target.files[0].name;
      }

      inputNode.addEventListener("change", updateInputFileName, false);
      inputNode.click();
    }

    // upload a student object file to prepopulate the degree plan table.
    //  only accepts stdobj files as input
    uploadStudentObjectFile() {
      const inputNode: HTMLInputElement = document.createElement("input");
      inputNode.type = "file";
      inputNode.accept = ".stdobj";
      inputNode.id = "fileInput";

      const updateInputFileName = (event: any) => {
        this.inputFileName = event.target.files[0].name;
      }

      inputNode.addEventListener("change", updateInputFileName, false);
      inputNode.click();
    }

    // allow the user to continue to the next page after they have uploaded one of the required
    //  files
    continue() {
      const router = this.router;

      // const promise = new Promise(function(resolve, reject) {
      //   let input: any = document.getElementById("fileInput");
      //   console.log(input.files[0]);

      //   let f = input.files[0];
        
      //   let reader = new FileReader();

      //   reader.onload = (function(theFile) {
      //       return function(e: any) {
      //         resolve(e.target.result);
      //       };
      //   })(f);

      //   // Read in the image file as a data URL.
      //   reader.readAsText(f);
      // })
      
      // promise.then(
      //   function(value) {
      //     router.navigateByUrl('/degreePlan', {state: {preload: value}})
      //   }
      // )

    }
  }