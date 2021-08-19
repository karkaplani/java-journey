package datastructure.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListQueue<R> extends AbstractQueue<R>{ //Add to the tail, remove the head

	class Node<T>{
		private T value;
		private Node<T> next;
		private Node<T> prev;
		
		private Node(T value) {
			this.value = value;
		}
		
		private Node(Node<T> next, Node<T> prev) {
			this.next = next;
			this.prev = prev;
		}
		
		private Node(T value, Node<T> next, Node<T> prev) {
			this.value = value;
			this.next = next;
			this.prev = prev;
		}

		@Override
		public String toString() {
			return "Node [value=" + value + ", next=" + next + ", prev=" + prev + "]";
		}
	}
	
	private Node<R> head;
	private Node<R> tail;
	private int size;
	
	@Override
	public boolean offer(R e) { 
		
		Node<R> node = new Node<R>(e);
		if(isEmpty()) {
			head = tail = node;
		} else {
			tail.next = node;
			node.prev = tail;
			tail = node;
		}
		size++;
		return true;
	}

	@Override
	public R poll() { 
		if(isEmpty()) return null;
		R r = head.value;
		removeOneNode(head);
		return r;
	}

	@Override
	public R peek() {
		if(isEmpty()) return null;
		return head.value;
	}

	@Override
	public int size() { 
		return size; 
	}

	@Override
	public boolean isEmpty() { 
		return size == 0 || head == null;
	}

	@Override
	public boolean contains(Object o) { 
		
		for(Node<R> node = head; node != null; node = node.next) {
			if((node.value != null && node.value.equals(o) ) || (node.value == null && o == null)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		
		for (Node< R> node = head; node != null; node = node.next) {
			if ((node.value != null && node.value.equals( o)) || (node.value == null && o == null)) {
				removeOneNode(node);
				return true;
			}
		}
		return false;
	}

	@Override
	public void clear() { 
		head = tail = null;
		size = 0;
	}
	
	private void removeOneNode(Node<R> node) {
		
		if(node==null) return;
		
		if(node==(head)) {
			head = head.next;
			if(head != null) {
				head.prev = null;
				node.next = null;
			}
		} else if(node == tail){
			tail = tail.prev;
			tail.next = null;
			node.prev = null;
		} else {
			node.prev.next = node.next;
			node.next.prev = node.prev;
			node.next = null;
			node.prev = null;
		}
		size--;
	}
	
	private class LKIT implements Iterator<R> {

		private Node<R> current = head, nodeReturned;
		
		public Iterator<R> iterator() {
			return new LKIT();
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public R next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No elements left");
			}
			if(current == null) {
				current  = head;
			}
			nodeReturned = current;
			current = current.next;
			return nodeReturned.value;
		}
		
		@Override
		public void remove() {
			if (nodeReturned == null) {
				throw new IllegalStateException();
			}
			LinkedListQueue.this.removeOneNode(nodeReturned);
			nodeReturned = null;
		}
	}
	
	@Override
	public Iterator<R> iterator() {
		LKIT it = new LKIT();
		return it.iterator();
	}
}
