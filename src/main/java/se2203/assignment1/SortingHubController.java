package se2203.assignment1;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.*;

public class SortingHubController implements Initializable {

    //size of the array
    public int size = 64;
    //needs to keep randomNum array static
    public static int[] intArray;
    //used for threading and calling sort method
    SortingStrategy sortingMethod;
    Thread thread = new Thread();

    @FXML
    Rectangle rectangle;
    @FXML
    Rectangle rect;
    @FXML
    private Label sliderNum;
    @FXML
    private Button sortBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Slider slider;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //setting the max and min value of the slider
        slider.setMax(128);
        slider.setMin(32);
        //setting slider initial value
        slider.setValue(64);

        //setting the initial value and size of the slider
        String stringF = String.format("%.0f", slider.getValue());
        sliderNum.setText(String.valueOf(stringF));

        //getting and setting combo box1
        comboBox.getItems().setAll("Merge Sort", "Selection Sort");
        //setting the combo box to have Merge Sort as initialization
        comboBox.getSelectionModel().selectFirst();

        //calling update Rectangle to display in anchor pane
        updateRectangle();
    }

    @FXML
    void ResetControl() {
        //resetting to array size 64
        thread.stop();

        //initialization
        slider.setValue(64);
        //getting and setting combo box
        comboBox.getItems().setAll("Merge Sort", "Selection Sort");
        //setting the combo box to have Merge Sort as initialization
        comboBox.getSelectionModel().selectFirst();

        //clearing
        anchorPane.getChildren().clear();
        //redraw rectangle
        updateRectangle();
    }

    @FXML
    public void setSortingStrategy() {
        //selection conditional statement for combo box
        if(comboBox.getSelectionModel().getSelectedItem() == "Merge Sort"){
            //this allows the current class to be accessed and use the method and variables
            sortingMethod = new MergeSort(intArray, this);
        }
        else if (comboBox.getSelectionModel().getSelectedItem() == "Selection Sort"){
            sortingMethod = new SelectionSort(intArray, this);
        }
    }

    @FXML
    public void sortFunction(){
        //sort function for the sort button
        setSortingStrategy();
        //creating single thread for sortingMethod
        thread = new Thread(sortingMethod);
        //sort button starts the thread to sort the given random array
        thread.start();
    }

    @FXML
    public void updateGraph(int[] data) {

        //width and height of anchor pane
        double width = anchorPane.getPrefWidth();
        double height = anchorPane.getPrefHeight();
        double widthConstant = width/intArray.length;
        double heightConstant = height/ intArray.length;
        //used to shift and add rectangle along the x on the anchor pane
        double x =0.0;

        for (int i=0;i<data.length;i++){
            //getting rectangle and setting it as rectangle
            rectangle = (Rectangle) anchorPane.getChildren().get(i);
            //setting the rectangle x and y
            rectangle.setX(x+1);
            rectangle.setY(height-(data[i]*heightConstant));
            //setting the width and height
            rectangle.setWidth(widthConstant-0.5);
            //height is justified since it starts from left right corner (0,0)
            rectangle.setHeight(data[i]*heightConstant);
            //shifting x to add another rectangle without overlap
            x+=(width/intArray.length);
        }
    }

    @FXML
    public void updateRectangle(){

        //stopping the thread to update and display rectangles
        thread.stop();

        //updating the slider value
        String stringF = String.format("%.0f", slider.getValue());
        sliderNum.setText(String.valueOf(stringF));

        //clearing and updating rectangle after slider is adjusted
        anchorPane.getChildren().clear();
        randomArray();

        double width = anchorPane.getPrefWidth();
        double height = anchorPane.getPrefHeight();
        double widthConstant = width/intArray.length;
        double heightConstant = height/ intArray.length;
        double x =0.0;

        //loop to create and update the rectangles corresponding to slider value
        for(int i =0; i<intArray.length; i++){
            rect = new Rectangle(x+1, height-(intArray[i]*heightConstant), widthConstant-0.5, intArray[i]*heightConstant);
            rect.setFill(Color.RED);
            anchorPane.getChildren().add(rect);
            //adding rect beside previous rect
            x+=widthConstant;
        }
    }

    //shuffling a given array
    public void shuffleArray(int[] array) {

        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;

        }
    }

    public void randomArray(){
        //getting the value of the slider and instantiating with variable size
        size = (int)(slider.getValue());
        //setting the size of the array as the slider value
        intArray = new int[size];

        //creating random number array starting from 1 to slider value
        for (int i=0; i<intArray.length; i++){
            intArray[i] = i+1;
        }
        //calling shuffleArray method
        shuffleArray(intArray);
    }


}
