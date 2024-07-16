public class Arbol<T extends Comparable<T>>{
    private NodoArbol<T> root;
    private final int NODE_LEFT = 1;
    private final int NODE_RIGHT = 2;
    private final int TWO_NODES = 3;

    public Arbol(){
        this.root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public NodoArbol<T> getRoot(){
        return root;
    }

    public boolean isRoot(NodoArbol<T> nodo){
        return root == nodo;
    }

    public boolean isLeaf(NodoArbol<T> nodo){
        return nodo.getLeft() == null && nodo.getRight() == null;
    }

    public boolean isInternal(NodoArbol<T> nodo){
        return !isLeaf(nodo);
    }

    public NodoArbol<T> add(NodoArbol<T> origen, T elemento) {

        NodoArbol<T> nodo = null;
        //Si el root es nulo, lo añade el primero
        if (root == null) {
            nodo = new NodoArbol<>(elemento);
            root = nodo;
        } else if (origen == null) { //el parametro pasado es invalido
            System.out.println("El origen es nulo");
        } else {

            //Comparamos los elementos
            //Si el nodo del origen es mayor que el elemento pasado, pasa a la izquierda
            if (origen.getElement().compareTo(elemento) > 0) {

                //Si tiene nodo izquierdo, hago la llamada recursiva
                if (origen.getLeft() != null) {
                    nodo = add(origen.getLeft(), elemento);
                } else {
                    //Creo el nodo
                    nodo = new NodoArbol<>(elemento);
                    //Indico que el padre del nodo creado
                    nodo.setParent(origen);
                    //Indico que el nodo es el nodo izquierdo del origen
                    origen.setLeft(nodo);
                }

            } else { //sino pasa a la derecha

                //Si tiene nodo derecho, hago la llamada recursiva
                if (origen.getRight() != null) {
                    nodo = add(origen.getRight(), elemento);
                } else {
                    //Creo el nodo
                    nodo = new NodoArbol<>(elemento);
                    //Indico que el padre del nodo creado
                    nodo.setParent(origen);
                    //Indico que el nodo es el nodo izquierdo del origen
                    origen.setRight(nodo);
                }

            }

        }
        return nodo;
    }

    public NodoArbol<T> add(T elemento) {

        NodoArbol<T> nodo = null;
        //Si el root es nulo, lo añade el primero
        if (root == null) {
            nodo = new NodoArbol<>(elemento);
            root = nodo;
        } else {

            //Creo un nodo auxuliar
            NodoArbol<T> aux = root;
            boolean insertado = false;
            //No salgo hasta que este insertado
            while (!insertado) {

                //Comparamos los elementos
                //Si el nodo del origen es mayor que el elemento pasado, pasa a la izquierda
                if (aux.getElement().compareTo(elemento) > 0) {

                    //Si tiene nodo izquierdo, actualizo el aux
                    if (aux.getLeft() != null) {
                        aux = aux.getLeft();
                    } else {
                        //Creo el nodo
                        nodo = new NodoArbol<>(elemento);
                        //Indico que el padre del nodo creado
                        nodo.setParent(aux);
                        aux.setLeft(nodo);
                        //indico que lo he insertado
                        insertado = true;
                    }

                } else {

                    if (aux.getRight() != null) {
                        aux = aux.getRight();
                    } else {
                        //Creo el nodo
                        nodo = new NodoArbol<>(elemento);
                        //Indico que el padre del nodo creado
                        nodo.setParent(aux);
                        aux.setRight(nodo);
                        //indico que lo he insertado
                        insertado = true;
                    }

                }

            }

        }

        return nodo;

    }

    public void dispararPreorden(){
        this.preorden(this.root);
    }
    public void preorden (NodoArbol<T> nodo){
        if(nodo != null){
            System.out.print(nodo.getElement() + ", ");
            preorden(nodo.getLeft());
            preorden(nodo.getRight());
        }
    }

    public void dispararInorden(){
        this.inorden(this.root);
    }
    public void inorden (NodoArbol<T> nodo){
        if(nodo != null){
            inorden(nodo.getLeft());
            System.out.print(nodo.getElement() + ", ");
            inorden(nodo.getRight());
        }
    }

    public void dispararPostorden(){
        this.postorden(this.root);
    }
    public void postorden (NodoArbol<T> nodo){
        if(nodo != null){
            postorden(nodo.getLeft());
            postorden(nodo.getRight());
            System.out.print(nodo.getElement() + ", ");
        }
    }

    public int heightArbol(NodoArbol<T> nodo){

        int height = 0;
        if (isInternal(nodo)){
            if (nodo.getLeft()!=null){
                height = Math.max(height, heightArbol(nodo.getLeft()));
            }

            if (nodo.getRight()!=null){
                height = Math.max(height, heightArbol(nodo.getRight()));
            }
            height++;
        }
        return height;
    }

    public int depthArbol(NodoArbol<T> nodo){
        int depth = 0;

        if (nodo != getRoot()){
            depth = 1 + depthArbol(nodo.getParent());
        }
        return depth;
    }

    public void remove(NodoArbol<T> nodo){
        if (root == null){
            System.out.println("No hay nodos que borrar");
        }else if(isLeaf(nodo)){
            removeLeaf(nodo);
        }else if(nodo.getRight() != null && nodo.getLeft() == null){
            removeWithChild(nodo, NODE_RIGHT);
        }else if(nodo.getLeft() != null && nodo.getRight() == null){
            removeWithChild(nodo, NODE_LEFT);
        }else{
            removeWithChild(nodo, TWO_NODES);
        }
    }

    private void removeLeaf(NodoArbol<T> nodo){
        if (isRoot(nodo)){
            root = null;
        }else{
            NodoArbol<T> parent = nodo.getParent();

            if (parent.getLeft() == nodo){
                parent.setLeft(null);
            }

            if (parent.getRight() == nodo){
                parent.setRight(null);
            }

            nodo = null;
        }
    }

    private void removeWithChild(NodoArbol<T> nodo, int typeNode){
        NodoArbol<T> next = null;

        switch(typeNode){
            case NODE_LEFT:
                next = nodo.getLeft();
                break;
            case NODE_RIGHT:
                next = minSubTree(nodo.getRight());
                break;
            case TWO_NODES:
                //Relaciones del hijo al padre
                next = minSubTree(nodo.getRight());
                if (!isRoot(next.getParent())){
                    nodo.getLeft().setParent(next);
                    nodo.getRight().setParent(next);
                    if (next.getParent().getLeft() == next){
                        next.getParent().setLeft(null);
                    }else if (next.getParent().getRight() == next){
                        next.getParent().setRight(null);
                    }
                }

                break;
        }

        //Relaciones del padre del anterior nodo al next hijo (nuevo nodo)
        next.setParent(nodo.getParent());
        if (!isRoot(nodo)){
            if (nodo.getParent().getLeft() == nodo){
                nodo.getParent().setLeft(next);
            }else if (nodo.getParent().getRight() == nodo){
                nodo.getParent().setRight(next);
            }
        }else{
            root = next;
        }

        if (nodo.getRight()!= null && nodo.getRight() != next){
            next.setRight(nodo.getRight());
        }
        if (nodo.getLeft()!= null && nodo.getLeft() != next){
            next.setLeft(nodo.getLeft());
        }
        nodo = null;
    }

    private NodoArbol<T> minSubTree(NodoArbol<T> nodo){
        if (nodo != null && nodo.getLeft() != null){
            while (!isLeaf(nodo)){
                nodo = minSubTree(nodo.getLeft());
            }
        }
        return nodo;
    }

    
}
