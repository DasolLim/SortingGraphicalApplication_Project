
package se2203.assignment1;
import java.lang.Runnable;

public interface SortingStrategy extends Runnable{
    public void sort(int[] numbers);
    //override the run() method to tell the system what task to do
    public void run();
}
