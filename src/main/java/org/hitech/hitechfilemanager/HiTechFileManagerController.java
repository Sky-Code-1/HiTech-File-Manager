package org.hitech.hitechfilemanager;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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

    private boolean draggingFiles = false;
    Map<ImageView, Label> foldersList = new HashMap<>();

    private HBox[] folderBox;

    @FXML
    private ImageView forwardDirectory, backwardDirectory, upDirectory, searchFile;
    @FXML
    private VBox fileVbox;
    @FXML
    private Label expandFolder, directoryLabel, fileTypeLabel, pathLabel;

    @FXML
    private AnchorPane pathBarAnchorPane;

    private ArrayList<String> filePaths = new ArrayList<>();

    private int pathIndex, currentPathIndex;

    private ArrayList<File> directoryFiles = new ArrayList<File>();
    public ArrayList<Label> fileLabels = new ArrayList<>();
    public Label fileLabel = new Label();
    @FXML
    private TextField pathTextField, searchBar;

    private boolean isDirectory = true, isFileType;

    /**===================================================================|
     *     This Method is invoked at any random time in the program      ||
     *     it is an action event handler that indicates that the mouse   ||
     *     cursor has been placed into the target or prospective target  ||
     *     it shows a lightgray color to indicate that the mouse cursor  ||
     *     around it                                                     ||
     *===================================================================*/
    @FXML
    public void onMouseEntered(MouseEvent mouseEvent) {
        Node node = (Node)mouseEvent.getTarget();
        node.setStyle("-fx-background-color: lightgray");
    }

    /**===================================================================|
     *     This Method is invoked at any random time in the program      ||
     *     it is an action event handler that counts the amount of time a||
     *     particular target in the program is been clicked              ||
     *====================================================================*/
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
    //Todo comments is needed here
    public int getClickCount(Event event){

        return 0;
    }
    @FXML
    //Todo comments is needed here
    public void onPreviousDirectoryClick(){
        if(currentPathIndex == 0)
            return;
//        if(pathIndex > currentPathIndex){
//            filePaths.remove(pathIndex-1);
//        }
        System.out.println("Before Clicking " + currentPathIndex);
        currentPathIndex -= 1;
        System.out.println("Size of the paths array " + filePaths.size());
        System.out.println("After Clicking " + currentPathIndex);
        String path = filePaths.get(currentPathIndex);
        pathTextField.setText(path);
        try{
            navigateFolder(path);
            pathIndex = filePaths.size();
            System.out.println(path);
            forwardDirectory.setOpacity(1.0);
            forwardDirectory.setDisable(false);
        }
         catch (Exception e){
             System.out.println(e.getMessage());
         }
        if(currentPathIndex == 0) {
            backwardDirectory.setDisable(true);
            backwardDirectory.setOpacity(0.4);
            return;
        }
//        filePaths.remove(pathIndex-1);
    }
   //Todo comments is needed here
    @FXML
    public void onForwardDirectoryClick(){
        if(currentPathIndex == pathIndex-1)
            return;
        currentPathIndex += 1;
        System.out.println(filePaths.size());
        System.out.println("The current path index " + currentPathIndex);
        String path = filePaths.get(currentPathIndex);
        pathTextField.setText(path);
        try{
            navigateFolder(path);
            pathIndex = filePaths.size();
            System.out.println(path);
        }
         catch (Exception e){
             System.out.println(e.getMessage());
         }
        if(currentPathIndex == pathIndex-1) {
            System.out.println("inside second if statement");
            forwardDirectory.setDisable(true);
            forwardDirectory.setOpacity(0.4);
            return;
        }
//        filePaths.remove(pathIndex-1);
    }

    //Todo comments is needed here
    public long clickInterval(MouseEvent event){
        long finalTime = 0;
        long startTime = System.currentTimeMillis();
        if(event.getClickCount() == 2)
            finalTime = (System.currentTimeMillis() - startTime)/1000L;
        return finalTime;
    }

    /**===================================================================|
     *     This Method is invoked at any random time in the program      ||
     *     it is an action event handler that indicates that the mouse   ||
     *     cursor has been removed from the target or prospective target ||
     *====================================================================*/
    @FXML
    public void onMouseExit(MouseEvent event){
        Node node = (Node)event.getTarget();
        node.setStyle("-fx-background-color: white; -fx-border-color:white");
    }
    /**===================================================================|
     *     This Method is invoked at any random time in the program      ||
     *     it is an action event handler that indicates that the mouse   ||
     *     cursor has been removed from the target or prospective target ||
     *====================================================================*/
    @FXML
    public void onMouseDragged(MouseEvent event){
        if(!draggingFiles)
        {
            draggingFiles = true;
        }
        Node node = (Node)event.getTarget();
        node.setStyle("-fx-background-color: gray; -fx-border-color:white");
    }

    /**===================================================================|
     *     This Method is invoked at any random time in the program      ||
     *     it is an action event handler that indicates that the mouse   ||
     *     cursor has been used to click the up directory on the program ||
     *==================================================================*/
    @FXML
    public void moveUpOneDirectory(MouseEvent event) throws Exception{
        if(pathTextField.getText().equals("C:/")){
            System.out.println("done");
            return;
        }
        else{
            upDirectory.setDisable(false);
            upDirectory.setOpacity(1.00);
            String path = pathTextField.getText();
            String upDirectoryPath = "";
            int j = 0;
            int pathLength = path.length();
            System.out.println(path);
           try{
            upDirectoryForLoop:
                 for(int i = pathLength - 1; i > 0; i--){
                if(path.charAt(i) == '\\'){
                    j = i;
                    break;
                }
            }
        }
               catch (Exception e){
                   System.out.println(e.getMessage());
               }
            upDirectoryPath = path.substring(0,j);
            pathTextField.setText(upDirectoryPath);
            openFolder(upDirectoryPath);
        }
    }
    @FXML
    public void onImageViewClick(MouseEvent event){
        ImageView view = (ImageView)event.getTarget();
        view.setOpacity(0.4);
    }
    @FXML
    //Todo comments is needed here
    public void onExpandFolderClicked() {

    }

    @FXML
    //Todo comments is needed here
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
    //Todo comments is needed here
    public void onHomeDirectoryClick(MouseEvent e) throws Exception{
        if(e.getClickCount() == 2){
            pathIndex++;
            openFolder("C:\\");
            pathLabel.setText("C:\\");
            pathTextField.setText("C:\\");
        }
    }

    //Todo comments is needed here
    public void onAnyLabelClick(MouseEvent e) throws Exception {
        String folderName;
        if (e.getTarget().toString().equalsIgnoreCase("Local disk"))
            folderName = "C:\\";
        else
            folderName = pathTextField.getText() + e.toString();
        System.out.println(e.getTarget().toString());
        navigateFolder(folderName);
    }

    @FXML
    //Todo comments is needed here
    public void pathLabelAction(MouseEvent event) {
        pathTextField.setText(pathLabel.getText());
        pathTextField.setVisible(true);
        pathLabel.setVisible(false);
    }

    @FXML
    //Todo comments is needed here
    public void pathTextFieldAction(MouseEvent event) {
        if (event.getTarget() == pathTextField) {
            // I'll do nothing.
        } else {
            pathTextField.setVisible(false);
            pathLabel.setVisible(true);
        }
    }




