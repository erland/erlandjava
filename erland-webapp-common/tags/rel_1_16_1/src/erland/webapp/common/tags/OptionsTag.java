package erland.webapp.common.tags;

import org.apache.struts.util.ResponseUtils;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.taglib.html.SelectTag;
import org.apache.struts.taglib.html.Constants;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.jsp.JspException;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;

/*
 * Copyright (C) 2004 Erland Isaksson (erland_i@hotmail.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

public class OptionsTag extends org.apache.struts.taglib.html.OptionsTag {
    protected String labelKeyProperty = null;

    public String getLabelKeyProperty() {
        return labelKeyProperty;
    }

    public void setLabelKeyProperty(String labelKeyProperty) {
        this.labelKeyProperty = labelKeyProperty;
    }

    public int doEndTag() throws JspException {

        // Acquire the select tag we are associated with
        SelectTag selectTag = (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);
        if (selectTag == null) {
            throw new JspException(messages.getMessage("optionsTag.select"));
        }
        StringBuffer sb = new StringBuffer();

        // If a collection was specified, use that mode to render options
        if (collection != null) {
            Iterator collIterator = getIterator(collection, null);
            while (collIterator.hasNext()) {

                Object bean = collIterator.next();
                Object value = null;
                Object label = null;

                try {
                    value = PropertyUtils.getProperty(bean, property);
                    if (value == null) {
                        value = "";
                    }
                } catch (IllegalAccessException e) {
                    throw new JspException(
                        messages.getMessage("getter.access", property, collection));
                } catch (InvocationTargetException e) {
                    Throwable t = e.getTargetException();
                    throw new JspException(
                        messages.getMessage("getter.result", property, t.toString()));
                } catch (NoSuchMethodException e) {
                    throw new JspException(
                        messages.getMessage("getter.method", property, collection));
                }

                try {
                    if (labelProperty != null) {
                        label = PropertyUtils.getProperty(bean, labelProperty);
                    } else if (labelKeyProperty != null) {
                        String property = (String)PropertyUtils.getProperty(bean, labelKeyProperty);
                        label = RequestUtils.message(pageContext,null,null,property);
                    } else {
                        label = value;
                    }

                    if (label == null) {
                        label = "";
                    }
                } catch (IllegalAccessException e) {
                    throw new JspException(
                        messages.getMessage("getter.access", labelProperty, collection));
                } catch (InvocationTargetException e) {
                    Throwable t = e.getTargetException();
                    throw new JspException(
                        messages.getMessage("getter.result", labelProperty, t.toString()));
                } catch (NoSuchMethodException e) {
                    throw new JspException(
                        messages.getMessage("getter.method", labelProperty, collection));
                }

                String stringValue = value.toString();
                addOption(sb, stringValue, label.toString(), selectTag.isMatched(stringValue));

            }

        }

        // Otherwise, use the separate iterators mode to render options
        else {

            // Construct iterators for the values and labels collections
            Iterator valuesIterator = getIterator(name, property);
            Iterator labelsIterator = null;
            if ((labelName == null) && (labelProperty == null)) {
                labelsIterator = getIterator(name, property); // Same coll.
            } else {
                labelsIterator = getIterator(labelName, labelProperty);
            }

            // Render the options tags for each element of the values coll.
            while (valuesIterator.hasNext()) {
                Object valueObject = valuesIterator.next();
                if (valueObject == null) {
                    valueObject = "";
                }
                String value = valueObject.toString();
                String label = value;
                if (labelsIterator.hasNext()) {
                    Object labelObject = labelsIterator.next();
                    if (labelObject == null) {
                        labelObject = "";
                    }
                    label = labelObject.toString();
                }
                addOption(sb, value, label, selectTag.isMatched(value));
            }
        }

        // Render this element to our writer
        ResponseUtils.write(pageContext, sb.toString());

        // Evaluate the remainder of this page
        return EVAL_PAGE;
    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        labelKeyProperty = null;
    }

}
