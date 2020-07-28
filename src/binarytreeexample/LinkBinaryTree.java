/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarytreeexample;

import java.util.Iterator;
import positionexample.Position;

/**
 *
 * @author admin
 * @param <E>
 */
public class LinkBinaryTrees<E> extends AbstractBinaryTree<E> {

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterable<Position<E>> positions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected static class Node<E> implements Position<E> {

        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }

        /**
         * @return the element
         */
        public E getElement() {
            return element;
        }

        /**
         * @param element the element to set
         */
        public void setElement(E element) {
            this.element = element;
        }

        /**
         * @return the parent
         */
        public Node<E> getParent() {
            return parent;
        }

        /**
         * @param parent the parent to set
         */
        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        /**
         * @return the left
         */
        public Node<E> getLeft() {
            return left;
        }

        /**
         * @param left the left to set
         */
        public void setLeft(Node<E> left) {
            this.left = left;
        }

        /**
         * @return the right
         */
        public Node<E> getRight() {
            return right;
        }

        /**
         * @param right the right to set
         */
        public void setRight(Node<E> right) {
            this.right = right;
        }
    } // end of nested Node<E> class

    protected Node<E> root = null;
    private int size = 0;

    public LinkBinaryTrees() {

    }

    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException("Not valid position type");
        }
        Node<E> node = (Node<E>) p;
        if (node.getParent() == node) {
            throw new IllegalArgumentException("p is no longer in the tree");
        }
        return node;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return node.getParent();
    }

    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return (Position<E>) node.getLeft().getElement();
    }

    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        return (Position<E>) node.getRight().getElement();
    }

    @Override
    public int size() {
        return size;
    }

    public Position<E> addRoot(E e) /*throws IllegalStateException */ {
        if (isEmpty()) //throw new IllegalStateException("Tree is not empty"); 
        {
            root = new Node<>(e, null, null, null);
        }
        size = 1;
        return root;
    }

    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("parent node already has a left child");
        }
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("parent node already has a right child");
        }
        Node<E> child = new Node<>(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    public void attach(Position<E> p, LinkBinaryTrees<E> t1, LinkBinaryTrees<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)) {
            throw new IllegalArgumentException("p must be a leaf");
        }
        size += t1.size() + t2.size();
        if (!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(p) == 2) {
            throw new IllegalArgumentException("p has two children");
        }
        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }
}
