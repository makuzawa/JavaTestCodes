package enumtest;

import java.util.Enumeration;
import java.util.Hashtable;

public class EnumrationTest {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Hashtable<Integer, Integer> table = new Hashtable<Integer,Integer>();
		int i;
		for(i=0;i<6;i++) {
			table.put(i, i);
		}

		Enumeration<Integer> en =table.elements();
		i=4;
		while(en.hasMoreElements()) {
			System.out.println(en);
			System.out.println(en.nextElement().intValue());
			table.remove(i);
			//table.put(i, i++);
		}
		
	
		System.out.println(en);
		
		
	}

}
