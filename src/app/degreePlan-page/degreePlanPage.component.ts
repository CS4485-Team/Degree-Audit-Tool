import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import configFile from 'config.json';

declare const electron: any;

@Component({
    selector: 'degreePlan-page',
    templateUrl: './degreePlanPage.component.html',
    styleUrls: ['./degreePlanPage.component.css']
})

export class DegreePlanPageComponent {
    configs: any;
    degreePlans: string[] = Object.keys(configFile.degreePlans);
    majors: string[] = configFile.majors;
    electives: any[] = configFile.electives;
    prereqs: any[] = [];
    selectedElectives: any[] = [];
    selectedAddElectives: any[] = [];
    selectedDegreePlan: string = 'Cyber Security';
    selectedMajor: string = 'Computer Science';
    selectedPrereqs: any[] = [];
    studentName: string = '';
    studentId: string = '';
    admitSem: string = '';
    gradSem: string = '';
    isFT: string = 'F';
    isTH: string = 'F';
    importedClassData: any = [];
    degreePlanData: any[] = []; // same data as in degree plan component
    errorMessage: string = "";
    isLoadingFromTranscript: boolean = true;
    saveDir: string = 'test';

    constructor(private router: Router, private httpClient: HttpClient) {
        const state: any = router.getCurrentNavigation()?.extras.state
        if (state['preload'].length != 0) {
            const csvData: any = this.CSVToArray(state['preload'], ',');

            try {
                if (state['uploadingTranscript']) {
                    this.isLoadingFromTranscript = true;
                    this.studentName = csvData[csvData.length - 2][0];
                    this.studentId = csvData[csvData.length - 2][1];
                    this.admitSem = csvData[csvData.length - 2][2];
                    this.importedClassData = csvData;
                    this.selectedMajor = csvData[csvData.length - 2][3];
                }
                else {
                    this.isLoadingFromTranscript = false;
                    this.studentName = csvData[0][0];
                    this.studentId = csvData[0][1];
                    this.admitSem = csvData[0][2];
                    this.importedClassData = csvData;
                    this.selectedMajor = csvData[0][3];
                }
            }
            catch {
                console.log("Error in loading preload data into document.");
            }
        }

        const courseList: string = JSON.stringify(configFile);
        this.configs = new Map(Object.entries(JSON.parse(courseList)));
        const prereqs = this.configs.get('degreePlans')[this.selectedDegreePlan]['prereqCourseList'];
        for (let i = 0; i < prereqs.length; ++i) {
            this.selectedPrereqs.push(prereqs[i]);
        }
    }

    setIsFT(value: boolean) {
        if (value)
            this.isFT = "Y";
        else
            this.isFT = "N";
    }

    setIsTH(value: boolean) {
        if (value)
            this.isTH = "Y";
        else
            this.isTH = "N";
    }
    
    onSelectedDegreePlan(value: string) {
        this.selectedDegreePlan = value;
        const courseList: string = JSON.stringify(configFile);
        this.configs = new Map(Object.entries(JSON.parse(courseList)));
    }

    onSelectedMajor(value: string) {
        this.selectedMajor = value;
    }

    onSelectedElective(value: any) {
        const courseList: string = JSON.stringify(configFile);
        const configs: any = new Map(Object.entries(JSON.parse(courseList)));
        if (this.selectedElectives.length == configs.get('outputCourseInfo')[this.selectedDegreePlan].numElectiveCourses)
            return;
        
        for (let i = 0; i < this.electives.length; ++i) {
            if (this.electives[i].number == value) {
                this.selectedElectives.push(this.electives[i]);
                this.electives.splice(i, 1);
            }
        }
        this.selectedElectives = [...this.selectedElectives];
    }

    removeElective(value: any) {
        for (let i = 0; i < this.selectedElectives.length; ++i) {
            if (this.selectedElectives[i].number == value.number) {
                this.electives.push(this.selectedElectives[i]);
                this.selectedElectives.splice(i, 1);
            }
        }
        this.selectedElectives = [...this.selectedElectives];
    }

    onSelectedAddElective(value: any) {
        const courseList: string = JSON.stringify(configFile);
        const configs: any = new Map(Object.entries(JSON.parse(courseList)));
        if (this.selectedAddElectives.length == configs.get('outputCourseInfo')[this.selectedDegreePlan].numAdditionalElectiveCourses)
            return;

        for (let i = 0; i < this.electives.length; ++i) {
            if (this.electives[i].number == value) {
                this.selectedAddElectives.push(this.electives[i]);
                this.electives.splice(i, 1);
            }
        }
        this.selectedAddElectives = [...this.selectedAddElectives];
    }

