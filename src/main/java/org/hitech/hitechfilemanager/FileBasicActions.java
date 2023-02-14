package org.hitech.hitechfilemanager;

import java.io.*;
import java.util.Scanner;

public class FileBasicActions {

    public void copy(File sourceFile, File destinationFile) throws Exception{
        Scanner fileReader = new Scanner(new FileReader(sourceFile));
        PrintWriter fileWriter = new PrintWriter(new FileWriter(destinationFile));
        do {
             fileWriter.println(fileReader.next());
        }
        while(fileReader.hasNext());
    }

    public void duplicate(File sourceFile){

    }


    public void move(File sourceFile, File destinationFile) throws Exception{
     copy(sourceFile,destinationFile);
     sourceFile.delete();
    }

    public void deleteFiles(File... fileToBeDeleted){

    }

    public void openFile(File file){

    }

    public void cutFiles(){

    }
}
