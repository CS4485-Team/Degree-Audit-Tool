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

    inputFileName: string = "";

    /*  StartSelectComponent
        Component to hold the logic for the start-select page. This page is intended to allow the
          user to select which option they would like to initiate the degree plan editor with.
        :param: router (Router) -> component used to route the user from the start-select page to the
          degreePlan page
        :param: httpClient (HttpClient) -> component used to read local file information. This component
          is used after the contents of the transcript PDF has been converted to a csv file, and when the
          user selects to start from a student object file
        :returns: none
    */
    constructor(private router: Router, private httpClient: HttpClient) {}

    /*  continueScratch
        Function to route the user to the degreePlan page with no prepopulation file selected.
    */
    continueScratch() {
      this.router.navigateByUrl("/degreePlan", {state: {preload: []}})
    }

    /*  uploadTranscriptFile
        Allows the user to upload a transcript PDF file in order to prepopulate the degreePlan page.
        The user can only input a PDF file if they select this option.
    */
    uploadTranscriptFile() {
      // the HTML element that acts as the holder for the input file
      const inputNode: HTMLInputElement = document.getElementById("fileInput") as HTMLInputElement;

      // the filename needs to be updated in a function outside of the promise since any reference to
      //  'this' will reference the promise itself
      const updateFileName = (name: string) => {
        this.inputFileName = name;
      }

      // updates the file information once the user has uploaded the file
      const updateFile = (event: any) => {
        this.inputFileName = "Loading file info...";

        // convert file from pdf to csv. This must be done in a promise since the
        //  file reading is async (meaning, the program may try to continue before the
        //  file contents are read, so we must wait until the contents are finished before
        //  proceeding).
        const promise = new Promise(function(resolve, reject) {
          electron.ipcRenderer.send("clearDirs");
          let reader = new FileReader();

          // once the file is loaded, call the 'copyFile' command in app.js to move the file
          //  to the required input spot for reading
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
            // electron.ipcRenderer.send("moveFile"); // once the file is done being processed, move it to the appropriate folder to preload
          }
        )

      }

      // once a user has input a file, call the updateFile function
      inputNode.addEventListener("change", updateFile, false);

      if (inputNode != null) {
        inputNode.accept = ".pdf"; // only allow the user to upload PDF files
        inputNode.click();
      }
    }

    /*  uploadStudentObjectFile
        Allow the user to upload a student object file to prepopulate the degreePlan page.
    */
    uploadStudentObjectFile() {
      // the HTML element that acts as the holder for the input file
      const inputNode: HTMLInputElement = document.getElementById("fileInput") as HTMLInputElement;

      // the filename needs to be updated in a function outside of the promise since any reference to
      //  'this' will reference the promise itself
      const updateFileName = (name: string) => {
        this.inputFileName = name;
      }

      // updates the file information once the user has uploaded the file
      const updateFile = (event: any) => {
        this.inputFileName = "Loading file info...";

        // convert file from pdf to csv. This must be done in a promise since the
        //  file reading is async (meaning, the program may try to continue before the
        //  file contents are read, so we must wait until the contents are finished before
        //  proceeding).
        const promise = new Promise(function(resolve, reject) {
          electron.ipcRenderer.send("clearDirs");
          let reader = new FileReader();

          // once the file is loaded, call the 'copyFile' command in app.js to move the file
          //  to the required input spot for reading
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
            // electron.ipcRenderer.send("moveFile"); // once the file is done being processed, move it to the appropriate folder to preload
          }
        )

      }

      inputNode.addEventListener("change", updateFile, false);

      if (inputNode != null) {
        inputNode.accept = ".csv";
        inputNode.click();
      }


    }

    /*  continue
        Allow the user to route to the degreePlan page once they have uploaded a file.
        The preopulation file must be uploaded before they are able to continue.
    */
    continue() {
      try {
        this.httpClient.get("../../Test.csv", {responseType: 'text'}).subscribe((csvData) => {
          this.router.navigateByUrl('/degreePlan', {state: {preload: csvData}});
        })
      }
      catch {
        this.inputFileName = "Error reading input file. Please try again.";
      }
    }
  }