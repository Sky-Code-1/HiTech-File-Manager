package org.hitech.hitechfilemanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.*;

public class HiTechFileManagerController {

   Map<ImageView, Label> foldersList = new HashMap<>();

    private HBox[] folderBox;
    @FXML
    private Label expandFolder,directoryLabel,fileTypeLabel,pathLabel;

    @FXML
    private AnchorPane pathBarAnchorPane;

    private LinkedList<String> filePaths = new LinkedList();
    @FXML
    private TextField pathTextField;

    private boolean isDirectory = true, isFileType;

    @FXML
    public void onMouseEntered(MouseEvent mouseEvent){

    }

    @FXML
    public void onExpandFolderClicked(){

    }
    @FXML
    public void changeFormat(MouseEvent event){
        if(event.getTarget().equals(fileTypeLabel)){
            isFileType = true;
            isDirectory = false;
            directoryLabel.setOpacity(1);
            fileTypeLabel.setOpacity(0.4);
        }
        else if(event.getTarget().equals(directoryLabel)) {
            isDirectory = true;
            isFileType = false;
            fileTypeLabel.setOpacity(1);
            directoryLabel.setOpacity(0.4);
        }
    }

    @FXML
    public void pathLabelAction(MouseEvent event){
        pathTextField.setText(pathLabel.getText());
        pathTextField.setVisible(true);
        pathLabel.setVisible(false);
    }

    @FXML
    public void pathTextFieldAction(MouseEvent event){
        if(event.getTarget() == pathTextField);
        else{
            pathTextField.setVisible(false);
            pathLabel.setVisible(true);
        }
    }
}