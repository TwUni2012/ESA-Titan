/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esa.titan.soap.weatherservice.city;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Vylyb
 */
public class CityConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String name) {
        return name;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((City)value).getName();
    }
    
}
