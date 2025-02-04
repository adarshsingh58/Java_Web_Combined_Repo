package ComparableComparator.ComparableDemo.main;

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
		
		list.add(person2);
		list.add(person1);
		
		System.out.println("data in list:"+list.size());
		
		System.out.println("Before Sort");
		for(Person person:list)
		{
			System.out.println("Name :"+person.getFirstName());
		}
		
		Collections.sort(list);
		
		System.out.println("After Sort");
		for(Person person:list)
		{
			System.out.println("Name :"+person.getFirstName());
		}
		
		
		
	}
}
