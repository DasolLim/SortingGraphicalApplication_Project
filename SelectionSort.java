package se2203.assignment1;

import javafx.application.Platform;

public class SelectionSort extends SortingHubController implements SortingStrategy{

    //attributes
    //list used to store and sort random array
    private int[] list;
    //used to reference the controller class
    private SortingHubController controller;

    //constructor with parameter
    public SelectionSort(int[] list, SortingHubController controller){
        this.list = list;
        this.controller = controller;
    }

    public void sort(int[] a){
        for (int i = 0; i < a.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < a.length; j++){
                if (a[j] < a[index]){
                    index = j;
                }
            }
            int smallerNumber = a[index];
            a[index] = a[i];
            a[i] = smallerNumber;

            //allows it to run in the main thread
            Platform.runLater(()->{
                controller.updateGraph(a);
            });
            try {
                //delaying the thread to appear as animation
                Thread.sleep(90);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        //take unsorted array of integers and return a sorted version of it
        sort(list);
    }
}
