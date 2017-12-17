public class NativeMethods 
{
	static
	{
		System.loadLibrary("NativeMethods");
	}
	public static native void openKey (String subKey);
	public static native void createKey (String subKey);
	public static native void closeKey ();
	public static native void deleteValue (String place);
	public static native void setValue (String place, String data);
}
