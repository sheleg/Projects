public class Test
{
	public static void main(String[] args)
	{
		String l = "C:\13";
		NativeMethods.createKey(l);
		NativeMethods.openKey(l);
		NativeMethods.setValue("9group","Sheleg");
		NativeMethods.closeKey();
	}
}