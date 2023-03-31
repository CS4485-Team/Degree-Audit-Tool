import { Component, Input, Output, EventEmitter, SimpleChanges } from '@angular/core';
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
const loadPrepopulationData = (selectedDegreePlan: string, studentName: string, studentId: string) : any[] => {
    let data: any[] = [];
    data.push({'type': 'mainHeader', 'data': ['DEGREE PLAN', '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': ['UNIVERSITY OF TEXAS AT DALLAS', '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': ['MASTER OF COMPUTER SCIENCE', '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': ['', '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': [selectedDegreePlan, '', '', '', '']});
    data.push({'type': 'binaryInput', 'data': ['FT:', '', '', '', '']});
    data.push({'type': 'binaryInput', 'data': ['Thesis:', '', '', '', '']});
    data.push({'type': 'input', 'data': [`Name: ${studentName}`, '', '', '', '']});
    data.push({'type': 'input', 'data': [`ID: ${studentId}`, '', '', '', '']});
    data.push({'type': 'input', 'data': ['Semester Admitted to Program:', '', '', 'Graduation:', '']});
    data.push({'type': 'input', 'data': ['Course Title', 'Course Number', 'UTD Semester', 'Transfer', 'Grade']});

    const courseList: string = JSON.stringify(auditReportConfigs);
    const configs: any = new Map(Object.entries(JSON.parse(courseList)));

    // push all core courses for this degree plan
    data.push({'type': 'header', 'data': ['CORE COURSES     (15 CREDIT HOURS)     3.19 Grade Point Average Required']});
    let courses: any = configs.get('coreCourseList');
    courses = courses[selectedDegreePlan];
    for (let i = 0; i < courses.length; ++i) {
        data.push({'type': 'input', 'data': [courses[i].name, courses[i].number, '', '', '']});
    };

    // push all additional core course work for this degree plan
    let additionalCourses: any = configs.get('additionalCoreCourseList');
    additionalCourses = additionalCourses[selectedDegreePlan];
    data.push({'type': 'header', 'data': ['One of the following Courses']});
    for (let i = 0; i < additionalCourses.length; ++i) {
        data.push({'type': 'input', 'data': [additionalCourses[i].name, additionalCourses[i].number, '', '', '']});
    };

    data.push({'type': 'header', 'data': ['FIVE APPROVED 6000 LEVEL ELECTIVES     (15 * Credit Hours)     3.0 Grade Point Average']});
    for (let i = 0; i < auditReportConfigs.courseCount.numElectiveCourses; ++i) {
        data.push({'type': 'input', 'data': ['', '', '', '', '']});
    };

    data.push({'type': 'header', 'data': ['Additional Electives (3 Credit Hours Minimum)']});
    for (let i = 0; i < auditReportConfigs.courseCount.numAdditionalElectiveCourses; ++i) {
        data.push({'type': 'input', 'data': ['', '', '', '', '']});
    };

    return data;
}

// need to seperate the data section of each row for loading the table
const seperateDataFromSettings = (dataWithSettings: any[]) : any[] => {
    let data: any[] = [];

    for (let i = 0; i < dataWithSettings.length; ++i) {
        data.push(dataWithSettings[i].data);
    }

    return data;
}

const generateCells = (dataWithSettings: any[]) : any[] => {
    let cells: any[] = [];

    for (let i = 0; i < dataWithSettings.length; ++i) {
        if (dataWithSettings[i].type == 'header') {
            cells.push({ row: i, col: 0, className: 'htCenter', renderer: header });
        }
        else if (dataWithSettings[i].type == 'mainHeader') {
            cells.push({ row: i, col: 0, className: 'htCenter', renderer: mainHeader });
        }
        else if (dataWithSettings[i].type == 'binaryInput') {
            cells.push({ row: i, col: 1, className: 'htCenter', type: 'checkbox', label: { position: 'before', value: 'Y ' }});
            cells.push({ row: i, col: 2, className: 'htCenter', type: 'checkbox', label: { position: 'before', value: 'N ' }});
        }
        else {
            for (let j = 0; j < dataWithSettings[i].data.length; ++j) {
                cells.push({ row: i, col: j, renderer: input });
            }
        }
    }

    return cells;
}

const generateMergeCells = (dataWithSettings: any[]) : any[] => {
    let mergeCells: any[] = [];

    for (let i = 0; i < dataWithSettings.length; ++i) {
        if (dataWithSettings[i].type == 'header' || dataWithSettings[i].type == 'mainHeader') {
            mergeCells.push({ row: i, col: 0, rowspan: 1, colspan: 5 });
        }
    }

    return mergeCells;
}

/*
    generates the custom borders for the table
*/
const generateBorders = (dataWithSettings: any[]) : any[] => {
    let borders: any[] = [
        // black outline for document
        {
            range: { from: {row: 0, col: 0}, to: {row: dataWithSettings.length - 1, col: dataWithSettings[0].data.length - 1} },
            top: {width: 2, color: 'black'},
            bottom: {width: 2, color: 'black'},
            left: {width: 2, color: 'black'},
            right: {width: 2, color: 'black'}
        },
    ];

    return borders;
}

const mainHeader = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
    Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
    td.style.fontWeight = 'bold';
    td.style.borderBottom = '#000'
}

const header = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
    Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
    td.style.background = '#fabf8f';
    td.style.fontWeight = 'bold';
}

const input = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
    Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
    td.style.border = '1px solid black'
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
    // input taken from the auditPage. Must preset to some value, otherwise the table will not be able
    // to render first time
    @Input() selectedDegreePlan: string = '';
    @Input() studentName: string = '';
    @Input() studentId: string = '';

    // data to pre-populate audit reports
    preloadDataWithSettings: any[];
    preloadData: any[];
    @Output() preloadDataChange = new EventEmitter<any[]>();
    settings: Handsontable.GridSettings;

    ngOnInit() {
        this.preloadDataWithSettings = loadPrepopulationData(this.selectedDegreePlan, this.studentName, this.studentId);
        this.preloadData = seperateDataFromSettings(this.preloadDataWithSettings);

        // these settings are what actually allow the table to generate. Do not adjust these directly. Instead,
        // notice that some settings have function calls. Adjust how the data is generated in these functions
        this.settings = {
            width: '100%',
            height: 'auto',
            stretchH: 'all',
            cell: generateCells(this.preloadDataWithSettings),
            mergeCells: generateMergeCells(this.preloadDataWithSettings),
            customBorders: generateBorders(this.preloadDataWithSettings)
        };
    }

    ngOnChanges(changes: SimpleChanges) {
        this.preloadDataWithSettings = loadPrepopulationData(this.selectedDegreePlan, this.studentName, this.studentId);
        this.preloadData = seperateDataFromSettings(this.preloadDataWithSettings);
        this.settings.cell = generateCells(this.preloadDataWithSettings);
        this.preloadDataChange.emit(this.preloadData);
    }
}