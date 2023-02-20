import { Component } from '@angular/core';
import auditReportConfigs from 'auditreportConfig.json';

/*
    loads data to prepolulate the table. This should only be called if the table
    has not already been updated or is a new table
*/
const loadPrepopulationData = () : any[] => {
    let data: any[] = [];
    data.push(['Course Title', 'Course Number', 'UTD Semester', 'Transfer', 'Grade']);
    for (let i = 0; i < auditReportConfigs.courseCount.numCoreCourses; ++i) {
        data.push(['', '', '', '', ''])
    }

    return data;
}

@Component({
    selector: 'audit-editor',
    templateUrl: './auditEditor.component.html',
    styleUrls: []
})

export class AuditEditorComponent {
    // data to pre-populate audit reports
    preloadData: any[] = loadPrepopulationData();
}