package org.hitech.hitechfilemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HiTechFileManagerController {

    Map<ImageView, Label> foldersList = new HashMap<>();

    private HBox[] folderBox;

    @FXML
    private VBox fileVbox;
    @FXML
    private Label expandFolder, directoryLabel, fileTypeLabel, pathLabel;

    @FXML
    private AnchorPane pathBarAnchorPane;

    private LinkedList<String> filePaths = new LinkedList();

    private ArrayList<File> directoryFiles = new ArrayList<File>();
    public ArrayList<Label> fileLabels = new ArrayList<>();
    @FXML
    private TextField pathTextField;

    private boolean isDirectory = true, isFileType;

    @FXML
    public void onMouseEntered(MouseEvent mouseEvent) {

    }

    @FXML
    public void onExpandFolderClicked() {

    }

    @FXML
    public void changeFormat(MouseEvent event) {
        if (event.getTarget().equals(fileTypeLabel)) {
            isFileType = true;
            isDirectory = false;
            directoryLabel.setOpacity(1);
            fileTypeLabel.setOpacity(0.4);
        } else if (event.getTarget().equals(directoryLabel)) {
            isDirectory = true;
            isFileType = false;
            fileTypeLabel.setOpacity(1);
            directoryLabel.setOpacity(0.4);
        }
    }

    @FXML
    public void onHomeDirectoryClick(MouseEvent e) throws Exception{
        openFolder("C:/");
    }

    public void onAnyLabelClick(MouseEvent e) throws Exception {
        String folderName;
        if (e.getTarget().toString().equalsIgnoreCase("local disk"))
            folderName = "C:/";
        else folderName = pathTextField.getText() + e.toString();
        System.out.println(e.getTarget().toString());
        showFiles(folderName);
    }

    @FXML
    public void pathLabelAction(MouseEvent event) {
        pathTextField.setText(pathLabel.getText());
        pathTextField.setVisible(true);
        pathLabel.setVisible(false);
    }

    @FXML
    public void pathTextFieldAction(MouseEvent event) {
        if (event.getTarget() == pathTextField) {
            // I'll do nothing.
        } else {
            pathTextField.setVisible(false);
            pathLabel.setVisible(true);
        }
    }

    public void showFiles(String path) throws Exception {
        fileVbox.getChildren().clear();
        directoryFiles.clear();
        directoryFiles = openFolder(path);
        for (File f : directoryFiles) {
            String fileName = f.getName();
            Label fileLabel = new Label(f.getName());
            fileVbox.getChildren().add(fileLabel);
            fileLabel.setOnMouseClicked(e -> {
                try {
                    String newPath = fileLabel.getText().toString();
                    fileLabels.clear();
                    pathTextField.setText(pathTextField.getText() + "\\" + newPath);
                    openFolder(pathTextField.getText());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    @FXML
    public ArrayList<File> openFolder(String path) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        try {
            fileVbox.getChildren().clear();
            File[] listedFiles;
            File fileInPath = new File(path);
            long sizeOfFolder = fileInPath.length();
            listedFiles = fileInPath.listFiles();

            if (fileInPath.isDirectory()) {
                for (File f : listedFiles) {
                    Label fileLabel = new Label(f.getName());
                    files.add(f);
                    fileVbox.getChildren().add(fileLabel);
                    fileLabel.setOnMouseClicked(e -> {
                        try {
                            String newPath = fileLabel.getText().toString();
                            fileLabels.clear();
                            pathTextField.setText(pathTextField.getText() + "\\" + newPath);
                            openFolder(pathTextField.getText());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    fileLabels.add(fileLabel);
//                    folderSize.setText("Total size   : " + sizeOfFolder / 1000 + "MB");
                }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

       return files;
    }
}