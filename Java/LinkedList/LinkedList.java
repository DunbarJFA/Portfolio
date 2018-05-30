import java.util.*;
/*
*A simple implementation of a linked list.
*
*@author Jerod Dunbar
*@version  June 20, 2017
*/
public class LinkedList<AnyType> {
	private Node first;
	private Node last;
	private int   size;

	public LinkedList() {
		first = null;
		last  = null;
		size = 0;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int getSize(){
		return this.size;
	}
	
	public void insert(AnyType value, LinkedListIterator iter) {
		Node newNode = new Node(value);
		if (iter.hasNext()){
			newNode.setNext(iter.whereIAm.getNext());
			iter.whereIAm.setNext(newNode);
			size++;
		}
		else if (this.isEmpty()){
			iter.whereIAm = newNode;
			size++;
		}
		else{
			iter.whereIAm.setNext(newNode);
			size++;
		}
	} 

	public void insert(AnyType value, int index){ 

		Node n = new Node(value);

		if (isEmpty() && index == 0) {
			this.first = n;
			this.last = n;
			size++;
		}        
		else if (size >= index && !isEmpty()) {
			if (index == 0) { // inserting at beginning
				n.setNext(this.first);
				this.first = n;
			}
			else if (index == size) { // inserting at end
				this.last.setNext(n);
				this.last = n;
			}
			else if (index < size && index != 0) { // inserting in middle
				// first, find the node "before" where we want to insert n
				Node before = first;
				int nodeNum = 0;
				while (nodeNum < index-1) {
					before = before.getNext();
					nodeNum++;
				}
				// when we get to this point, before should have a reference to the node at index-1
				System.out.println("NodeNum: " + nodeNum);
				System.out.println("Node value: " + before.getElement());

				n.setNext(before.getNext());
				before.setNext(n);
			}
			size++;
		}
		else
			System.err.println("Error: cannot insert at that point");
		}
	
	public void delete(int index) {

		Node whereIAm = first;
		int counter;
		
		if (index == 0){
			if (whereIAm.hasNext() == false){//only one node in the list
				first = null;
				last = null;
				size--;
			}
			else{
				first = whereIAm.getNext();//first node removed
				size--;
			}
		}
		else if(index < size - 1){//average case removal
			for (counter = 1; counter < index; counter ++){
				whereIAm = whereIAm.getNext();
			}
			whereIAm.setNext(whereIAm.getNext().getNext());
			size --;
		}
		else if(index == size - 1){//removing the last node
			whereIAm = first;
			for (counter = 1; counter < index; counter ++){
				whereIAm = whereIAm.getNext();
			}
			whereIAm.setNext(null);
			size--;
			last = whereIAm;
		}
	}//Removes a Node by replacing its previous Node's Next with that of the Node to be removed.  

	public void delete(LinkedListIterator iter) {
		iter.remove();
	}    

	public AnyType get(int index) {
		Node result = first;
		int nodeNum = 0;
		while (nodeNum < index) {
			result = result.getNext();
			nodeNum++;
		}
		return result.getElement();
	}

	public AnyType get(LinkedListIterator iter) {
		return iter.whereIAm.getElement();
	}

	public LinkedListIterator iterator() {
		return new LinkedListIterator();
	}

	public int size() {
		return this.size;
	}

	public class LinkedListIterator implements java.util.Iterator<AnyType>{

	    private Node whereIAm;

	    public LinkedListIterator() {
	        whereIAm = first;
	    }

	    //next returns the next element in the list, and increments the iterator
	    public AnyType next() {
	        if (whereIAm.hasNext()) {
	          AnyType value = whereIAm.getNext().getElement();
			  whereIAm = whereIAm.getNext();
	          return value;
	        }
	        else throw new java.util.NoSuchElementException( );
	    }

	    public boolean hasNext() {
	       if(size == 0){
			   return false;
		   }
			   
		   else {
			   return whereIAm.hasNext();
		   }
	    }
		
	    public void remove() {
	        if (whereIAm == first && whereIAm.hasNext() == false){//only one node
				first = null;
				last = null;
				size --;
			}
			else if (whereIAm == first && whereIAm.hasNext()){//removing from beginning
				first = whereIAm.getNext();
				size--;
			}
			else if (whereIAm == last || whereIAm.hasNext() == false){//removing last
				whereIAm = first;
				for(int i = 0; i < size - 2; i ++){
					whereIAm = whereIAm.getNext();
				}
				whereIAm.setNext(null);
				last = whereIAm;
				size--;
			}
			else{
				Node follower = first;
				while (follower.getNext() != whereIAm){
					follower = follower.getNext();
				}
				follower.setNext(whereIAm.getNext());
				whereIAm = follower;
				size --;
			}
	    }
	}// end class Iterator

    private class Node {

        private AnyType myDataElement;
        private Node next;

        public Node(AnyType data) {
            myDataElement = data;
            next = null;
        }

        public Node(AnyType data, Node next) {
            myDataElement = data;
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }

        public AnyType getElement() {
            return this.myDataElement;
        }

        public void setNext(Node next) {
            this.next = next;
        }
            
        public void setElement(AnyType data) {
            this.myDataElement = data;
        }
		public boolean hasNext(){
			if(this.getNext() == null){
				return false;
			}
			else{
				return true;
			}
		}
    }// end class Node
}// end class LinkedList
