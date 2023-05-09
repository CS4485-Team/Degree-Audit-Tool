const {app, BrowserWindow, ipcMain, ipcRenderer} = require('electron')
const url = require("url");
const path = require("path");
const https = require('https');
const { spawn } = require('node:child_process');
const jetpack = require("fs-jetpack");
const fs = require('fs');
const Alert = require("electron-alert");

alert = new Alert();

let mainWindow

function clearDirs () {
  // clear input and output directories to prepare for new file saves
  const directory = ["./src/input", "./src/output", "./src/transcriptInput", "./src/transcriptOutput"];

  try {
    directory.forEach(directory => {
      if (!fs.existsSync(directory)) {
        fs.mkdirSync(directory, { recursive: true });
      }

      fs.readdir(directory, (err, files) => {
        if (err) {
          console.log(err);
        };
      
          for (const file of files) {
            fs.unlink(path.join(directory, file), (err) => {
              if (err) {
                console.log(err);
              }
            });
          }
        });
    })
  }
  catch (err) {
    console.log(err);
  }

  try {
    fs.readdir('.', (err, files) => {
      if (err) {
        console.log(err);
      };
          fs.unlink(path.join('.', 'Test.csv'), (err) => {
            if (err) {
              console.log(err);
            }
          });
      });
  }
  catch (err) {
    console.log(err);
  }
}

function createWindow () {
  clearDirs();

  mainWindow = new BrowserWindow({
    webPreferences: {
      contextIsolation: false,
      nodeIntegration: true
    }
  });
  mainWindow.maximize();

  mainWindow.loadURL(
    url.format({
      pathname: path.join(__dirname, `/dist/Degree-Audit-Tool/index.html`),
      protocol: "file:",
      slashes: true
    })
  );

  mainWindow.on('closed', function () {
    mainWindow = null
  })
}

app.on('ready', createWindow)

app.on('window-all-closed', function () {
  if (process.platform !== 'darwin') app.quit()
})

app.on('activate', function () {
  if (mainWindow === null) createWindow()
})


// call the degree audit jar file to compile the submitted data into a
//  viewable pdf file
ipcMain.on('generateDegreePlanPDF', (event, degreePlanType) => {
  try {
    const process = spawn("java", ['-jar', './Degree-Audit-Tool.jar', degreePlanType]);

    process.stdout.on('data', (data) => {
      console.log(`stdout: ${data}`);
    });

    process.stderr.on('data', (err) => {
      console.log(`stderr: ${err}`);
    });
  }
  catch (err) {
    alert("Error in generating Degree Plan PDF. Please try again.")
  }
});

ipcMain.on('saveCSVFile', (event, csvData) => {

  let outputData = "";
  for (let i = 0; i < csvData.length; i++) {
    if (csvData[i] != '\n') {
      for (let j = 0; j < csvData[i].length - 1; j++) {
        outputData += csvData[i][j] + ','
      }
      outputData += csvData[i][csvData[i].length - 1];
    }
    else {
      outputData += '\n';
    }
  }

  try {
    jetpack.write('./src/input/input.csv', outputData);
  }
  catch (err) {
    console.log(err);
  }
});

ipcMain.on("copyFile", (event, fileToCopy) => {
  try {
    jetpack.copy(fileToCopy, "./src/transcriptInput/input.pdf");
  }
  catch (err) {
    console.log(err);
  }
});

ipcMain.on("copyStudentObject", (event, fileToCopy) => {
  try {
    jetpack.copy(fileToCopy, './Test.csv');
  }
  catch (err) {
    console.log(err);
  }
})

ipcMain.on('parseTranscript', (event) => {
  try {
    const process = spawn("java", ['-jar', './Degree2.jar', "./src/transcriptInput/input.pdf"]);

    process.stdout.on('data', (data) => {
      console.log(`stdout: ${data}`);
    });

    process.stderr.on('data', (err) => {
      console.log(`stderr: ${err}`);
    });
  }
  catch (err) {
    console.log(err);
  }
});

ipcMain.on('clearDirs', (event) => {
  clearDirs();
})

ipcMain.on('saveDegreePlanAndAudit', (event, destination) => {
  try {
    jetpack.copy('./src/output/DegreePlan.pdf', destination + `/DegreePlan.pdf`);
    jetpack.copy('./src/output/AudRep.pdf', destination + `/AuditReport.pdf`);
    console.log("Files saved to destination " + destination);

    let swalOptions = {
      title: "Files Successfully Saved",
      text: "You may now continue",
      icon: "check",
      showCancelButton: true
    };

    let promise = alert.fireWithFrame(swalOptions, "Success Confirmation", null, false);
    promise.then((result) => {
      if (result.value) {
        // confirmed
      } else if (result.dismiss === Alert.DismissReason.cancel) {
        // canceled
      }
    })
  }
  catch (err) {
    let swalOptions = {
      title: "Files unable to Save",
      text: "Please check your directory and try again.",
      icon: "error",
      showCancelButton: true
    };

    let promise = alert.fireWithFrame(swalOptions, "Failure Confirmation", null, false);
    promise.then((result) => {
      if (result.value) {
        // confirmed
      } else if (result.dismiss === Alert.DismissReason.cancel) {
        // canceled
      }
    })
  }
});