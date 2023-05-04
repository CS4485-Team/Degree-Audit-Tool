import { Component } from '@angular/core';
import { Router } from '@angular/router';

declare const electron: any;

@Component({
  selector: 'startSelect',
  templateUrl: './startSelect.component.html',
  styleUrls: ['./startSelect.component.css']
})

export class StartSelectComponent {
    inputNode: any;
    constructor(private router: Router) {}

    uploadTranscriptFile() {
      let container: any = document.getElementById("continueButton");
      this.inputNode = document.createElement("input");
      this.inputNode.type = "file";
      this.inputNode.click();
    }

    uploadStudentObjectFile() {

    }

    continue() {
      let r = this.router;
      let promise = new Promise(function(resolve, reject) {
        let files: any = document.getElementById("transcriptInput"); // FileList object
        // console.log(files.files[0]);

        // use the 1st file from the list
        let f = files.files[0];
        
        let reader = new FileReader();

        // Closure to capture the file information.
        reader.onload = (function(theFile) {
            return function(e: any) {
              resolve(e.target.result);
            };
        })(f);

        // Read in the image file as a data URL.
        // reader.readAsText(f);
        reader.readAsDataURL(f);
      })
      
      promise.then(
        function(value) {
          // r.navigateByUrl('/degreePlan', {state: {preload: value}})
          electron.ipcRenderer.send("parseTranscript", value);
        }
      )
    }

    continueScratch() {
      this.router.navigateByUrl("/degreePlan", {state: {preload: []}})
    }
  }