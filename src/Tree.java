import java.util.ArrayList;

public class Tree<T extends Comparable<T>>{
    private Node<T> root;
    private final int NODE_LEFT = 1;
    private final int NODE_RIGHT = 2;
    private final int TWO_NODES = 3;

    public Tree(){
        this.root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public Node<T> getRoot(){
        return root;
    }

    public boolean isRoot(Node<T> node){
        return root == node;
    }

    public boolean isLeaf(Node<T> node){
        return node.getLeft() == null && node.getRight() == null;
    }

    public boolean isInternal(Node<T> node){
        return !isLeaf(node);
    }

    public Node<T> add(Node<T> origen, T elemento) {

        Node<T> node = null;
        //Si el root es nulo, lo añade el primero
        if (root == null) {
            node = new Node<>(elemento);
            root = node;
        } else if (origen == null) { //el parametro pasado es invalido
            System.out.println("El origen es nulo");
        } else {

            //Comparamos los elementos
            //Si el nodo del origen es mayor que el elemento pasado, pasa a la izquierda
            if (origen.getElement().compareTo(elemento) > 0) {

                //Si tiene nodo izquierdo, hago la llamada recursiva
                if (origen.getLeft() != null) {
                    node = add(origen.getLeft(), elemento);
                } else {
                    //Creo el nodo
                    node = new Node<>(elemento);
                    //Indico que el padre del nodo creado
                    node.setParent(origen);
                    //Indico que el nodo es el nodo izquierdo del origen
                    origen.setLeft(node);
                }

            } else { //sino pasa a la derecha

                //Si tiene nodo derecho, hago la llamada recursiva
                if (origen.getRight() != null) {
                    node = add(origen.getRight(), elemento);
                } else {
                    //Creo el nodo
                    node = new Node<>(elemento);
                    //Indico que el padre del nodo creado
                    node.setParent(origen);
                    //Indico que el nodo es el nodo izquierdo del origen
                    origen.setRight(node);
                }

            }

        }
        return node;
    }

    public Node<T> add(T element) {

        Node<T> node = null;
        //Si el root es nulo, lo añade el primero
        if (root == null) {
            node = new Node<>(element);
            root = node;
        } else {

            //Creo un nodo auxuliar
            Node<T> auxiliarNode = root;
            boolean inserted = false;
            //No salgo hasta que este insertado
            while (!inserted) {

                //Comparamos los elementos
                //Si el nodo del origen es mayor que el elemento pasado, pasa a la izquierda
                if (auxiliarNode.getElement().compareTo(element) > 0) {

                    //Si tiene nodo izquierdo, actualizo el aux
                    if (auxiliarNode.getLeft() != null) {
                        auxiliarNode = auxiliarNode.getLeft();
                    } else {
                        //Creo el nodo
                        node = new Node<>(element);
                        //Indico que el padre del nodo creado
                        node.setParent(auxiliarNode);
                        auxiliarNode.setLeft(node);
                        //indico que lo he insertado
                        inserted = true;
                    }

                } else {

                    if (auxiliarNode.getRight() != null) {
                        auxiliarNode = auxiliarNode.getRight();
                    } else {
                        //Creo el nodo
                        node = new Node<>(element);
                        //Indico que el padre del nodo creado
                        node.setParent(auxiliarNode);
                        auxiliarNode.setRight(node);
                        //indico que lo he insertado
                        inserted = true;
                    }

                }

            }

        }

        return node;

    }

    public void triggerPreorder(){
        this.preorder(this.root);
    }
    public void preorder(Node<T> node){
        if(node != null){
            System.out.print(node.getElement() + ", ");
            preorder(node.getLeft());
            preorder(node.getRight());
        }
    }

    public void triggerInorder(){
        this.inorder(this.root);
    }
    public void inorder(Node<T> node){
        if(node != null){
            inorder(node.getLeft());
            System.out.print(node.getElement() + ", ");
            inorder(node.getRight());
        }
    }

    public void triggerPostorder(){
        this.postorder(this.root);
    }
    public void postorder(Node<T> node){
        if(node != null){
            postorder(node.getLeft());
            postorder(node.getRight());
            System.out.print(node.getElement() + ", ");
        }
    }

    public int heightTree(Node<T> node){

        int height = 0;
        if (isInternal(node)){
            if (node.getLeft()!=null){
                height = Math.max(height, heightTree(node.getLeft()));
            }

            if (node.getRight()!=null){
                height = Math.max(height, heightTree(node.getRight()));
            }
            height++;
        }
        return height;
    }

    public int depthTree(Node<T> node){
        int depth = 0;

        if (node != getRoot()){
            depth = 1 + depthTree(node.getParent());
        }
        return depth;
    }

    public void remove(Node<T> node){
        if (root == null){
            System.out.println("There are no nodes to be deleted");
        }else if(isLeaf(node)){
            removeLeaf(node);
        }else if(node.getRight() != null && node.getLeft() == null){
            removeWithChild(node, NODE_RIGHT);
        }else if(node.getLeft() != null && node.getRight() == null){
            removeWithChild(node, NODE_LEFT);
        }else{
            removeWithChild(node, TWO_NODES);
        }
    }

    private void removeLeaf(Node<T> node){
        if (isRoot(node)){
            root = null;
        }else{
            Node<T> parent = node.getParent();

            if (parent.getLeft() == node){
                parent.setLeft(null);
            }

            if (parent.getRight() == node){
                parent.setRight(null);
            }

            node = null;
        }
    }

    private void removeWithChild(Node<T> node, int typeNode){
        Node<T> next = null;

        switch (typeNode) {
            case NODE_LEFT -> next = node.getLeft();
            case NODE_RIGHT -> next = minSubTree(node.getRight());
            case TWO_NODES -> {
                //Relaciones del hijo al padre
                next = minSubTree(node.getRight());
                if (!isRoot(next.getParent())) {
                    node.getLeft().setParent(next);
                    node.getRight().setParent(next);
                    if (next.getParent().getLeft() == next) {
                        next.getParent().setLeft(null);
                    } else if (next.getParent().getRight() == next) {
                        next.getParent().setRight(null);
                    }
                }
            }
            default -> {
                System.out.println("This node has no child");
                return;
            }
        }

        //Relaciones del padre del anterior nodo al next hijo (nuevo nodo)
        next.setParent(node.getParent());
        if (!isRoot(node)){
            if (node.getParent().getLeft() == node){
                node.getParent().setLeft(next);
            }else if (node.getParent().getRight() == node){
                node.getParent().setRight(next);
            }
        }else{
            root = next;
        }

        if (node.getRight()!= null && node.getRight() != next){
            next.setRight(node.getRight());
        }
        if (node.getLeft()!= null && node.getLeft() != next){
            next.setLeft(node.getLeft());
        }
        node = null;
    }

    private Node<T> minSubTree(Node<T> node){
        if (node != null && node.getLeft() != null){
            while (!isLeaf(node)){
                node = minSubTree(node.getLeft());
            }
        }
        return node;
    }

    public Node<T> getNode(Node<T> node, T element){
        Node<T> auxiliar = null;

        if (node.getElement().compareTo(element) == 0){
            auxiliar = node;
        }else{
            if (node.getLeft() != null){
                auxiliar = getNode(node.getLeft(), element);
            }

            if (node.getRight() != null){
                auxiliar = getNode(node.getRight(), element);
            }
        }
        return auxiliar;
    }

    public T getElement(Node<T> node, T element){

        Node<T> auxiliar = getNode(node, element);
        if (auxiliar == null){
            return null;
        }
        return auxiliar.getElement();
    }

    public void getNodes(Node<T> node, T element, ArrayList<Node<T>> nodeList){
        if (node.getElement().compareTo(element) == 0){
            nodeList.add(node);
        }
        if (node.getLeft() != null){
            getNodes(node.getLeft(), element, nodeList);
        }

        if (node.getRight() != null){
            getNodes(node.getRight(), element, nodeList);
        }
    }

    public ArrayList<T> getElements(Node<T> node, T element){
        ArrayList<T> elements = new ArrayList<>();
        ArrayList<Node<T>> nodeList = new ArrayList<>();

        getNodes(node, element, nodeList);

        for (Node<T> auxiliar: nodeList){
            elements.add(auxiliar.getElement());
        }
        return elements;
    }
}
