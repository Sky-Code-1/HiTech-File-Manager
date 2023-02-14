package org.hitech.hitechfilemanager;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    private FileBasicActions basicActions = new FileBasicActions();

    Map<ImageView, Label> foldersList = new HashMap<>();

    private HBox[] folderBox;

    @FXML
    private ImageView forwardDirectory, backwardDirectory, upDirectory;
    @FXML
    private VBox fileVbox;
    @FXML
    private Label expandFolder, directoryLabel, fileTypeLabel, pathLabel;

    @FXML
    private AnchorPane pathBarAnchorPane;

    private ArrayList<String> filePaths = new ArrayList<>();

    private ArrayList<File> directoryFiles = new ArrayList<File>();
    public ArrayList<Label> fileLabels = new ArrayList<>();
    @FXML
    private TextField pathTextField;

    private boolean isDirectory = true, isFileType;

    @FXML
    public void onMouseEntered(MouseEvent mouseEvent) {
        Node node = (Node)mouseEvent.getTarget();
        node.setStyle("-fx-background-color: #f2d1d1; -fx-border-color: blue");
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
        }
        else if (event.getTarget().equals(directoryLabel)) {
            isDirectory = true;
            isFileType = false;
            fileTypeLabel.setOpacity(1);
            directoryLabel.setOpacity(0.4);
        }
    }

    @FXML
    public void onHomeDirectoryClick(MouseEvent e) throws Exception{
        openFolder("C:\\");
        pathLabel.setText("C:\\");
        pathTextField.setText("C:\\");
    }

    public void onAnyLabelClick(MouseEvent e) throws Exception {
        String folderName;
        if (e.getTarget().toString().equalsIgnoreCase("Local disk"))
            folderName = "C:\\";
        else
            folderName = pathTextField.getText() + e.toString();
        filePaths.add(folderName);
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
        filePaths.add(path);
        directoryFiles = openFolder(path);
        for (File f : directoryFiles) {
            String fileName = f.getName();
            Label fileLabel = new Label(f.getName());
            fileVbox.getChildren().add(fileLabel);
            fileLabel.setOnMouseClicked(e -> {
                try {
                    String newPath = fileLabel.getText();
                    System.out.println(newPath);
                    fileLabels.clear();
                    String previousPath = pathTextField.getText().toString();
                    int lengthOfPath = previousPath.length();
                    if(previousPath.substring(lengthOfPath-1).equals("/"))
                        pathTextField.setText(pathTextField.getText() + "/" + newPath);
                    else
                        pathTextField.setText(pathTextField.getText() + "\\" + newPath);

                    openFolder(pathTextField.getText());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());;
                }
            });
        }
    }

    @FXML
    public ArrayList<File> openFolder(String path) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        double fileVboxWidth = fileVbox.getWidth();
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
                    fileLabel.setPrefWidth(fileVboxWidth-40);
                    fileLabel.setOnMouseExited(this::onMouseExit);
                    fileLabel.setOnMouseEntered(this::onMouseEntered);
                    fileLabel.setOnMouseClicked(e -> {
                        try {
                            if(e.getClickCount() == 2 && clickInterval(e) < 0.25){
                                String newPath = fileLabel.getText().toString();
                                fileLabels.clear();
                                newPath = pathTextField.getText() + "\\" + newPath;
                                pathTextField.setText(newPath);
                                filePaths.add(newPath);
                                openFolder(pathTextField.getText());
                            }
                            else if(clickInterval(e) > 0.25){

                                TextField textField = new TextField(fileLabel.getText());
                                textField.setOnAction(evt -> fileLabel.setText(textField.getText()));
                                textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                                    if (!newValue) {
                                        fileLabel.setText(textField.getText());
                                    }
                                });

                                fileVbox.getChildren().set(fileVbox.getChildren().indexOf(fileLabel), textField);
                                textField.requestFocus();
                            }

                        }
                        catch (Exception ex) {
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
//     public int isBackwardArrowClicked()
    @FXML
      public void onArrowClick(MouseEvent event){
        if(event.getTarget().equals(backwardDirectory)){
            System.out.println(filePaths.size());
            int size = filePaths.size();
            size -= 1;
//            openFolder()
        }
        else if(event.getTarget().equals(forwardDirectory)){

        }
        else{

        }
    }
    public int getClickCount(Event event){

        return 0;
    }

    public long clickInterval(MouseEvent event){
        long finalTime = 0;
        long startTime = System.currentTimeMillis();
        if(event.getClickCount() == 2)
            finalTime = (System.currentTimeMillis() - startTime)/1000L;
        return finalTime;
    }
    @FXML
    public void onMouseExit(MouseEvent event){
        Node node = (Node)event.getTarget();
        node.setStyle("-fx-background-color: white; -fx-border-color:white");
    }

}