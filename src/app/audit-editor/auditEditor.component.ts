import { Component, Input } from '@angular/core';
import auditReportConfigs from 'auditreportConfig.json';
import Handsontable from 'handsontable';

/*
    loadPrepopulationData
    params: none
    returns: any[]
    desc:
        loads data to prepolulate the table. This should only be called if the table
        has not already been updated or is a new table. Each row of the table is pushed
        into the data array seperately. Each row element contains two pieces of info,
        the type of row and the actual row data. The type of row is used to
        instruct the custom renderer how to style the row (i.e., highlight, bold-text, etc.),
        while the data is the actual data loaded into that row.
*/
const loadPrepopulationData = (selectedDegreePlan: string) : any[] => {
    let data: any[] = [];
    data.push({'type': 'title', 'data': ['DEGREE PLAN', '', '', '', '']});
    data.push({'type': 'title', 'data': ['UNIVERSITY OF TEXAS AT DALLAS', '', '', '', '']});
    data.push({'type': 'title', 'data': ['MASTER OF COMPUTER SCIENCE', '', '', '', '']});
    data.push({'type': 'input', 'data': ['', '', '', '', '']});
    data.push({'type': 'title', 'data': [selectedDegreePlan, '', '', '', '']});
    data.push({'type': 'none', 'data': ['FT:', 'Y', 'N']});
    data.push({'type': 'none', 'data': ['Name of Student:', '', '', '', '']});
    data.push({'type': 'input', 'data': ['Student I.D. Number:', '', '', '', '']});
    data.push({'type': 'input', 'data': ['Semester Admitted to Program:', '', '', 'Graduation:', '']});
    data.push({'type': 'title', 'data': ['Course Title', 'Course Number', 'UTD Semester', 'Transfer', 'Grade']});
    data.push({'type': 'title', 'data': ['CORE COURSES     (15 CREDIT HOURS)     3.19 Grade Point Average Required']});

    const courseList: string = JSON.stringify(auditReportConfigs);
    const map = new Map(Object.entries(JSON.parse(courseList)));
    let courses: any = map.get('coreCourseList');
    courses = courses[selectedDegreePlan];

    // push all core courses for this degree plan
    for (let i = 0; i < auditReportConfigs.courseCount.numCoreCourses; ++i) {
        if (i < courses.length)
            data.push({'type': 'input', 'data': [courses[i].name, '', '', '', '']});
        else
            data.push({'type': 'input', 'data': ['', '', '', '', '']});
    };

    data.push({'type': 'title', 'data': ['One of the following Courses']});
    for (let i = 0; i < auditReportConfigs.courseCount.numAdditionalCoreCourses; ++i) {
        data.push({'type': 'input', 'data': ['', '', '', '', '']});
    };

    data.push({'type': 'title', 'data': ['FIVE APPROVED 6000 LEVEL ELECTIVES     (15 * Credit Hours)     3.0 Grade Point Average']});
    for (let i = 0; i < auditReportConfigs.courseCount.numElectiveCourses; ++i) {
        data.push({'type': 'input', 'data': ['', '', '', '', '']});
    };

    data.push({'type': 'title', 'data': ['Additional Electives (3 Credit Hours Minimum)']});
    for (let i = 0; i < auditReportConfigs.courseCount.numAdditionalElectiveCourses; ++i) {
        data.push({'type': 'input', 'data': ['', '', '', '', '']});
    };

    return data;
}

const seperateDataFromSettings = (dataWithSettings: any[]) : [any[], any[]] => {
    let data: any[] = [];
    let settings: any[] = [];

    for (let i = 0; i < dataWithSettings.length; ++i) {
        data.push(dataWithSettings[i].data);
        settings.push(dataWithSettings[i].type);
    }

    return [data, settings];
}

const generateBorders = (settings: any[]) : any[] => {
    let borders: any[] = [
        // black outline for document
        {
            range: { from: {row: 0, col: 0}, to: {row: settings.length - 1, col: 4} },
            top: {width: 2, color: 'black'},
            bottom: {width: 2, color: 'black'},
            left: {width: 2, color: 'black'},
            right: {width: 2, color: 'black'}
            
        },
        { row: 0, col: 0, 
            top: {width: 1, color: 'black'}, 
            bottom: {width: 1, color: 'white'},
        },
        { row: 1, col: 0, 
            bottom: {width: 1, color: 'white'},
        },
        { row: 2, col: 0, 
            bottom: {width: 1, color: 'white'},
        },
        { row: 8, col: 0, 
            bottom: {width: 2, color: 'black'},
        },
    ]

    return borders;
}

const sectionHeader = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
    Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
    td.style.background = '#fabf8f';
    td.style.fontWeight = 'bold';
}

const title = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
    Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
    td.style.fontWeight = 'bold';
}

@Component({
    selector: 'audit-editor',
    template: `
    <hot-table class="hot-table" id="degree-plan-table"
        [settings]="settings"
        [data]="preloadData"
        [colHeaders]="false"
        [rowHeaders]="false"
        licenseKey="non-commercial-and-evaluation">
    </hot-table>
    `,
    styleUrls: ['./auditEditor.component.css']
})

export class AuditEditorComponent {
    @Input() selectedDegreePlan: string = 'Cyber Security';
    // data to pre-populate audit reports
    preloadDataDicts: any[] = loadPrepopulationData(this.selectedDegreePlan);
    preloadDataAndSettings: any[] = seperateDataFromSettings(this.preloadDataDicts);
    preloadData = this.preloadDataAndSettings[0];
    borders: any[] = generateBorders(this.preloadDataAndSettings[1]);
    
    settings: Handsontable.GridSettings = {
        width: '75%',
        height: 'auto',
        stretchH: 'all',
        cell: [
            { row: 0, col: 0, className: 'htCenter', renderer: title },
            { row: 1, col: 0, className: 'htCenter', renderer: title },
            { row: 2, col: 0, className: 'htCenter', renderer: title },
            { row: 4, col: 0, className: 'htCenter', renderer: title },
            { row: 10, col: 0, className: 'htCenter', renderer: sectionHeader },
            { row: 16, col: 0, className: 'htCenter', renderer: sectionHeader },
            { row: 19, col: 0, className: 'htCenter', renderer: sectionHeader },
            { row: 25, col: 0, className: 'htCenter', renderer: sectionHeader },
        ],
        mergeCells: [
            { row: 0, col: 0, rowspan: 1, colspan: 5 },
            { row: 1, col: 0, rowspan: 1, colspan: 5 },
            { row: 2, col: 0, rowspan: 1, colspan: 5 },
            { row: 3, col: 0, rowspan: 1, colspan: 5 },
            { row: 4, col: 0, rowspan: 1, colspan: 5 },
            { row: 10, col: 0, rowspan: 1, colspan: 5 },
            { row: 16, col: 0, rowspan: 1, colspan: 5 },
            { row: 19, col: 0, rowspan: 1, colspan: 5 },
            { row: 25, col: 0, rowspan: 1, colspan: 5 },
        ],
        customBorders: this.borders
    };

    ngOnChanges() {
        this.preloadDataDicts = loadPrepopulationData(this.selectedDegreePlan);
        this.preloadDataAndSettings = seperateDataFromSettings(this.preloadDataDicts);
        this.preloadData = this.preloadDataAndSettings[0];
        this.borders = generateBorders(this.preloadDataAndSettings[1]);
    }
}