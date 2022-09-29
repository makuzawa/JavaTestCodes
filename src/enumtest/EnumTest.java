package enumtest;

import static org.junit.Assert.*;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnumTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		int max = 100000;
		//	int max = 8;
		Hashtable<Integer, Integer> table = new Hashtable<Integer,Integer>();

		int i;
		int maxdatasize = (max);
		for(i=0;i<maxdatasize;i++) {
			table.put(i, i);
		}
		Object lock = new Object();
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO 自動生成されたメソッド・スタブ
				try {
					synchronized(lock) {
						lock.wait();
					}
				} catch (InterruptedException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
				int counter = maxdatasize;
				while(true) {
					System.out.println("Thread="+counter);
					table.put(counter, counter++);
					//Thread.sleep(100);

				}


			}

		});

		t.start();



		LinkedList<Integer> expectedList = new LinkedList<Integer>();
		Enumeration<Integer> en =table.elements();

		while(en.hasMoreElements()) {
			int v = en.nextElement().intValue();
			expectedList.add(v);

		}


		en =table.elements();
		ListIterator<Integer> ite = expectedList.listIterator();

		en.hasMoreElements();

		synchronized(lock) {
			lock.notifyAll();
		}


		//	table.put(maxdatasize, maxdatasize);
		while(en.hasMoreElements()) {

			int v = en.nextElement().intValue();
			System.out.println(v);
			assertEquals(ite.next(), (Integer)v);



		}


	}

}