/**===================================================================|
 *     This Method takes in a parameter of type string that          ||
 *   represents the absolute path directory of that particular       ||
 *   file or folder                                                  ||
*====================================================================*/
    @FXML
    public ArrayList<File> openFolder(String path) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        fileVbox.setSpacing(10);
        double fileVboxWidth = fileVbox.getWidth();
            if (currentPathIndex > 0){
                backwardDirectory.setOpacity(1.0);
                backwardDirectory.setDisable(false);
            }
            else {
                backwardDirectory.setOpacity(0.4);
                backwardDirectory.setDisable(true);
            }
        if(pathTextField.getText().equalsIgnoreCase("C:\\")) {
            upDirectory.setOpacity(0.4);
            upDirectory.setDisable(true);
        }

        else{
            upDirectory.setOpacity(1.00);
            upDirectory.setDisable(false);
        }
        try {
            fileVbox.getChildren().clear();
            File[] listedFiles;
            File fileInPath = new File(path);

            if (fileInPath.isDirectory()) {
                filePaths.add(path);
                listedFiles = fileInPath.listFiles();
                for (File f : listedFiles) {
                    Label fileLabel = new Label(f.getName());
                    files.add(f);
                    fileVbox.getChildren().add(fileLabel);
                    fileLabel.setStyle("-fx-background-color: white");
                    fileLabel.setPrefWidth(fileVboxWidth-80);
                    fileLabel.setOnMouseExited(this::onMouseExit);
                    fileLabel.setOnMouseEntered(this::onMouseEntered);
                    fileLabel.setOnMouseDragged(this::onMouseDragged);
                    fileLabel.setOnMouseDragEntered(this::onMouseEntered);
                    fileLabel.setOnMouseClicked(e -> {
                        try {
                            if(e.getClickCount() == 2 && clickInterval(e) < 0.25){
                                String newPath = fileLabel.getText().toString();
                                fileLabels.clear();
                                searchBar.setPromptText("Search in " + newPath);
                                newPath = pathTextField.getText() + "\\" + newPath;
                                File file = new File(newPath);
                                if(!file.isDirectory()){
                                    Alert alert = new Alert(Alert.AlertType.ERROR,"Can't open now because, File is not a directory");
                                    alert.show();
                                    return;
                                }

                                pathTextField.setText(newPath);
                                currentPathIndex += 1;
                                pathIndex += 1;
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
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Can't open now because, File is not a directory");
                alert.show();
                return null;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Length of accessed paths " + filePaths.size());
        System.out.println("Current path index " + pathIndex);
       return files;
    }

    //Todo comments is needed here
    public ArrayList<File> navigateFolder(String path) throws IOException {
        ArrayList<File> files = new ArrayList<>();
        fileVbox.setSpacing(10);
        double fileVboxWidth = fileVbox.getWidth();
            if (currentPathIndex > 0){
                backwardDirectory.setOpacity(1.0);
                backwardDirectory.setDisable(false);
            }
            else {
                backwardDirectory.setOpacity(0.4);
                backwardDirectory.setDisable(true);
            }
        if(pathTextField.getText().equalsIgnoreCase("C:\\")) {
            upDirectory.setOpacity(0.4);
            upDirectory.setDisable(true);
        }

        else{
            upDirectory.setOpacity(1.00);
            upDirectory.setDisable(false);
        }
        try {
            fileVbox.getChildren().clear();
            File[] listedFiles;
            File fileInPath = new File(path);

            if (fileInPath.isDirectory()) {
                listedFiles = fileInPath.listFiles();
                for (File f : listedFiles) {
                    Label fileLabel = new Label(f.getName());
                    files.add(f);
                    fileVbox.getChildren().add(fileLabel);
                    fileLabel.setStyle("-fx-background-color: white");
                    fileLabel.setPrefWidth(fileVboxWidth-80);
                    fileLabel.setOnMouseExited(this::onMouseExit);
                    fileLabel.setOnMouseEntered(this::onMouseEntered);
                    fileLabel.setOnMouseDragged(this::onMouseDragged);
                    fileLabel.setOnMouseDragEntered(this::onMouseEntered);
                    fileLabel.setOnMouseClicked(e -> {
                        try {
                            if(e.getClickCount() == 2 && clickInterval(e) < 0.25){
                                String newPath = fileLabel.getText().toString();
                                fileLabels.clear();
                                searchBar.setPromptText("Search in " + newPath);
                                newPath = pathTextField.getText() + "\\" + newPath;
                                File file = new File(newPath);
                                if(!file.isDirectory()){
                                    Alert alert = new Alert(Alert.AlertType.ERROR,"Can't open now because, File is not a directory");
                                    alert.show();
                                    return;
                                }
                                pathTextField.setText(newPath);
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
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Can't open now because, File is not a directory");
                alert.show();
                return null;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Length of accessed paths " + filePaths.size());
        System.out.println("Current path index " + pathIndex);
       return files;
    }
    //Todo comments is needed here
      public void onMouseClicked(MouseEvent event) throws Exception{
        Node node = (Node)event.getTarget();
        Label label = (Label)node;
        openFolder(label.getText());
    }
     public void onLabelMouseClicked(MouseEvent event, String path) throws Exception{
         if(event.getClickCount() == 2){
            path = pathTextField.getText() + "\\" + path;
            openFolder(path);
         }
     }
}