    removeAddElective(value: any) {
        for (let i = 0; i < this.selectedAddElectives.length; ++i) {
            if (this.selectedAddElectives[i].number == value.number) {
                this.electives.push(this.selectedAddElectives[i]);
                this.selectedAddElectives.splice(i, 1);
            }
        }
        this.selectedAddElectives = [...this.selectedAddElectives];
    }

    onSelectedPrereq(value: any) {
        for (let i = 0; i < this.prereqs.length; ++i) {
            if (this.prereqs[i].number == value) {
                this.selectedPrereqs.push(this.prereqs[i]);
                this.prereqs.splice(i, 1);
            }
        }
        this.selectedPrereqs = [...this.selectedPrereqs];
    }

    removePrereq(value: any) {
        for (let i = 0; i < this.selectedPrereqs.length; ++i) {
            if (this.selectedPrereqs[i].number == value.number) {
                this.prereqs.push(this.selectedPrereqs[i]);
                this.selectedPrereqs.splice(i, 1);
                console.log(this.prereqs);
            }
        }
        this.selectedPrereqs = [...this.selectedPrereqs];
    }

    setData(data: any[]) {
        this.degreePlanData = data;
    }

    saveDegreePlan(): any {
        const courseList: string = JSON.stringify(configFile);
        const configs: any = new Map(Object.entries(JSON.parse(courseList)));

        let errorsExist: boolean = this.studentName.length == 0 || this.studentId.length != 10 || this.admitSem.length != 3 || this.gradSem.length != 3;
        if (errorsExist) {
            return null;
        }

        // reformat the data for csv output
        let data: any[] = [];

        // push headers
        data.push([this.studentName, this.studentId, this.admitSem, `${this.isFT}${this.isTH}`, this.gradSem]);
        data.push('\n');

        // push data
        let start: number = 12; // offset used to skip the header portion of the degree plan
                                  //  (student name, id, UTD title, etc.)

        for (let i = start; i < start + configs.get("outputCourseInfo")[this.selectedDegreePlan]["numCoreCourses"]; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '' || this.degreePlanData[i][j] == undefined) 
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            if (filteredData.length == 0) {
                filteredData = ['blank', 'blank', 'blank', 'blank', 'blank'];
            }
            data.push(filteredData);
            data.push('\n');
        }

