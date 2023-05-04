const {app, BrowserWindow, ipcMain, ipcRenderer} = require('electron')
const url = require("url");
const path = require("path");
const { exec, spawn } = require('node:child_process');

let mainWindow

function createWindow () {
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

ipcMain.on('close', () => {
  mainWindow.hide();
})

ipcMain.on('test', () => {
  console.log("test working");
})