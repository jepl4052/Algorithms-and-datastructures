/*
    Jens Plate, jepl4052
 */

// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera.
package alda.heap;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>>
{


    private static final int DEFAULT_CAPACITY = 2; // For standard binary heap
    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array

    int nodeCapacity;

    /**
     * Construct the binary heap.
     */
    public DHeap( )
    {
        this( DEFAULT_CAPACITY );
    }

    /**
     * Construct the binary heap.
     * @param capacity the capacity of the binary heap.
     */
    public DHeap(int capacity )
    {
        if(capacity < 2) throw new IllegalArgumentException();
        currentSize = 0;
        array = (AnyType[]) new Comparable[ capacity + 1 ];
        this.nodeCapacity = capacity;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );

        int hole = ++currentSize;

        try {
            // Percolate up
            for (array[0] = x; x.compareTo(array[parentIndex(hole)]) <= 0; hole = parentIndex(hole)) {
                array[hole] = array[parentIndex(hole)];
            }
        }
        catch (IllegalArgumentException e) {
            //You tried to find parent for the root, do nothing
        }
        array[ hole ] = x;
    }


    private void enlargeArray( int newSize )
    {
        AnyType [] old = array;
        array = (AnyType []) new Comparable[ newSize ];
        for( int i = 0; i < old.length; i++ )
            array[ i ] = old[ i ];
    }

    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin( );
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );

        return minItem;
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
    }

    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown(int hole)
    {
        int child;
        AnyType tmp = array[ hole ];

        for( ; findMinChild(hole) <= currentSize; hole = child )
        {
            child = findMinChild(hole);

            if( array[ child ].compareTo( tmp ) < 0 )
                array[ hole ] = array[ child ];
            else
                break;
        }
        array[hole] = tmp;
    }

    // Following methods added by JEPL4052

    public int parentIndex(int i) {

        if(i <= 1) throw new IllegalArgumentException();

        return ((i - 2) / nodeCapacity + 1);
    }

    public int firstChildIndex(int i) {

        if(i <= 0) throw new IllegalArgumentException();

        return (i * nodeCapacity) - (nodeCapacity - 2);
    }

    private int findMinChild(int parent) {

        int minChild = firstChildIndex(parent);

        for(int i = minChild; i <= (minChild + nodeCapacity) && i <= currentSize; i++) {
            if(array[minChild].compareTo(array[i]) > 0) {
                minChild = i;
            }
        }
        return minChild;
    }

    // Following methods provided from DHeapTester
    public int size(){ return currentSize; }
    AnyType get(int index){ return array[index]; }

}