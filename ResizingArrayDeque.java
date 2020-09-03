/**
* Implementation of Deque using resizing array
* @author  Aditya Raghunath Sawant
* @version 1.0
* @since   08-31-2020 
*/
import java.util.*;


public class ResizingArrayDeque<Item> implements Iterable<Item>{

    // Declaration of class variables
    private Item[] array;
    private int size;
    private int first;
    private int last;

    /**
     * Constructor to set default values
     * Supress general type cast warnings of Object array to generic array
     */
    @SuppressWarnings("unchecked")
    public ResizingArrayDeque() {
        array = (Item[]) new Object[1];
        size = 0;
        first = -1;
        last = -1;
    }

    /**
     * Function to check if ResizingArrayDeque is empty
     * @return boolean of size equals 0
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Function to get size of ResizingArrayDeque
     * @return the size of ResizingArrayDeque
     */
    public int size() {
        return size;
    }

    /**
    * Function to add new item to the left i.e. start of the deque
    * This function behaves like push of stack as it adds element at the front of deque
    * @param item to be added to the deque
    */
    public void pushLeft(Item item) {
        // Check if size is equal to array length
        if (size == array.length) 
        {
            //Resize the array to twice its size
            resize(size * 2);
        }

        // Check if first is 0
        if (first == 0) 
        {
            // Shift the current element to right 
            moveArrayRight();
        }

        if (isEmpty()) 
        {
            //If array is empty, set the new item as first item
            first = 0;
            last = 0;
            array[first] = item;
        } 
        else 
        {
            // If array is not empty, set first position of array to new item
            array[first - 1] = item;
            first = first - 1;
        }

        //Increase size
        size++;
    }

    /**
    * Function to add new item to the right i.e. end of the deque
    * This function behaves like enqueue of queue as it adds element at the end of deque
    * @param item to be added to the deque
    */
    public void pushRight(Item item) {
        // Check if size is equal to array length
        if (size == array.length) 
        {
            //Resize the array to twice its size
            resize(size * 2);
        }

        if (last == array.length - 1) 
        {
            // Shift the current element to left
            moveArrayLeft();
        }

        if (isEmpty()) 
        {
            //If array is empty, set the new item as first item 
            first = 0;
            last = 0;
            array[last] = item;
        } 
        else 
        {
            // Add new item at the end of the array
            array[last + 1] = item;
            last = last + 1;
        }

        //Increase size
        size++;
    }

    /**
    * Function to remove item from the left i.e. start of the deque
    * This function behaves like pop of stack as it removes element from the start of deque
    * @return item popped from left of deque
    */
    public Item popLeft() {
        //Check if deque is empty
        if (isEmpty()) 
        {
            //Throw exception if deque is empty
            throw new RuntimeException("Deque underflow");
        }

        //Get first item from deque
        Item item = array[first];
        //Set current first item to its next item
        array[first] = null; 

        //Decrease size of deque
        size--;

        if (isEmpty()) 
        {
            //If deque is empty, set first and last to -1
            first = -1;
            last = -1;
        } 
        else 
        {
            //If deque is not empty, set index of first element to first+1
            first = first + 1;
        }

        //Check if size is one/fourth of array
        if (size == array.length / 4) 
        {
            //Resize array
            resize(array.length / 2);
        }

        //Return the item
        return item;
    }


    /**
    * Function to remove item from the right i.e. end of the deque
    * @return item popped from right of deque
    */
    public Item popRight() {
        //Check if deque is empty
        if (isEmpty()) {
            //Throw exception if deque is empty
            throw new RuntimeException("Deque underflow");
        }

        //Get last item from deque
        Item item = array[last];
        //Set current first item to its next item
        array[last] = null;

        //Decrease size
        size--;

        if (isEmpty()) 
        {
            //If deque is empty, set first and last indices to -1
            first = -1;
            last = -1;
        } 
        else 
        {
            //If deque is not empty, set last index to previous index i.e. last - 1
            last = last - 1;
        }

        //Check if size is one/fourth of array
        if (size == array.length / 4) 
        {
            //Resize array
            resize(array.length / 2);
        }

        //Return the item
        return item;
    }

