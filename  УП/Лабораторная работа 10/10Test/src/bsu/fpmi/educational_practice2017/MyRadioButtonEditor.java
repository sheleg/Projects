/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsu.fpmi.educational_practice2017;

import java.beans.PropertyEditorSupport;
import javax.swing.JRadioButton;

/**
 *
 * @author vladasheleg
 */
public class MyRadioButtonEditor extends PropertyEditorSupport {
	
	@Override
	public String[] getTags() {
		return new String[] {"RadioButton"};
	}
	
	@Override
	public void setAsText(String s) {
		setValue(new JRadioButton(s));
	}

	@Override 
	public String getAsText() {
		return ((JRadioButton)getValue()).getText();
	}

	@Override
	public String getJavaInitializationString() {
		return "";
	}	
}
