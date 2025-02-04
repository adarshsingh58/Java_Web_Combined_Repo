package InnerClass.NestedClassesAndInterface.www.ClassInsideInterface;

public class ClassInsideInterfaceImpl implements ClassInsideInterface{

	@Override
	public void hello(Name name) {
		System.out.println(name.firstName);
		System.out.println(name.lastName);
	}
	public static void main(String[] args) {
		ClassInsideInterface classInsideInterface=new ClassInsideInterfaceImpl();
		classInsideInterface.hello(new Name());
	}

}
