import java.util.Iterator;
import java.util.List;

public class BinarySearchTreeDict<K extends Comparable<K>,V> implements ProjOneDictionary<K,V> {
    ListQueue<K> LQ;
    Node root;
    Node curr;

    int size = 0;

    private class Node {
        K key;
        V value;
        Node left;
        Node right;
        Node parent;
        Node(K newKey, V newValue) {
            super();
            key = newKey;
            value = newValue;
        }
    }
    public Node findLeftChild(K key) {
        curr = root;
        while(curr != null){
            if(key.compareTo(curr.key) == 0){
                return curr.left;
            }
            if(key.compareTo(curr.key) > 0){
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return null;
    }
    public Node findRightChild(K key) {
        curr = root;
        while(curr != null){
            if(key.compareTo(curr.key) == 0){
                return curr.right;
            }
            if(key.compareTo(curr.key) > 0){
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return null;
    }
    private class BSTIterator implements Iterator<K> {
        int ind = 0;
        private BSTIterator() {
            LQ = new ListQueue<>();
            if(root != null) {

                LQ.enqueue(root.key);
            }
        }
        @Override
        public boolean hasNext() {
            return LQ.getSize() != 0;
        }//fix 1
        @Override
        public K next() {
            //returns key or null

            K toRet;
            K dequeuedK = LQ.dequeue();
            if(dequeuedK == null) {
                toRet = null;
            } else {
                toRet = dequeuedK;
                Node left = findLeftChild(dequeuedK);
                Node right = findRightChild(dequeuedK);
                if(left != null) {
                    LQ.enqueue(left.key);
                }
                if(right != null) {
                    LQ.enqueue(right.key);
                }

            }
            return toRet;
        }
    }



    @Override
    public boolean insert(K key, V value) throws NullValueException {
        if(value == null){
            throw new NullValueException();
        }

        boolean returnValue = false;
        curr = root;
        Node parent = root;
        if(root == null){
//            System.out.println("Root is null");
            Node rootNode = new Node(key,value);
            rootNode.left = null;
            rootNode.right = null;
            rootNode.key = key;
            rootNode.value = value;
            rootNode.parent = null;
            root = rootNode;
//            front = rootNode;
            size+=1;
//            System.out.println("Root is made");
            return false;
        }

        int leftOrRight = 0;//-1 = left, 1 = right
        while(curr != null){
            if(key.compareTo(curr.key) == 0){
                curr.value = value;
                returnValue = true;
                leftOrRight = 0;
                break;
            }
            if(key.compareTo(curr.key) > 0){
                parent = curr;
                curr = curr.right;
                leftOrRight = 1;
            } else {
                parent = curr;
                curr = curr.left;
                leftOrRight = -1;
            }

        }

        if(!returnValue) {
            Node newNode = new Node(key,value);
            newNode.left = null;
            newNode.right = null;
            newNode.parent = parent;
            if(leftOrRight == -1) {
                parent.left = newNode;
//                front = newNode;
            } else if(leftOrRight == 1) {
                parent.right = newNode;
            } else {
                //error
                System.out.println("Error insert()");
            }
            size += 1;
        }
        return returnValue;
    }

    @Override
    public V find(K key) {
        curr = root;
        while(curr != null){
            if(key.compareTo(curr.key) == 0){
                return curr.value;
            }
            if(key.compareTo(curr.key) > 0){
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        return null;
    }

    @Override
    public boolean delete(K key) {
        //true if deleted, false if not found
        curr = root;
//        System.out.println("Root is " + root.key);
        while(curr != null){
            if(key.compareTo(curr.key) == 0){
                //curr = nodeToDelete
//                System.out.println("Print this");
                break;
            }
            if(key.compareTo(curr.key) > 0){
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        if(curr == null){
            return false;
        }
        if(curr.left == null && curr.right == null){
            //leaf
            if(curr == root) {//root node is a leaf
                root = null;
            } else if(curr.key.compareTo(curr.parent.left.key) == 0){
                //left child
                curr.parent.left = null;
                curr.parent = null;
            } else if(curr.key.compareTo(curr.parent.right.key) == 0) {
                curr.parent.right = null;
                curr.parent = null;
            } else {
                System.out.println("Error");
            }
            size-=1;
            return true;
        }
        if(curr.left == null) {
//            System.out.println("Print this");
            //only child is right
            if(curr.parent == null) {
                //root node with right child
//                System.out.println("Print this2");
                root = curr.right;
                curr.right.parent = null;
            } else if(curr.parent.right == curr) {
                curr.parent.right = curr.right;
                curr.right.parent = curr.parent;
            } else if(curr.parent.left == curr) {
                curr.parent.left = curr.right;
                curr.right.parent = curr.parent;
            } else {
                System.out.println("Error delete()");
            }
            size-=1;
            return true;
        }
        if(curr.right == null) {
            //only child is left
            if(curr.parent == null) {
                //root node with left child
                root = curr.left;
                curr.left.parent = null;
            } else if(curr.parent.right == curr) {
                curr.parent.right = curr.left;
                curr.left.parent = curr.parent;
            } else if(curr.parent.left == curr) {
                curr.parent.left = curr.left;
                curr.left.parent = curr.parent;
            } else {
                System.out.println("Error delete()");
            }
            size-=1;
            return true;
        }

        //both children are not null
        Node currRight = curr.right;

        while(currRight.left != null) {
            currRight = currRight.left;
        }
        //got the replacement node
        K replacementKey = currRight.key;
        V replacementValue = currRight.value;
        delete(currRight.key);
        curr.key = replacementKey;
        curr.value = replacementValue;

        size-=1;

        return true;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }
}
