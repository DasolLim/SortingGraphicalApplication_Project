package se2203.assignment1;

import javafx.application.Platform;

public class MergeSort extends SortingHubController implements SortingStrategy{

    //attributes
    //list used to store and sort random array
    private int[] list;
    //used to reference the controller class
    private SortingHubController controller;

    //constructor with parameter
    public MergeSort(int[] list, SortingHubController controller) {
        this.list = list;
        this.controller = controller;
    }

    //in-place merge sort to display step by step animation on the anchor pane
    public void sort(int[] array) {
        int x = array.length;
        int size;
        int left;
        for (size = 1; size <= x - 1; size = 2 * size) {
            for (left = 0; left < x - 1; left += 2 * size) {
                //divide and conquer
                int mid = Math.min(left + size - 1, x - 1);
                int right = Math.min(left + 2 * size - 1, x - 1);

                merge(array, left, mid, right);
                //allows it to run in the main thread
                Platform.runLater(()->{
                    controller.updateGraph(array);
                });
                try {
                    //delaying the thread to appear as animation
                    Thread.sleep(90);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void merge(int[] array, int start, int middle, int end) {
        //divide and conquer method
        //two int arrays 1 and 2
        //left is the first index and right is the last index
        int left1 = start;
        int right1 = middle;
        int left2 = middle + 1;
        int right2 = end;

        //sortedArray is used to store the sorted data
        int[] sortedArray = new int[end - start + 1];
        int i = 0;
        while ((left1 <= right1) && (left2 <= right2))
        {
            if (array[left1] <= array[left2]) {
                sortedArray[i] = array[left1];
                left1++;
            } else {
                sortedArray[i] = array[left2];
                left2++;
            }
            i++;
        }
        //copying the data into the temp array
        while (left1 <= right1) {
            sortedArray[i] = array[left1];
            left1++;
            i++;
        }
        while (left2 <= right2) {
            sortedArray[i] = array[left2];
            left2++;
            i++;
        }
        //populating the array into sortedArray with the sorted data
        for (int k = start, j = 0; k <= end; k++, j++)
            array[k] = sortedArray[j];
    }

    @Override
    public void run() {
        //take unsorted array of integers and return a sorted version of it
        sort(list);
    }
}