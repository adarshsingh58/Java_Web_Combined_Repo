package InnerClass.NormalInnerClass.CallFromNonStaticMethod;

public class OtherClass {

	public static void main(String[] args) {
		Outer o=new Outer();
		Outer.Inner inner=o.new Inner();
		inner.hello();
	}
}
