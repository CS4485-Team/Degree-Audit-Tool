import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import auditReportConfigs from 'auditreportConfig.json';

declare var electron: any;

@Component({
    selector: 'degreePlan-page',
    templateUrl: './degreePlanPage.component.html',
    styleUrls: ['./degreePlanPage.component.css']
})

export class DegreePlanPageComponent {
    degreePlans: string[] = auditReportConfigs.degreePlans;
    majors: string[] = auditReportConfigs.majors;
    electives: any[] = auditReportConfigs.electives;
    selectedElectives: any[] = [];
    selectedAddElectives: any[] = [];
    selectedDegreePlan: string = 'Cyber Security';
    selectedMajor: string = 'Computer Science';
    studentName: string = '';
    studentId: string = '';
    admitSem: string = '';
    gradSem: string = '';
    importedClassData: any = [];
    degreePlanData: any[] = []; // same data as in degree plan component

    constructor(private router: Router) {
        const state: any = router.getCurrentNavigation()?.extras.state
        // console.log(state['preload']);

        const csvData: any = this.CSVToArray(state['preload'], ',');

        try {
            this.studentName = csvData[csvData.length - 2][0];
            this.studentId = csvData[csvData.length - 2][1];
            this.admitSem = csvData[csvData.length - 2][2];
            this.importedClassData = csvData;
            this.selectedMajor = csvData[csvData.length - 2][3];
        }
        catch {
            console.log("Error in loading preload data into document.");
        }
    }

    testIPC() {
        electron.ipcRenderer.send('test');
    }
    
    onSelectedDegreePlan(value: string) {
        this.selectedDegreePlan = value;
    }

    onSelectedMajor(value: string) {
        this.selectedMajor = value;
    }

    onSelectedElective(value: any) {
        for (let i = 0; i < this.electives.length; ++i) {
            if (this.electives[i].number == value) {
                this.selectedElectives.push(this.electives[i]);
                this.electives.splice(i, 1);
            }
        }
    }

    removeElective(value: any) {
        for (let i = 0; i < this.selectedElectives.length; ++i) {
            if (this.selectedElectives[i].number == value.number) {
                this.electives.push(this.selectedElectives[i]);
                this.selectedElectives.splice(i, 1);
            }
        }
    }

    onSelectedAddElective(value: any) {
        for (let i = 0; i < this.electives.length; ++i) {
            if (this.electives[i].number == value) {
                this.selectedAddElectives.push(this.electives[i]);
                this.electives.splice(i, 1);
            }
        }
    }

    removeAddElective(value: any) {
        for (let i = 0; i < this.selectedAddElectives.length; ++i) {
            if (this.selectedAddElectives[i].number == value.number) {
                this.electives.push(this.selectedAddElectives[i]);
                this.selectedAddElectives.splice(i, 1);
            }
        }
    }

    setData(data: any[]) {
        this.degreePlanData = data;
    }

    saveDegreePlan(): any[] {
        const courseList: string = JSON.stringify(auditReportConfigs);
        const configs: any = new Map(Object.entries(JSON.parse(courseList)));

        // reformat the data for csv output
        const headers: string[] = ['Course Title', 'Course Num', 'UTD Semester', 'Transfer/Waiver', 'Grade'];
        let dataStart: number = 0;
        let data: any[] = [];

        // push headers
        data.push(headers);
        data.push('\n');

        // push data
        let start: number = 12; // offset used to skip the header portion of the degree plan
                                  //  (student name, id, UTD title, etc.)

        for (let i = start; i < start + configs.get("outputCourseInfo")[this.selectedDegreePlan]["numCoreCourses"]; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '')
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
                if (this.degreePlanData[i][j] == '')
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
                if (this.degreePlanData[i][j] == '')
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
                if (this.degreePlanData[i][j] == '')
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
                if (this.degreePlanData[i][j] == '')
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
                if (this.degreePlanData[i][j] == '')
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

        console.log(data);
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
    confirmSave() {
        var data: any[] = this.saveDegreePlan();
        let blob: Blob = new Blob(data, {type: 'text/csv'});
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.click();
        // if (confirm('Are you sure you would like to continue?')) {
        //     this.router.navigate(['/viewPdf']);
        // }
    }

    uploadTranscriptFile() {
        var input = document.createElement('input');
        input.type = 'file';
        input.click();
        electron.ipcRenderer.send("transcriptUpload", ("hello"));
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
		var arrMatches = null;

		while (arrMatches = objPattern.exec( strData )){
			var strMatchedDelimiter = arrMatches[ 1 ];
			if (
				strMatchedDelimiter.length &&
				(strMatchedDelimiter != strDelimiter)
				){
				arrData.push( [] );

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