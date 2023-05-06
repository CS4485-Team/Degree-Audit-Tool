import { Component, Input, Output, EventEmitter } from '@angular/core';
import { toWords } from 'number-to-words';
import auditReportConfigs from 'auditReportConfig.json';
import Handsontable from 'handsontable';

/*  loadPrepopulationData
    Fills the table seen on the degreePlan page with the information entered by the user.
    If the user input a prepopulation file (either a transcript PDF file or a student object file),
    the table will reflect the information from those files. Anytime information is changed, this function is
    recalled to reload the table and reflect the changes made. 
    The table is built using rows of CSV. Each row is built and individually pushed to the data list. My custom
    implementation was to assign each row a 'type' and a 'data' field. The 'type' field is used to format the row.
    Several functions exist lower in this file that apply a specific style to a row depending on what this type is.
    The 'data' field of the row contains the actual data to fill the table with. Each 'data' field of each row MUST be
    the same length. In this case, each row is hardcoded to be 5 elements long. Any modification to the width of the table
    will need to be reflected in each individual row. The only rows that do not need to be the same length are rows that will
    be merged into a single row. These rows in my custom implementation are called 'header' rows.

    :param: selectedDegreePlan (string) -> the degree plan selected on the degreePlan page
    :param: selectedMajor (string) -> the major selected on the degreePlan page
    :param: studentName (string) -> the student name input on the degreePlan page
    :param: studentId (string) -> the student id input on the degreePlan page
    :param: admitSem (string) -> the semester admitted input on the degreePlan page
    :param: gradSem (string) -> the anticipated graduation semester input on the degreePlan page
    :param: electives (any[]) -> list of selected electives input on the degreePlan page
    :param: addElectives (any[]) -> list of additional electives input on the degreePlan page
    :param: importedClassData (any[]) -> list of class data inputted from the prepopulation data file (this may be empty if
        the user did not input one of these files and started from scratch)
    :returns: data (any[]) the data to fill the table
*/
const loadPrepopulationData = (selectedDegreePlan: string, selectedMajor: string, studentName: string, studentId: string, 
    admitSem: string, gradSem: string, electives: any[], addElectives: any[], importedClassData: any[]) : any[] => {
    let data: any[] = [];

    data.push({'type': 'mainHeader', 'data': ['DEGREE PLAN', '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': ['UNIVERSITY OF TEXAS AT DALLAS', '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': [`MASTER OF ${selectedMajor.toUpperCase()}`, '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': ['', '', '', '', '']});
    data.push({'type': 'mainHeader', 'data': [selectedDegreePlan, '', '', '', '']});
    data.push({'type': 'binaryInput', 'data': ['FT:', '', '', '', '']});
    data.push({'type': 'binaryInput', 'data': ['Thesis:', '', '', '', '']});
    data.push({'type': 'input', 'data': [`Name: ${studentName}`, '', '', '', '']});
    data.push({'type': 'input', 'data': [`ID: ${studentId}`, '', '', '', '']});
    data.push({'type': 'input', 'data': [`Semester Admitted to Program: ${admitSem}`, '', '', `Graduation: ${gradSem}`, '']});
    data.push({'type': 'input', 'data': ['Course Title', 'Course Number', 'UTD Semester', 'Transfer', 'Grade']});

    const courseList: string = JSON.stringify(auditReportConfigs);
    const configs: any = new Map(Object.entries(JSON.parse(courseList)));

    // push all core courses for this degree plan
    data.push({'type': 'header', 'data': ['CORE COURSES     (15 CREDIT HOURS)     3.19 Grade Point Average Required']});
    let courses: any = configs.get('coreCourseList');
    courses = courses[selectedDegreePlan];
    for (let i = 0; i < configs.get("outputCourseInfo")[selectedDegreePlan]["numCoreCourses"]; ++i) {
        if (i < courses.length)
            data.push({'type': 'input', 'data': [courses[i].name, courses[i].number, '', '', '']});
        else
            data.push({'type': 'input', 'data': ['','','','','']});
    }

    // push all additional core course work for this degree plan
    let additionalCourses: any = configs.get('additionalCoreCourseList');
    additionalCourses = additionalCourses[selectedDegreePlan];
    data.push({'type': 'header', 'data': ['One of the following Courses']});
    for (let i = 0; i < configs.get("outputCourseInfo")[selectedDegreePlan]["numAdditionalCoreCourses"]; ++i) {
        if (i < additionalCourses.length)
            data.push({'type': 'input', 'data': [additionalCourses[i].name, additionalCourses[i].number, '', '', '']});
        else
            data.push({'type': 'input', 'data': ['','','','','']});
    }

    let electivesCount: number = configs.get("outputCourseInfo")[selectedDegreePlan]["numElectiveCourses"];
    data.push({'type': 'header', 'data': [`${toWords(electivesCount).toUpperCase()} APPROVED 6000 LEVEL ELECTIVES     (15 * Credit Hours)     3.0 Grade Point Average`]});
    for (let i = 0; i < electivesCount; ++i) {
        if (i < electives.length) {
            data.push({'type': 'input', 'data': [electives[i].name, electives[i].number, '', '', '']});
        }
        else {
            data.push({'type': 'input', 'data': ['', '', '', '', '']});
        }
    }

    data.push({'type': 'header', 'data': ['Additional Electives (3 Credit Hours Minimum)']});
    for (let i = 0; i < configs.get("outputCourseInfo")[selectedDegreePlan]["numAdditionalElectiveCourses"]; ++i) {
        if (i < addElectives.length) {
            data.push({'type': 'input', 'data': [addElectives[i].name, addElectives[i].number, '', '', '']});
        }
        else {
            data.push({'type': 'input', 'data': ['', '', '', '', '']});
        }
    }

    // push other requirements
    data.push({'type': 'header', 'data': ['Other Requirements']})
    for (let i = 0; i < configs.get("outputCourseInfo")[selectedDegreePlan]["numOtherRequirements"]; ++i) {
        data.push({'type': 'input', 'data': ['','','','','']});
    }

    // push admission prereqs
    let prereqCourses: any = configs.get('prereqCourseList');
    prereqCourses = prereqCourses[selectedDegreePlan];
    data.push({'type': 'header', 'data': ['Admission Prerequisites']})
    for (let i = 0; i < configs.get("outputCourseInfo")[selectedDegreePlan]["numAdmissionPrereqs"]; ++i) {
        if (i < prereqCourses.length)
            data.push({'type': 'input', 'data': [prereqCourses[i].name, prereqCourses[i].number,'','','']});
        else
            data.push({'type': 'input', 'data': ['', '', '', '', '']});
    }

    // iterate over all data again and check to see if any of the data also exists in the imported class data
    for (let i = 0; i < data.length; ++i) {
        for (let j = 0; j < importedClassData.length; ++j) {
            if (data[i]['data'][1] == importedClassData[j][2]) {
                // found a class that was imported
                data[i]['data'][2] = importedClassData[j][11];
                data[i]['data'][4] = importedClassData[j][9];
                data[i]['data'][3] = importedClassData[j][7];
            }
        }
    }

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

@Component({
    selector: 'degreePlan-editor',
    template: `
    <hot-table class="hot-table" id="degree-plan-table"
        [settings]="settings"
        [data]="preloadData"
        [colHeaders]="false"
        [rowHeaders]="false"
        [cell]="cells"
        [mergeCells]="mergeCells"
        [customBorders]="borders"
        licenseKey="non-commercial-and-evaluation">
    </hot-table>
    `,
    styleUrls: ['./degreePlanEditor.component.css']
})

export class DegreePlanEditorComponent {
    // input taken from the auditPage. Must preset to some value, otherwise the table will not be able
    // to render first time
    @Input() selectedDegreePlan: string = '';
    @Input() selectedMajor: string = '';
    @Input() studentName: string = '';
    @Input() studentId: string = '';
    @Input() admitSem: string = '';
    @Input() gradSem: string = '';
    @Input() electives: any[] = [];
    @Input() addElectives: any[] = [];
    @Input() importedClassData: any[] = [];

    preloadDataWithSettings: any[];
    preloadData: any[];
    @Output() preloadDataChange = new EventEmitter<any[]>();
    settings: Handsontable.GridSettings;

    cells: any[] = [];
    borders: any[] = [];
    mergeCells: any[] = [];

    ngOnInit() {
        this.preloadDataWithSettings = loadPrepopulationData(this.selectedDegreePlan, this.selectedMajor, 
            this.studentName, this.studentId, this.admitSem, this.gradSem, this.electives, this.addElectives,
            this.importedClassData);
        this.preloadData = seperateDataFromSettings(this.preloadDataWithSettings);

        // these settings are what actually allow the table to generate. Do not adjust these directly. Instead,
        // notice that some settings have function calls. Adjust how the data is generated in these functions
        this.settings = {
            width: '100%',
            height: 'auto',
            stretchH: 'all'
        };
    }

    ngOnChanges() {
        this.preloadDataWithSettings = loadPrepopulationData(this.selectedDegreePlan, this.selectedMajor,
            this.studentName, this.studentId, this.admitSem, this.gradSem, this.electives, this.addElectives,
            this.importedClassData);
        this.generateCells(this.preloadDataWithSettings);
        this.generateMergeCells(this.preloadDataWithSettings);
        this.generateBorders(this.preloadDataWithSettings);
        this.preloadData = seperateDataFromSettings(this.preloadDataWithSettings);
        this.preloadDataChange.emit(this.preloadData);
    }

    generateCells = (dataWithSettings: any[]) => {
        let cells: any[] = [];
    
        for (let i = 0; i < dataWithSettings.length; ++i) {
            if (dataWithSettings[i].type == 'header') {
                cells.push({ row: i, col: 0, className: 'htCenter', renderer: this.header});
            }
            else if (dataWithSettings[i].type == 'mainHeader') {
                cells.push({ row: i, col: 0, className: 'htCenter', renderer: this.mainHeader });
            }
            else if (dataWithSettings[i].type == 'binaryInput') {
                cells.push({ row: i, col: 1, className: 'htCenter', type: 'checkbox', label: { position: 'before', value: 'Y ' }});
                cells.push({ row: i, col: 2, className: 'htCenter', type: 'checkbox', label: { position: 'before', value: 'N ' }});
            }
            else {
                for (let j = 0; j < dataWithSettings[i].data.length; ++j) {
                    cells.push({ row: i, col: j, renderer: this.input });
                }
            }
        }
    
        this.cells = cells;
    }

    generateBorders = (dataWithSettings: any[]) => {
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
    
        this.borders = borders;
    }

    generateMergeCells = (dataWithSettings: any[]) => {
        let mergeCells: any[] = [];
    
        for (let i = 0; i < dataWithSettings.length; ++i) {
            if (dataWithSettings[i].type == 'header' || dataWithSettings[i].type == 'mainHeader') {
                mergeCells.push({ row: i, col: 0, rowspan: 1, colspan: 5 });
            }
        }
    
        this.mergeCells = mergeCells;
    }

    mainHeader = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
        Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
        td.style.fontWeight = 'bold';
        td.style.borderBottom = '#000'
    }
    
    header = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
        Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
        td.style.background = '#fabf8f';
        td.style.fontWeight = 'bold';
    }
    
    input = (instance: any, td: any, row: any, col: any, prop: any, value: any, cellProperties: any) : void => {
        Handsontable.renderers.TextRenderer.apply(this, [instance, td, row, col, prop, value, cellProperties]);
        td.style.border = '1px solid black'
    }
}