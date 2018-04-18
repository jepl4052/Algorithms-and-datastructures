/*
    jens plate, jepl4052
 */

package alda.linear;

import java.util.*;

public class MyALDAList<E> implements ALDAList<E> {

    private Node<E> first;
    private Node<E> last;
    private int size;

    private static class Node<T> {

        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    public void add(E element) {

        if(element == null) throw new NullPointerException();

        if (first == null) {
            first = new Node<>(element);
            last = first;
        } else {
            last.next = new Node<>(element);
            last = last.next;
        }

        this.size++;
    }

    /**
     * This method adds a new node to this linked list at a specific index position.
     * Shifts node at that position and all subsequent nodes to the right.
     *
     * @param index the index position the new element will be inserted at.
     * @param element the element to add to the linked list.
     *
     * @throws NullPointerException if the element to add is null.
     * @throws IndexOutOfBoundsException if index is < 0 or larger than size() of this list.
     */

    public void add(int index, E element) {

        if(element == null) throw new NullPointerException();
        if(index < 0 || index > size()) throw new IndexOutOfBoundsException();

        if(index == 0 && first == null) {
            add(element);
        }

        else if(index == 0) {
            addFirst(element);
            this.size++;
        }

        else if(index == size()) {
            add(element);
        }

        else {

            Node<E> previous = getNode(index-1);
            Node<E> next = previous.next;
            Node<E> current = new Node<>(element);
            previous.next = current;
            current.next = next;
            this.size++;
        }
    }

    private void addFirst(E element) {

        if (size() == 0) {
            first = new Node<>(element);
            last = first;
            last.next = null;

        }
        else {

            Node<E> temp = first;
            first = new Node<>(element);
            first.next = temp;
        }
    }

    /**
     * This method deletes a node from a specific index in this linked list.
     * Returns the data from the node that was removed.
     *
     * @param index position of the node to delete from this linked list.
     *
     * @return E the data from the deleted node.
     *
     * @throws IndexOutOfBoundsException if index is < 0 or larger than size of this linked list.
     */

    public E remove(int index) {
        if(index < 0 || index > size()) throw new IndexOutOfBoundsException();

        E temp = get(index);

        if(index == 0) {
            removeFirst();
        }
        else if (index == size()-1) {
            removeLast(index);
        }

        else {
            Node<E> previous = getNode(index-1);
            previous.next = previous.next.next;
        }

        this.size--;
        return temp;
    }

    public boolean remove(E element) {

        if(element == null) throw new NullPointerException();

        int index = indexOf(element);

        if(index == -1) return false;

        remove(index);

        return true;

    }

    private void removeFirst() {
        first = first.next;
    }

    private void removeLast(int index) {
        Node<E> temp = getNode(index-1);
        temp.next = null;
        last = temp;
    }

    public E get(int index) {

        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();

        return getNode(index).data;

    }

    private Node<E> getNode(int index) {
        Node<E> temp = first;

        if(index == 0) {
            return temp;

        }
        else if (index == size()-1) {
            return last;
        }
        else {

            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
        }

        return temp;
    }

    public boolean contains(E element) {
        for(Node<E> temp=first; temp!=null; temp=temp.next){
            if(temp.data==element || temp.data.equals(element)){
                return true;
            }
        }
        return false;
    }

    public int indexOf(E element) {
        if(first == null) throw new NullPointerException();
        if(!contains(element)) return -1;

        int count = 0;
        Node<E> temp = first;

        while(count <= size()) {

            if(temp.data == element || temp.data.equals(element)) {
                break;
            } else {
                temp = temp.next;
                count++;
            }
        }
        return count;
    }

    public void clear() {
        first = null;
        last = null;
        this.size = 0;
    }

    public int size() {
        return size < Integer.MAX_VALUE ? size : Integer.MAX_VALUE;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node<E> current = first;
            private Node<E> previous = null;
            private boolean remove = false;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {

                if(current == null)
                    throw new NoSuchElementException();

                E temp = current.data;

                previous = current;
                current = current.next;

                remove = false;
                return temp;
            }

            @Override
            public void remove() {

                if(previous == null || remove)
                    throw new IllegalStateException();

                MyALDAList.this.remove(previous.data);

                remove = true;
            }

        };
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder("[");

        for (int i = 0; i < size(); i++) {
            s.append(get(i));

            if(size()-i-1 != 0)
                s.append(", ");
        }
        s.append("]");

        return String.valueOf(s);
    }
}
