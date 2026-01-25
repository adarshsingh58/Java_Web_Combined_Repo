package InnerClass.NormalInnerClass.main.CallingDifferentLeelVarFromInnerClass;

public class Outer {
	
	int a=5;
	
	public static void main(String[] args) {
		Outer outer=new Outer();
		Outer.Inner inner=outer.new Inner();
		inner.hello();
	}
	
	public class Inner{
		
		public void hello()
		{
			System.out.println("Hello. Var a="+a);
		}
	}

}
