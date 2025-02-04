package InnerClass.NormalInnerClass.www.main;

public class Outer {
	
	public static void main(String[] args) {
		Outer outer=new Outer();
		Outer.Inner inner=outer.new Inner();
		inner.hello();
	}
	
	public class Inner{
		
		public void hello()
		{
			System.out.println("Hello, I am Inner Class");
		}
	}

}
