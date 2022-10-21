import java.util.Collection;
import java.util.Queue;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Your implementation of a BST.
 *
 * @author Nitya Jani
 * @version 1.0
 * @userid njani8
 * @GTID 903598748
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**cc
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is nothing so it can't be added to the BST.");
        }
        for (T j : data) {
            add(j);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data points to nothing.");
        }
        root = addHelper(root, data);
    }

    /**
     * Adds the data to the tree (helper)
     *
     * @param curr is the current node
     * @param d is the data
     * @return the node to point to
     */
    private BSTNode<T> addHelper(BSTNode<T> curr, T d) {
        if (curr == null) {
            BSTNode<T> newNode = new BSTNode<>(d);
            size = size + 1;
            return newNode;
        } else if (curr.getData().compareTo(d) < 0) {
            curr.setRight(addHelper(curr.getRight(), d));
        } else if (curr.getData().compareTo(d) > 0) {
            curr.setLeft(addHelper(curr.getLeft(), d));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is nothing.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = removeHelper(root, data, dummy);
        size = size - 1;
        return dummy.getData();
    }
    /**
     * @return the node to point to
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param curr is the current node
     * @param d is the data
     * @param dataToReturn is a dummy node
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, T d, BSTNode<T> dataToReturn) {
        if (curr == null) {
            throw new NoSuchElementException("The data you're trying to remove isn't in the tree.");
        } else if (curr.getData().compareTo(d) < 0) {
            curr.setRight(removeHelper(curr.getRight(), d, dataToReturn));
        } else if (curr.getData().compareTo(d) > 0) {
            curr.setLeft(removeHelper(curr.getLeft(), d, dataToReturn));
        } else if (curr.getData().compareTo(d) == 0) {
            if (curr.getRight() == null && curr.getLeft() == null) {
                dataToReturn.setData(curr.getData());
                return null;
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                dataToReturn.setData(curr.getData());
                return curr.getRight();
            } else if (curr.getRight() == null && curr.getLeft() != null) {
                dataToReturn.setData(curr.getData());
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() != null) {
                dataToReturn.setData(curr.getData());
                //find successor
                BSTNode<T> empty = new BSTNode<T>(null);
                curr.setRight(findSuccessor(curr.getRight(), empty));
                curr.setData(empty.getData());
            }
        }
        return curr;
    }

    /**
     * finds the successor by going all the way left
     *
     * @return node (successor) connected to all other descendants if necessary
     * @param curr is the current node
     * @param empty is an empty dummy node
     */
    private BSTNode<T> findSuccessor(BSTNode<T> curr, BSTNode<T> empty) {
        if (curr.getLeft() == null) {
            empty.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(findSuccessor(curr.getLeft(), empty));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is nothing.");
        }
        return getHelper(root, data);
    }

    /**
     * Returns the node from the tree with the matching data
     *
     * @param d the data to search for
     * @return the node in the tree with the right data
     * @param curr is the current node
     */
    private T getHelper(BSTNode<T> curr, T d) {
        if (curr == null) {
            throw new NoSuchElementException("That data is not in the tree.");
        } else if (curr.getData().compareTo(d) == 0) {
            return curr.getData();
        } else if (curr.getData().compareTo(d) < 0) {
            return getHelper(curr.getRight(), d);
        } else {
            return getHelper(curr.getLeft(), d);
        }
    }

    /**
     * Returns whether data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is nothing.");
        }
        return containsHelper(root, data);
    }
    /**
     * Helper Method for contains
     * @param d the data to search for
     * @return true if the parameter is contained within the tree
     * @param curr is the current node
     */
    private boolean containsHelper(BSTNode<T> curr, T d) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(d) == 0) {
            return true;
        } else if (curr.getData().compareTo(d) < 0) {
            return containsHelper(curr.getRight(), d);
        } else {
            return containsHelper(curr.getLeft(), d);
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * Go through the tree
     *
     * @param curr is a node
     * @param l is the list
     */
    private void preorderHelper(BSTNode<T> curr, List<T> l) {
        if (curr == null) {
            return;
        } else {
            l.add(curr.getData());
            preorderHelper(curr.getLeft(), l);
            preorderHelper(curr.getRight(), l);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * Go through the tree
     *
     * @param curr is a node
     * @param l is the list
     */
    private void inorderHelper(BSTNode<T> curr, List<T> l) {
        if (curr == null) {
            return;
        } else {
            inorderHelper(curr.getLeft(), l);
            l.add(curr.getData());
            inorderHelper(curr.getRight(), l);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }

    /**
     * Go through the tree
     *
     * @param curr is a node
     * @param l is the list
     */
    private void postorderHelper(BSTNode<T> curr, List<T> l) {
        if (curr == null) {
            return;
        } else {
            postorderHelper(curr.getLeft(), l);
            postorderHelper(curr.getRight(), l);
            l.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new LinkedList<>();
        if (root != null) {
            queue.add(root);
            list.add(root.getData());
            BSTNode<T> curr = root;
            while (!queue.isEmpty()) {
                curr = queue.remove();
                if (curr.getLeft() != null) {
                    queue.add(curr.getLeft());
                    list.add(curr.getLeft().getData());
                }
                if (curr.getRight() != null) {
                    queue.add(curr.getRight());
                    list.add(curr.getRight().getData());
                }
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        int height = heightHelper(root);
        return height;
    }

    /**
     * finds the height
     *
     * @param curr is the current node
     * @return the height
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> list = new ArrayList<T>();
        getMaxDataPerLevelHelper(root, 1, list);
        return list;
    }

    /**
     * finds the max on each level
     *
     * @param curr is the current node
     * @param l is the list
     * @param level is the level on the tree
     *
     */
    private void getMaxDataPerLevelHelper(BSTNode<T> curr, int level, List<T> l) {
        if (curr == null) {
            return;
        } else if (l.size() < level) {
            l.add(curr.getData());
        }
        getMaxDataPerLevelHelper(curr.getRight(), level + 1, l);
        getMaxDataPerLevelHelper(curr.getLeft(), level + 1, l);
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /*public static void main(String[] args) {
        BST<Integer> tree = new BST<>();
        tree.add(100);
        tree.add(45);
        tree.add(102);
        tree.add(401);
        tree.add(13);
        tree.add(44);
        tree.add(39);
        tree.add(10);
        Integer a = tree.get(39);
        System.out.println(a);
    }*/
}
