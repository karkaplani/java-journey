package datastructure.queue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

//Methods to test: 

//offer -> Inserts without violating capacity restrictions +
//poll -> Removes the head of the queue. Returns null if queue empty +
//peek -> Retrieves the head of the queue. Returns null if empty +
//size +
//isEmpty +
//contains +
//remove -> Throws exception if queue is empty +
//clear -> Removes all the elements +
//iterator  +


public class TestQueue {

	private Queue<String> queue;
	
	@BeforeEach
	void setup() {
		queue = new LinkedList<>();
	}
	
	@AfterEach
	void teardown() {
		queue = null;
	}
	
	@Test
	public void testOfferForEmpty() { //Offer edge case
		assertTrue(queue.isEmpty());
		queue.offer("Hi");
		assertFalse(queue.isEmpty());
	}
	
	@Test
	public void testOfferForOneElement() { //Offer edge case
		queue.offer("Head");
		assertEquals(queue.size(), 1);
		queue.offer("Tail");
		assertSame(queue.peek(), "Head"); 
	}
	
	@Test
	public void testOfferForManyElements() { //Offer normal case
		queue.add("Hey");
		assertFalse(queue.isEmpty()); 
		queue.offer("Hi");
		assertEquals(queue.size(), 2);
	}
	
	@Test
	public void testOfferForNullQueue() { //Offer error case
		String object = null;
		assertTrue(queue.isEmpty());
		assertTrue(queue.offer(object));
		assertNull(queue.peek());
	}
	
	@Test
	public void testPollForEmpty() { //Poll edge
		assertTrue(queue.isEmpty());
		assertEquals(null, queue.poll());
	}
	
	@Test
	public void testPollForOneElement() { //Poll edge
		queue.offer("Head");
		assertSame(queue.poll(), "Head");
	}
	
	@Test
	public void testPollForManyElements() { //Poll normal
		queue.add("Head");
		queue.add("Second Head");
		queue.add("Tail");
		assertFalse(queue.isEmpty());
		queue.poll(); //Removes the head
		assertSame(queue.peek(), "Second Head"); //New head
	}
	
	//There is no exception seem to be thrown in the documentation of poll. 
	
	@Test
	public void testPeekForEmpty() { //Peek edge
		assertTrue(queue.isEmpty());
		assertEquals(null, queue.peek());
	}
	
	@Test
	public void testPeekForOneElement() { //Peek edge
		queue.add("Head&Tail");
		assertSame(queue.peek(), "Head&Tail"); //Head and tail is the same for the queue of one element.
	}
	
	@Test
	public void testPeekForManyElements() { //Peek normal
		queue.add("Head");
		queue.add("Tail");
		assertFalse(queue.isEmpty());
		assertSame(queue.peek(), "Head");
	}
	
	//Peek doesn't throw an exception as well. Returns null if the queue is empty.
	
	@Test
	public void testSizeForEmpty() { //Size edge
		assertTrue(queue.isEmpty());
		assertEquals(0, queue.size());
	}	
	
	@Test
	public void testSizeForOneElement() { //Size edge
		queue.add("Head");
		assertEquals(1, queue.size());
	}	
	
	@Test
	public void testSizeForManyElements() { //Size normal
		queue.add("Head");
		queue.add("Tail");
		assertFalse(queue.isEmpty()); 
		assertEquals(queue.size(), 2);
	}
	
	//Size doesn't throw an exception as well. Returns 0 if the queue is empty.

	@Test
	public void tesIsEmptyForEmpty() { //isEmpty edge
		assertTrue(queue.isEmpty());
	}	
	
	@Test
	public void tesIsEmptyForOneElement() { //isEmpty normal
		queue.add("Head"); 
		assertFalse(queue.isEmpty());
	}	
	
	@Test
	public void tesIsEmptyForManyElements() { //isEmpty normal
		queue.add("Head"); 
		queue.add("Tail");
		assertFalse(queue.isEmpty());
	}	
	
	//isEmpty doesn't throw an exception as well. Returns either true or false.
	
	@Test
	public void testContainsForEmpty() { //Contains edge
		assertTrue(queue.isEmpty());
		String object = "Hi";
		assertFalse(queue.contains(object));
	}
	
	@Test
	public void testContainsForOneElement() { //Contains edge
		String object = "Hi";
		queue.add(object);
		assertTrue(queue.contains(object));
	}
	
	
	@Test
	public void testContainsForNonEmpty() { //Contains normal
		String object = "Head";
		queue.add(object);
		queue.add("Tail");
		assertFalse(queue.isEmpty());
		assertTrue(queue.contains("Head"));
	}
	
	@Test
	public void testContainsForNullQueue() { //Contains exception case (Null pointer)
		queue = null;
		assertThrows(NullPointerException.class, () -> queue.contains("Hi"));
	}
	
	
	@Test
	public void testRemoveForOneElement() { //Remove edge
		String testObject = "Head";
		queue.add(testObject);
		assertFalse(queue.isEmpty());
		queue.remove(testObject);
		assertFalse(queue.contains(testObject));
	}
	
	
	@Test
	public void testRemoveForManyElements() { //Remove normal
		String testObject = "Head";
		queue.add(testObject);
		queue.add("Tail");
		assertFalse(queue.isEmpty());
		queue.remove(testObject);
		assertFalse(queue.contains(testObject));
	}	
	
	@Test
	public void testRemoveForEmpty() { //Remove exception
		assertTrue(queue.isEmpty());
		assertThrows(NoSuchElementException.class, () -> queue.remove());
	}
	
	@Test
	public void testClearForEmpty() { //Clear edge
		assertTrue(queue.isEmpty());
		queue.clear();
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testClearForOneElement() { //Clear normal
		queue.add("Head");
		assertFalse(queue.isEmpty());
		queue.clear();
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testClearForManyElements() { //Clear normal
		queue.add("Head");
		queue.add("Tail");
		assertFalse(queue.isEmpty());
		queue.clear();
		assertTrue(queue.isEmpty());
	}
	
	//In the doc it seems that clear throws an exception if it's not supported by the collection type. Queue supports it, so there's no exception case for this
	
	@Test
	public void testIteratorForEmpty() { //Iterator edge and exception (No such element)
		assertTrue(queue.isEmpty());
		Iterator<String> it = queue.iterator();
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	public void testIteratorForManyElements() {
		
		String[] elements = {"Hi", "Hey", "Hello"};
		
		queue.add(elements[0]);
		queue.add(elements[1]);
		queue.add(elements[2]);
		
		Iterator<String> it = queue.iterator();
		int i = 0;
		
		while(it.hasNext()) {
			assertEquals(it.next(), elements[i]);
			i++;
		}
	}
}

