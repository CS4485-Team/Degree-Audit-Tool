import { Component } from '@angular/core';
import { Router } from '@angular/router';
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
    selectedDegreePlan: string = 'Cyber Security';
    selectedMajor: string = 'Computer Science';
    studentName: string = '';
    studentId: string = '';
    admitSem: string = '';
    gradSem: string = '';
    degreePlanData: any[] = []; // same data as in degree plan component

    constructor(private router: Router) {}

    testIPC() {
        console.log(electron.ipcRenderer.send('test'));
    }
    
    onSelectedDegreePlan(value: string) {
        this.selectedDegreePlan = value;
    }

    onSelectedMajor(value: string) {
        this.selectedMajor = value;
    }

    onSelectedElective(value: string) {
        this.selectedElectives.push(value);
        console.log(this.selectedElectives);
    }

    removeElective(value: string) {
        for (let i = 0; i < this.selectedElectives.length; ++i) {
            if (this.selectedElectives[i].name == value) {
                this.selectedElectives.splice(i, 1);
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
        console.log();

        // push headers
        data.push(headers);
        data.push('\n');

        // push data
        let start: number = 12; // offset used to skip the header portion of the degree plan
                                  //  (student name, id, UTD title, etc.)
        for (let i = start; i < start + auditReportConfigs.courseCount.numCoreCourses; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '')
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            data.push(filteredData);
            data.push('\n');
        }
        data = data.slice(dataStart, configs.outputCourseInfo[this.selectedDegreePlan].numCoreCourses);
        dataStart += configs.outputCourseInfo[this.selectedDegreePlan].numCoreCourses;

        start += auditReportConfigs.courseCount.numCoreCourses + 1;
        for (let i = start; i < start + auditReportConfigs.courseCount.numAdditionalCoreCourses; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '')
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            data.push(filteredData);
            data.push('\n');
        }
        data = data.slice(dataStart, configs.outputCourseInfo[this.selectedDegreePlan].numAdditionalCoreCourses);
        dataStart += configs.outputCourseInfo[this.selectedDegreePlan].numAdditionalCoreCourses;

        start += auditReportConfigs.courseCount.numAdditionalCoreCourses + 1;
        for (let i = start; i < start + auditReportConfigs.courseCount.numElectiveCourses; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '')
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            data.push(filteredData);
            data.push('\n');
        }
        data = data.slice(dataStart, configs.outputCourseInfo[this.selectedDegreePlan].numElectiveCourses);
        dataStart += configs.outputCourseInfo[this.selectedDegreePlan].numElectiveCourses;

        start += auditReportConfigs.courseCount.numElectiveCourses + 1;
        for (let i = start; i < start + auditReportConfigs.courseCount.numAdditionalElectiveCourses; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '')
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            data.push(filteredData);
            data.push('\n');
        }
        data = data.slice(dataStart, configs.outputCourseInfo[this.selectedDegreePlan].numAdditionalElectiveCourses);
        dataStart += configs.outputCourseInfo[this.selectedDegreePlan].numAdditionalElectiveCourses;

        start += auditReportConfigs.courseCount.numAdditionalElectiveCourses + 1;
        for (let i = start; i < start + auditReportConfigs.courseCount.numOtherRequirements; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '')
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            data.push(filteredData);
            data.push('\n');
        }
        data = data.slice(dataStart, configs.outputCourseInfo[this.selectedDegreePlan].numOtherRequirements);
        dataStart += configs.outputCourseInfo[this.selectedDegreePlan].numOtherRequirements;

        start += auditReportConfigs.courseCount.numOtherRequirements + 1;
        for (let i = start; i < start + auditReportConfigs.courseCount.numAdmissionPrereqs; ++i) {
            var filteredData: any[] = [];
            for (let j = 0; j < this.degreePlanData[i].length; ++j) {
                if (this.degreePlanData[i][j] == '')
                    filteredData.push('blank');
                else
                    filteredData.push(this.degreePlanData[i][j]);
            }
            data.push(filteredData);
            data.push('\n');
        }
        data = data.slice(dataStart, configs.outputCourseInfo[this.selectedDegreePlan].numAdmissionPrereqs);
        dataStart += configs.outputCourseInfo[this.selectedDegreePlan].numAdmissionPrereqs;

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
    }
}