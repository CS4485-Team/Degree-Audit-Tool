import { Component } from '@angular/core';
import { Router } from '@angular/router';
import auditReportConfigs from 'auditreportConfig.json';


@Component({
    selector: 'audit-page',
    templateUrl: './auditPage.component.html',
    styleUrls: ['./auditPage.component.css']
})

export class AuditComponent {
    degreePlans: string[] = auditReportConfigs.degreePlans;
    selectedDegreePlan: string = 'Cyber Security';
    studentName: string = '';
    studentId: string = '';
    degreePlanData: any[] = []; // same data as in degree plan component

    constructor(private router: Router) {}
    
    onSelected(value: string): void {
        this.selectedDegreePlan = value;
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