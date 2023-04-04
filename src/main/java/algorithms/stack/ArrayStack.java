package algorithms.stack;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ArrayStack<E> implements Stack<E>, Iterable<E>{
    private E[] a; //stack entries
    private int N;//size

    public ArrayStack(int capacity) {
       this.a = (E[]) new Object[capacity];
       this.N = 0;
    }

   public ArrayStack(){
        this.a = (E[]) new Object[1];
        this.N = 0;
    }

    @Override
    public void push(@NotNull E item) {
        if (N == a.length) resize(2*a.length); //Si N es igual al tamaño de a entonces no tenemos mas lugar.
        // Por eso agrandamos a al doble del tamaño
        //Despues de agrandarlo, ponemos el proximo item en N++ (la siguiente posición)
        a[N++] = item;

    }

    @Override
    public E pop() {
        E item = a[--N]; //Sacamos el item del tope del Stack
        a[N] = null; //Acá evitamos loitering. No poseemos referencia a un item que ya no necesitamos. Por eso lo ponemos en null
        if ( N>0 && N == a.length/4){ //Con esto evitamos overflow y nunca esta menos de 1/4 lleno(A menos que el stack este vacio)
            resize(a.length/2);
        }
        return item;
    }

    @Override
    public boolean isEmpty() {
        return N==0; //Si N esta en 0 me devuelve true, lo que significa que true == vacío :)
    }

    @Override
    public int size() {
        return N;
    }

    public void resize(int max) {   //Paso el stack de tamaño N a un nuevo Array con el nuevo tamaño (max)
        E[] temporal = (E[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temporal[i] = a[i];
            a = temporal;  //podemos seguir usando nuestro Array a  8-)
        }
    }



    @NotNull
    @Override
    public Iterator<E> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<E>{

        private int i = N; //A i le asignamos N de nuestra clase ArrayStack()
        @Override
        public boolean hasNext() {
            return i==0; //Si nuestro stack esta vacío nos devuelve true uwu
        }

        @Override
        public E next() {
            if (!(i==0)){
                return a[--i];
            }
            throw new RuntimeException("Stack is Empty");
        }
    }
}
