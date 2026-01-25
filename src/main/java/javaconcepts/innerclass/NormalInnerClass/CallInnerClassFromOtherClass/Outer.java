package InnerClass.NormalInnerClass.CallInnerClassFromOtherClass;

public class Outer {
	
	public static void main(String[] args) {
		Outer outer=new Outer();
		outer.callInnerToSayHello();
	}
	public void callInnerToSayHello()
	{
		Inner inner=new Inner();
		inner.hello();
	}
	
	public class Inner{
		
		public void hello()
		{
			System.out.println("Hello, I am Inner Class");
		}
	}

}
