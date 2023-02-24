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
    
    onSelected(value: string): void {
        this.selectedDegreePlan = value;
    }
}