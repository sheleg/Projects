
package bsu.fpmi.educational_practice;

import java.beans.PropertyEditorSupport;
import javax.swing.JButton;

public class MyButtonEditor extends PropertyEditorSupport {
	
	@Override
	public String[] getTags() {
		return new String[] {"button", "button1", "button2"};
	}
	
	@Override
	public void setAsText(String s) {
		setValue(new JButton(s));
	}

	@Override 
	public String getAsText() {
		return ((JButton)getValue()).getText();
	}

	@Override
	public String getJavaInitializationString() {
		return "";
	}	
}
