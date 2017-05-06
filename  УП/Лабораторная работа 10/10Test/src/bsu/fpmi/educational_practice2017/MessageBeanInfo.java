/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bsu.fpmi.educational_practice2017;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 *
 * @author vladasheleg
 */
public class MessageBeanInfo {
    static PropertyDescriptor prop(String name, String description) {
	try {
	    PropertyDescriptor p =
		new PropertyDescriptor(name, bsu.fpmi.educational_practice2017.Message.class);
	    p.setShortDescription(description);
	    return p;
	}
	catch(IntrospectionException e) { return null; } 
    }
    
    static PropertyDescriptor[] props = {
	prop("messageText", "The message text that appears in the bean body"),
	prop("HelloLabel", "The label for the Hello button"),
    };
    
    public PropertyDescriptor[] getPropertyDescriptors() { return props; }
    
    public int getDefaultPropertyIndex() { return 0; }
}