    /**
     * This function increases the capacity of Deque to double
     * It performs deep copy of all items in the bag to the new bag
     * Supress general type cast warnings of Object array to generic array
     * @param newSize that would be the size of new array
     */
    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];

        int j = 0;
        //Deep copy element in new array
        for(int i = first; i <= last; i++) {
            newArray[j] = array[i];
            j++;
        }

        //Set first to 0 index
        first = 0;
        //Set last to last index
        last = size-1;

        //Point the newArray to array object
        array = newArray;
    }

    /**
     * Function to shift every element in array to right
     */
    private void moveArrayRight() {
        //Copy ith element at i+1th position
        for (int i = last; i >= first; i--) 
        {
            array[i + 1] = array[i];
        }
        //Increase first and last index
        first++;
        last++;
    }

    /**
     * Function to shift every element in array to left
     */
    private void moveArrayLeft() {
        //Copy ith element at i-1th position
        for (int i = first; i <= last; i++) 
        {
            array[i - 1] = array[i];
        }
        //Decrease first and last index
        first--;
        last--;
    }

    /**
     * @return object of Iterator
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        int index = first;

        /**
         * function to check if next element exists in the Deque
         * @return boolean of index less than size
         */
        @Override
        public boolean hasNext() {
            return index <= last;
        }

        /**
         * Override next function that will return the next item in deque
         * @return item in the steque
         */
        @Override
        public Item next() {
            Item item = array[index];
            index++;
            return item;
        }
    }


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        ResizingArrayDeque<String> deque = new ResizingArrayDeque<>();

        deque.testPushLeft();
        System.out.println("-----------");
        deque.testPushRight();
        System.out.println("-----------");
        deque.testPopLeft();
        System.out.println("-----------");
        deque.testPopRight();
        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Time Elapsed is:"+(elapsedTime/1000000)+"ms");
    }

    private void testPushLeft() {
        System.out.println("Testing Push Left operation ");

        ResizingArrayDeque<String> deque1 = new ResizingArrayDeque<>();
        deque1.pushLeft("X");
        deque1.pushLeft("Y");
        deque1.pushLeft("Z");

        System.out.print("Deque items generated : ");

        Iterator itr1 = deque1.iterator();
        while(itr1.hasNext())
        {
            System.out.print(itr1.next().toString()+" ");
        }

        System.out.println();
        System.out.println("Expected: Z Y X");
    }

    private void testPushRight() {
        System.out.println("Testing Push Right operation ");

        ResizingArrayDeque<String> deque2 = new ResizingArrayDeque<>();
        deque2.pushRight("X");
        deque2.pushRight("Y");
        deque2.pushRight("Z");

        System.out.print("Deque items generated : ");

        Iterator itr1 = deque2.iterator();
        while(itr1.hasNext())
        {
            System.out.print(itr1.next().toString()+" ");
        }

        System.out.println();
        System.out.println("Expected: X Y Z");
    }

    private void testPopLeft() {
        System.out.println("Testing Pop Left operation ");

        ResizingArrayDeque<String> deque3 = new ResizingArrayDeque<>();
        deque3.pushRight("X");
        deque3.pushRight("Y");
        deque3.pushRight("Z");

        deque3.popLeft();
        deque3.popLeft();

        System.out.print("Deque items generated : ");

        Iterator itr1 = deque3.iterator();
        while(itr1.hasNext())
        {
            System.out.print(itr1.next().toString()+" ");
        }

        System.out.println();
        System.out.println("Expected: Z");
    }

    private void testPopRight() {
        System.out.println("Test Pop Right operation ");

        ResizingArrayDeque<String> deque4 = new ResizingArrayDeque<>();
        deque4.pushRight("X");
        deque4.pushRight("Y");
        deque4.pushRight("Z");

        deque4.popRight();
        deque4.popRight();

        System.out.print("Deque items generated : ");

        Iterator itr1 = deque4.iterator();
        while(itr1.hasNext())
        {
            System.out.print(itr1.next().toString()+" ");
        }

        System.out.println();
        System.out.println("Expected: X");
    }
}