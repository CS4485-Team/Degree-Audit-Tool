import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

declare const electron: any;

@Component({
  selector: 'startSelect',
  templateUrl: './startSelect.component.html',
  styleUrls: ['./startSelect.component.css']
})

export class StartSelectComponent {

    inputFile: File;
    inputFileName: string = "";

    constructor(private router: Router, private httpClient: HttpClient) {}

    // allow the user to continue to the degree plan creation page without
    //  uploading one of the prepopulation files
    continueScratch() {
      this.router.navigateByUrl("/degreePlan", {state: {preload: []}})
    }

    // upload a student transcript file to prepopulate the degree plan table.
    //  only accepts pdf files as input
    uploadTranscriptFile() {
      const inputNode: HTMLInputElement = document.getElementById("fileInput") as HTMLInputElement;

      const updateFileName = (name: string) => {
        this.inputFileName = name;
      }

      const updateFile = (event: any) => {
        this.inputFileName = "Loading file info...";

        // convert file from pdf to csv
        const promise = new Promise(function(resolve, reject) {
          let reader = new FileReader();

          reader.onload = (function(theFile) {
            return function(event: any) {
              electron.ipcRenderer.send("copyFile", theFile.path);
              electron.ipcRenderer.send("parseTranscript");
              resolve(theFile.name);
            };

          })(event.target.files[0]);
          reader.readAsDataURL(event.target.files[0]);
        });

        promise.then(
          function(value: any) {
            updateFileName(value);
            electron.ipcRenderer.send("moveFile");
          }
        )

      }

      inputNode.addEventListener("change", updateFile, false);

      if (inputNode != null) {
        inputNode.accept = ".pdf";
        inputNode.click();
      }
    }

    // upload a student object file to prepopulate the degree plan table.
    //  only accepts stdobj files as input
    uploadStudentObjectFile() {
      const inputNode: HTMLInputElement = document.getElementById("fileInput") as HTMLInputElement;

      const updateFile = (event: any) => {
        this.inputFileName = event.target.files[0].name;
        this.inputFile = event.target.files[0];
      }
      inputNode.addEventListener("change", updateFile, false);

      if (inputNode != null) {
        inputNode.accept = ".stdobj";
        inputNode.click();
      }
    }

    // allow the user to continue to the next page after they have uploaded one of the required
    //  files
    continue() {
      try {
        this.httpClient.get("Test.csv", {responseType: 'text'}).subscribe((csvData) => {
          this.router.navigateByUrl('/degreePlan', {state: {preload: csvData}});
        })
      }
      catch {
        this.inputFileName = "Error in loading file. Please try again.";
      }
    }
  }