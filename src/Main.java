

public class Main {
    public static void main(String[] args) {

        Arbol<Integer> arbol = new Arbol<>();
        arbol.add(8);
        NodoArbol<Integer> t4 = arbol.add(4);
        arbol.add(10);
        arbol.add(12);
        arbol.add(1);
        arbol.add(0);
        arbol.add(2);
        NodoArbol<Integer> t = arbol.add(9);
        arbol.add(20);
        arbol.add(30);
        NodoArbol<Integer> t2 = arbol.add(14);
        arbol.add(16);
        arbol.add(15);
        NodoArbol<Integer> t3 = arbol.add(13);

        System.out.println("INORDEN: ");
        arbol.dispararInorden();
        arbol.remove(t);
        arbol.remove(t2);

        arbol.remove(t3);
        arbol.remove(t4);

        System.out.println(" ");
        System.out.println("POSTORDEN: ");
        arbol.dispararPostorden();
        System.out.println(" ");
        System.out.println("PREORDEN: ");
        arbol.dispararPreorden();
        System.out.println(" ");

        System.out.println("Altura: " + arbol.heightArbol(arbol.getRoot()));

        System.out.println("----------------------------");

        arbol.inorden(arbol.getRoot());


    }
}