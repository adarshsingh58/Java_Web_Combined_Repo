package InnerClass.NormalInnerClass.main.CallingOuterInstaneVarFromInnerClas;

public class Outer {
	
	static int b=4;
	int a=5;
	
	public static void main(String[] args) {
		Outer outer=new Outer();
		Outer.Inner inner=outer.new Inner();
		inner.hello();
	}
	
	/**
	 * Inner class cannot define static members in itself but can access otehr static members
	 * @author adarsh.a.singh
	 *
	 */
	public class Inner{
		int a=6;
		
		public void hello()
		{
			int a=7;
			System.out.println("Static Var from Outer class b="+Outer.b);
			System.out.println("Instance Var from Outer class a="+Outer.this.a);
			System.out.println("Instance Var from Inner class a="+this.a);
			System.out.println("Method/Local Var from Inner class a="+a);
		}
	}

}
