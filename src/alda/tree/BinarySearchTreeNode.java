package alda.tree;

/*
    Jens Plate, jepl4052
 */

/**
 *
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) De ändringar som är tillåtna är dock
 * begränsade av följande:
 * <ul>
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * </ul>
 *
 * @author henrikbe
 *
 * @param <T>
 */

//@SuppressWarnings("unused") // Denna rad ska plockas bort. Den finns här
// tillfälligt för att vi inte ska tro att det är
// fel i koden. Varningar ska normalt inte döljas på
// detta sätt, de är (oftast) fel som ska fixas.

public class BinarySearchTreeNode<T extends Comparable<T>> {

    private T data;
    private BinarySearchTreeNode<T> left;
    private BinarySearchTreeNode<T> right;

    public BinarySearchTreeNode(T data) {
        this.data = data;
    }

    public boolean add(T data) {

        if (this.data.compareTo(data) > 0) {

            if (left == null) {

                left = new BinarySearchTreeNode<>(data);
                return true;
            }
            else {

                return left.add(data);
            }
        }

        else if (this.data.compareTo(data) < 0) {

            if (right == null) {

                right = new BinarySearchTreeNode<>(data);
                return true;
            }
            else {
                return right.add(data);
            }
        }
        return false;
    }

    private T findMin() {

        if(left != null)
            return left.findMin();
        else
            return this.data;
    }

    public BinarySearchTreeNode<T> remove(T data) {

        return removeNode(this, data);
    }

    private BinarySearchTreeNode<T> removeNode(BinarySearchTreeNode<T> root, T data) {

        if(root == null)
            return null;

        if(root.data.compareTo(data) > 0) {

            root.left = removeNode(root.left, data);
        }
        else if(root.data.compareTo(data) < 0) {

            root.right = removeNode(root.right, data);
        }
        else {

            if(root.left == null && root.right == null) {

                root = null;
            }
            else if(root.right == null) {

                root = root.left;
            }
            else if(root.left == null) {

                root = root.right;
            }
            else {

                BinarySearchTreeNode<T> temp  = findNode(root.right.findMin());
                root.data = temp.data;
                root.right = removeNode(root.right, temp.data);
            }
        }

        return root;
    }

    public boolean contains(T data) {

        return findNode(data) != null;
    }

    private BinarySearchTreeNode<T> findNode(T data) {

        if(this.data.compareTo(data) < 0) {

            return right != null ? right.findNode(data) : null;
        }
        else if (this.data.compareTo(data) > 0 ) {

            return left != null ? left.findNode(data) : null;
        }
        else if (this.data.compareTo(data) == 0) {

            return this;
        }

        return null;
    }

    public int size() {

        int size = 0;

        if(left != null)
            size += left.size();

        if(right != null)
            size += right.size();

        return size + 1;
    }

    public int depth() {

        int leftDepth = 0;
        int rightDepth = 0;

        if(left != null)
            leftDepth += left.depth() + 1;

        if(right != null)
            rightDepth += right.depth() + 1;

        if(leftDepth >= rightDepth)
            return leftDepth;
        else
            return rightDepth;
    }

    public String toString() {

        StringBuilder str = toStringBuilder();

        return str.substring(0, str.length()-2);
    }

    private StringBuilder toStringBuilder() {

        StringBuilder str = new StringBuilder();

        return str.append((left != null) ? left.toStringBuilder() : "")
                .append(this.data.toString())
                .append(", ")
                .append((right != null) ? right.toStringBuilder() : "");
    }
}