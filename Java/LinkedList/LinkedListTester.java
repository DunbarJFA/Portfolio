import static org.junit.Assert.*;
import org.junit.*;
/*
*This is an instructor-provided tester class for linked list 
*implementations.
*
*This code is the product of the University of New Orleans,
*but it is provided in order to be used as a tool for verifying 
*the quality of the accompanying LinkedList implentation.
*
*This code is intended for use with the JUnit testing framework.
*/
public class LinkedListTester {

    private LinkedList<Integer> list1;
    private LinkedList<String>  list2;

    @Before
    public void setup() {
        list1 = new LinkedList<Integer>();
        list2 = new LinkedList<String>();
    }

    @Test
    public void testInitialState() {
        assertTrue(list1.size() == 0);
        assertTrue(list2.size() == 0);
    }

    @Test
    public void testAddTenThings() {
        for (int i=0; i<10; i++)
          list1.insert(i,0);
        for (int i=0; i<10; i++)
          list2.insert(((Integer)i).toString(),0);
        assertTrue(list1.size() == 10);
        assertTrue(list2.size() == 10);
        assertTrue(list1.get(1) == 8);
        assertTrue(list2.get(2).equals("7"));
    }


    @Test
    public void testInitialStateOfIteratorOnEmptyList() {
        LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();
        LinkedList<String>.LinkedListIterator  it2 = list2.iterator();
        assertFalse(it1.hasNext());
        assertFalse(it2.hasNext());
    }


    @Test
    public void simpleIteratorTest() {
        for (int i=0; i<10; i++)
          list1.insert(i,0);
        for (int i=0; i<10; i++)
          list2.insert(((Integer)i).toString(),0);
        LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();
        LinkedList<String>.LinkedListIterator  it2 = list2.iterator();

        int counter = 8;
        while (it1.hasNext()) {
            assertEquals((Integer)counter, (Integer)it1.next());
            counter--;
        }

    }

	@Test
	public void addRepeatedlyToTheEnd(){
		LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();
		LinkedList<String>.LinkedListIterator  it2 = list2.iterator();
		
		int count = 0;
		
		for (int i=0; i<10; i++){
			list1.insert(i,it1);
			list2.insert(((Integer)i).toString(),it2);
			count ++;	
		}
		assertEquals(count,list1.getSize());
		assertEquals(count,list2.getSize());
		
		assertEquals(list1.getSize() - 1,9);
        assertEquals(((Integer)list2.getSize()).toString(),"10");
	}
	
	@Test
	public void deleteTest(){
		LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();
		LinkedList<String>.LinkedListIterator  it2 = list2.iterator();
		
		list1.insert(5,0);
		list2.insert("5",0);
		
		list1.delete(0);
		list2.delete(0);
		
		assertTrue(list1.isEmpty());
		assertTrue(list2.isEmpty());
		
		for (int i=0; i<10; i++){
			list1.insert(i,i);
			list2.insert(((Integer)i).toString(),i);
		}
		
		list1.delete(0);
		list2.delete(0);
		list1.delete(5);
		list2.delete(5);
		list1.delete(7);
		list2.delete(7);
		
		assertEquals(7,list1.getSize());
		assertEquals(7,list2.getSize());
		
		LinkedList<Integer>.LinkedListIterator test1 = list1.iterator();
		LinkedList<String>.LinkedListIterator  test2 = list2.iterator();
		
		assertEquals((Integer)2,(Integer)test1.next());
		assertEquals("2",test2.next());
		
		int testVal = 0;
		
		while (testVal != 3){
			testVal = test1.next();
			test2.next();
		}
		assertEquals((Integer)4,((Integer)test1.next()));
		assertEquals("4",test2.next());
		
		while (testVal != 7){
			testVal = test1.next();
			test2.next();
		}
		assertEquals((Integer)8,(Integer)test1.next());
		assertEquals("8",test2.next());
	} 
}

