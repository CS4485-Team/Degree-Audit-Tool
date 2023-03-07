import { Component } from '@angular/core';
import auditReportConfigs from 'auditreportConfig.json';


@Component({
    selector: 'audit-page',
    templateUrl: './auditPage.component.html',
    styleUrls: ['./auditPage.component.css']
})

export class AuditComponent {
    degreePlans: string[] = auditReportConfigs.degreePlans;
    selectedDegreePlan: string = '';
    studentName: string = '';
    
    onSelected(value: string): void {
        this.selectedDegreePlan = value;
    }

    studentNameUpdated(value: string): void {
        this.studentName = value;
        console.log(this.studentName);
    }
}