/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsu.fpmi.educational_practice2017;

import java.beans.PropertyEditorSupport;
import javax.swing.JLabel;

/**
 *
 * @author vladasheleg
 */
public class MyLabelEditor extends PropertyEditorSupport {
	
	@Override
	public String[] getTags() {
		return new String[] {"label"};
	}
	
	@Override
	public void setAsText(String s) {
		setValue(new JLabel(s));
	}

	@Override 
	public String getAsText() {
		return ((JLabel)getValue()).getText();
	}

	@Override
	public String getJavaInitializationString() {
		return "";
	}
	
}