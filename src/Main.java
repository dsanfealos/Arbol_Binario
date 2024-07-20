

public class Main {
    public static void main(String[] args) {

        Tree<Integer> testTree = new Tree<>();
        testTree.add(8);
        Node<Integer> t4 = testTree.add(4);
        testTree.add(10);
        testTree.add(12);
        testTree.add(1);
        testTree.add(0);
        testTree.add(2);
        Node<Integer> t = testTree.add(9);
        testTree.add(20);
        testTree.add(30);
        Node<Integer> t2 = testTree.add(14);
        testTree.add(16);
        testTree.add(15);
        Node<Integer> t3 = testTree.add(13);

        System.out.println("INORDER: ");
        testTree.triggerInorder();
        System.out.println(" ");
        System.out.println("PREORDER: ");
        testTree.triggerPreorder();
        System.out.println(" ");
        System.out.println("POSTORDER: ");
        testTree.triggerPostorder();
        System.out.println(" ");

        testTree.remove(t);
        testTree.remove(t2);

        testTree.remove(t3);
        testTree.remove(t4);

        System.out.println("Height: " + testTree.heightTree(testTree.getRoot()));

        System.out.println("----------------------------");

        testTree.inorder(testTree.getRoot());

        //Mostrar
        System.out.println("-------------");
        Node<Integer> node = testTree.getNode(testTree.getRoot(), 12);
        System.out.println("Get node: " + node.getElement());

        Integer element = testTree.getElement(node, 12);
        System.out.println("Get element: " + element);


    }
}