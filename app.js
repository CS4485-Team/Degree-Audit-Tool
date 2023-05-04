const {app, BrowserWindow, ipcMain, ipcRenderer} = require('electron')
const url = require("url");
const path = require("path");
const { spawn } = require('node:child_process');
const jetpack = require("fs-jetpack");

let mainWindow

function createWindow () {

  // clear input and output directories to prepare for new file saves
  const fs = require("fs");
  const path = require("path");

  const directory = ["./src/input", "./src/output"];

  directory.forEach(directory => {
    fs.readdir(directory, (err, files) => {
      if (err) throw err;
    
        for (const file of files) {
          fs.unlink(path.join(directory, file), (err) => {
            if (err) throw err;
          });
        }
      });
  })

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
  const process = spawn("java", ['-jar', './Degree-Audit-Tool.jar', degreePlanType]);

  process.stdout.on('data', (data) => {
    console.log(`stdout: ${data}`);
  });

  process.stderr.on('data', (err) => {
    console.log(`stderr: ${err}`);
  });
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

  jetpack.write('./src/input/input.csv', outputData);
});