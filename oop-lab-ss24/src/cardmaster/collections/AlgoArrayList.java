package cardmaster.collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class AlgoArrayList<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 8;
    public T[] data;
    private int size;
    public boolean sorted = false;

    @SuppressWarnings("unchecked")
    public AlgoArrayList() {
        this.data = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }


    public void add(T element) {
        if (this.size < data.length - 1) {
            this.data[size] = element;
            size++;
        } else {
            increaseSize();
            add(element);
        }
    }

    public T pop(){
        T temp = data[size-1];
        remove(size-1);
        return temp;
    }

    public void swap(int a, int b){
        T temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }
    public void shuffle() {
        Random random = new Random();
        int n = this.size;
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            this.swap(i, change);
        }
    }

    public void remove(int index) {
        this.data[index] = null;
        this.size--;
        closeGaps();
    }

    public void remove(T element){
        for(int i = 0; i < getSize(); i++){
            if(data[i].equals(element)){
                remove(i);
            }
        }
    }
    @SuppressWarnings("unchecked")
    private void increaseSize() {
        T[] help = (T[]) new Object[data.length + DEFAULT_CAPACITY];
        for (int i = 0; i < this.data.length; i++) {
            help[i] = this.data[i];
        }
        this.data = help;
    }
    @SuppressWarnings("unchecked")
    public void closeGaps() {
        T[] help = (T[]) new Object[size];
        int index = 0;
        for (T element : this.data) {
            if (element != null) {
                help[index] = element;
                index++;
            }
        }
        this.data = help;


    }

    public void mergeSort(Comparator<T> c) {
        closeGaps();
        this.data = mergeSort(this.data, c);
        this.sorted = true;
    }
    @SuppressWarnings("unchecked")
    private T[] mergeSort(T[] elements, Comparator<T> c) {
        if (elements.length == 1 || elements.length == 0) {

            return elements;
        }


        T[] l = (T[]) new Object[elements.length / 2];
        T[] r;
        if (elements.length % 2 == 0) {
            r = (T[]) new Object[elements.length / 2];
        } else {
            r = (T[]) new Object[elements.length / 2 + 1];
        }

        for (int i = 0; i < elements.length; i++) {
            if (i < (int) Math.floor(elements.length / 2.0)) {
                l[i] = elements[i];
            } else {
                r[i - (int) Math.floor(elements.length / 2.0)] = elements[i];
            }
        }
        l = mergeSort(l, c);
        r = mergeSort(r, c);


        return merge(l, r, c);
    }
    @SuppressWarnings("unchecked")
    private T[] merge(T[] l, T[] r, Comparator<T> comp) {
        T[] merge = (T[]) new Object[l.length + r.length];
        int i = 0;
        int j = 0;
        for (int k = 0; k < l.length + r.length; k++) {
            if (i < l.length && (j >= r.length || comp.compare(l[i], r[j]) <= 0)) {
                merge[k] = l[i];
                i++;
            } else {
                merge[k] = r[j];
                j++;
            }
        }
        return merge;
    }


    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() {
        closeGaps();
        T[] copy = (T[]) new Object[this.data.length];
        System.arraycopy(this.data, 0, copy, 0, this.data.length);
        return copy;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int count = 0;
            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public T next() {
                return data[count++];
            }
        };
    }

    public int getSize() {
        return size;
    }

    public T get(int index){

        if(index > size){
            throw new IndexOutOfBoundsException("Ungültiger Index: " + index + ". Bitte geben sie einen Index größer gleich 0 und kleiner " + getSize() + " an." );
        }
        else{
            return data[index];
        }

    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean contains(T element){
        for(T e : data){
            if(e == null){
                continue;
            }
            if(element.equals(e)){
                return true;
            }
        }
        return false;
    }


}

