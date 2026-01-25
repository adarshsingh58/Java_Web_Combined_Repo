package ComparableComparator.Comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Person> list=new ArrayList<>();
		/*First Person Object */
		Person person1=new Person();
		person1.setAge(24);
		person1.setFirstName("Adarsh");
		person1.setLastName("Singh");
		
		/*Second Person Object */
		Person person2=new Person();
		person2.setAge(25);
		person2.setFirstName("Batman");
		person2.setLastName("Zing");
		
		
		/*3rd Person Object */
		Person person3=new Person();
		person3.setAge(27);
		person3.setFirstName("A");
		person3.setLastName("Xcon");
		
		/*4th Person Object */
		Person person4=new Person();
		person4.setAge(12);
		person4.setFirstName("OO");
		person4.setLastName("pon");
		
		
		list.add(person4);
		list.add(person2);
		list.add(person1);
		list.add(person3);
		
		System.out.println("data in list:"+list.size());
		
		System.out.println("Before Sort");
		for(Person person:list)
		{
			System.out.println("Name :"+person.getFirstName());
		}
		
		Collections.sort(list, (o1,o2)->  o1.getFirstName().compareTo(o2.getFirstName()));
		
		System.out.println("After Sort");
		for(Person person:list)
		{
			System.out.println("Name :"+person.getFirstName());
		}
		
		
		
	}
}
