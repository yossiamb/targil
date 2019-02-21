package com.company.thread;

import com.company.utils.FileUtils;

import java.io.File;

public class FileReaderRunnable implements Runnable {

    private File fileName;

    public FileReaderRunnable(File fileName){
        this.fileName = fileName;
    }

    public void run() {
        FileUtils.getFiles(fileName);
    }
}
