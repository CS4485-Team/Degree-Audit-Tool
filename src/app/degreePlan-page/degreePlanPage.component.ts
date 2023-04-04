import { Component } from '@angular/core';
import { Router } from '@angular/router';
import auditReportConfigs from 'auditreportConfig.json';


@Component({
    selector: 'degreePlan-page',
    templateUrl: './degreePlanPage.component.html',
    styleUrls: ['./degreePlanPage.component.css']
})

export class DegreePlanPageComponent {
    degreePlans: string[] = auditReportConfigs.degreePlans;
    majors: string[] = auditReportConfigs.majors;
    selectedDegreePlan: string = 'Cyber Security';
    selectedMajor: string = "Computer Science";
    studentName: string = '';
    studentId: string = '';
    degreePlanData: any[] = []; // same data as in degree plan component

    constructor(private router: Router) {}
    
    onSelectedDegreePlan(value: string) {
        this.selectedDegreePlan = value;
    }

    onSelectedMajor(value: string) {
        this.selectedMajor = value;
    }

    setData(data: any[]) {
        this.degreePlanData = data;
    }

    saveDegreePlan() {
        const courseList: string = JSON.stringify(auditReportConfigs);
        const configs: any = new Map(Object.entries(JSON.parse(courseList)));

        // reformat the data for csv output
        const headers: string[] = ['Course Title', 'Course Num', 'UTD Semester', 'Transfer/Waiver', 'Grade'];
        const data: any[] = [];

        // push data
        let courses: any = configs.get('coreCourseList');
        courses = courses[this.selectedDegreePlan];

        data.push(headers);
        data.push('\n');
        for (let i = 0; i < courses.length; ++i) {
            data.push([courses[i].name, courses[i].number, 'S23', 'Y', 'A']);
            data.push('\n');
        }

        // save the file
        let blob: Blob = new Blob(data, {type: 'text/csv'});
        var link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.click(); 
    }

    updateDegreePlanData(newData: any[]) {
        this.degreePlanData = newData;
    }

    // confirm the user wants to navigate away from this page before they lose changes
    confirmBack() {
        if (confirm("You will lose changes if you go back. Are you sure you would like to continue?")) {
            this.router.navigate(['/home-page']);
        }
    }
}