        start += configs.get("outputCourseInfo")[this.selectedDegreePlan]["numCoreCourses"] + 1;
        for (let i = start; i < start + configs.get("outputCourseInfo")[this.selectedDegreePlan]["numAdditionalCoreCourses"]; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '' || this.degreePlanData[i][j] == undefined)
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            if (filteredData.length == 0) {
                filteredData = ['blank', 'blank', 'blank', 'blank', 'blank'];
            }
            data.push(filteredData);
            data.push('\n');
        }

        start += configs.get("outputCourseInfo")[this.selectedDegreePlan]["numAdditionalCoreCourses"] + 1;
        for (let i = start; i < start + configs.get("outputCourseInfo")[this.selectedDegreePlan]["numElectiveCourses"]; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '' || this.degreePlanData[i][j] == undefined)
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            if (filteredData.length == 0) {
                filteredData = ['blank', 'blank', 'blank', 'blank', 'blank'];
            }
            data.push(filteredData);
            data.push('\n');
        }

        start += configs.get("outputCourseInfo")[this.selectedDegreePlan]["numElectiveCourses"] + 1;
        for (let i = start; i < start + configs.get("outputCourseInfo")[this.selectedDegreePlan]["numAdditionalElectiveCourses"]; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '' || this.degreePlanData[i][j] == undefined)
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            if (filteredData.length == 0) {
                filteredData = ['blank', 'blank', 'blank', 'blank', 'blank'];
            }
            data.push(filteredData);
            data.push('\n');
        }

        start += configs.get("outputCourseInfo")[this.selectedDegreePlan]["numAdditionalElectiveCourses"] + 1;
        for (let i = start; i < start + configs.get("outputCourseInfo")[this.selectedDegreePlan]["numOtherRequirements"]; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '' || this.degreePlanData[i][j] == undefined)
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            if (filteredData.length == 0) {
                filteredData = ['blank', 'blank', 'blank', 'blank', 'blank'];
            }
            data.push(filteredData);
            data.push('\n');
        }

        start += configs.get("outputCourseInfo")[this.selectedDegreePlan]["numOtherRequirements"] + 1;
        for (let i = start; i < start + configs.get("outputCourseInfo")[this.selectedDegreePlan]["numAdmissionPrereqs"]; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '' || this.degreePlanData[i][j] == undefined)
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            if (filteredData.length == 0) {
                filteredData = ['blank', 'blank', 'blank', 'blank', 'blank'];
            }
            data.push(filteredData);
            data.push('\n');
        }

        return data;
    }

    updateDegreePlanData(newData: any[]) {
        this.degreePlanData = newData;
    }

    // confirm the user wants to navigate away from this page before they lose changes
    confirmBack() {
        if (confirm('You will lose changes if you go back. Are you sure you would like to continue?')) {
            this.router.navigate(['/home-page']);
        }
    }

    // check whether the user left any fields blank. If not, proceed to the next section of the
    //  application. Else, alert the user that some fields were not fully entered properly
    async confirmSavePDF() {
        if (confirm("Submit and save degree plan / audit report?")) {
            var data: any[] = this.saveDegreePlan();
            if (data == null) {
                this.errorMessage = "Please correct any mistakes and try again."
                return;
            }
            this.errorMessage = "";

            electron.ipcRenderer.send("saveCSVFile", data);

            let degreePlanType: number = 1;
            switch(this.selectedDegreePlan) {
                case "Data Science":
                    degreePlanType = 1;
                    break;
                case "Intelligent Systems":
                    degreePlanType = 2;
                    break;
                case "Systems":
                    degreePlanType = 3;
                    break;
                case "Software Engineering":
                    degreePlanType = 4;
                    break;
                case "Cyber Security":
                    degreePlanType = 5;
                    break;
                case "Networks and Telecommunications":
                    degreePlanType = 6;
                    break;
                case "Traditional Computer Science":
                    degreePlanType = 7;
                    break;
                case "Interactive Computing":
                    degreePlanType = 8;
                    break;
            }
            
            electron.ipcRenderer.send("generateDegreePlanPDF", degreePlanType);
            setTimeout(this.saveDegreePlanFile, 1000);
        }
    }

    updateSaveDir(e: any) {
        this.saveDir = e.target.value;
        console.log(this.saveDir);
    }

    saveDegreePlanFile() {
        console.log(this.saveDir);
        if (this.saveDir == '' || this.saveDir == undefined) {
            electron.ipcRenderer.send('saveDegreePlanAndAudit', configFile.outputFolder);
        }
        else {
            electron.ipcRenderer.send('saveDegreePlanAndAudit', this.saveDir);
        }
    }

    confirmSaveStudentObject() {
        var data: any[] = this.saveDegreePlan();
        let blob: Blob = new Blob(data, {type: 'text/csv'});
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.click();
    }


    CSVToArray(strData: string, strDelimiter: string){
		// Check to see if the delimiter is defined. If not,
		// then default to comma.
		strDelimiter = (strDelimiter || ",");

		// Create a regular expression to parse the CSV values.
		var objPattern = new RegExp(
			(
				// Delimiters.
				"(\\" + strDelimiter + "|\\r?\\n|\\r|^)" +

				// Quoted fields.
				"(?:\"([^\"]*(?:\"\"[^\"]*)*)\"|" +

				// Standard fields.
				"([^\"\\" + strDelimiter + "\\r\\n]*))"
			),
			"gi"
			);

		var arrData: any = [[]];
		var arrMatches: any = null;

		while (arrMatches = objPattern.exec( strData )){
			var strMatchedDelimiter = arrMatches[ 1 ];
			if (
				strMatchedDelimiter.length &&
				(strMatchedDelimiter != strDelimiter)
				){
				arrData.push([]);

			}
			if (arrMatches[ 2 ]){
				var strMatchedValue: any = arrMatches[ 2 ].replace(
					new RegExp( "\"\"", "g" ),
					"\""
					);
			} else {
				var strMatchedValue: any = arrMatches[ 3 ];
			}
			arrData[arrData.length - 1].push(strMatchedValue);
		}
		return(arrData);
	}
}