package erland.webapp.common.tags.tablegrid;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.io.IOException;

import erland.util.StringUtil;

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

public class TableGridTag extends TagSupport {
    /** Logging instance */
    private static Log LOG = LogFactory.getLog(TableGridTag.class);
    private String rows;
    private String cols;
    private String nameRowsCols;
    private String rowsProperty;
    private String colsProperty;
    private String tableStyle;
    private String rowIterationsProperty;
    private String rowIterations;
    private String columnIterations;
    private String columnIterationsProperty;
    private String cellStyle;
    private String rowStyle;
    private String width;
    private String height;
    private String cellWidth;
    private String cellHeight;
    private String align;
    private String valign;
    private String name;
    private String property;
    private String id;
    private String indexId;

    private Object[] iterateObject;
    private int index;
    private int rowIndex;
    private int columnIndex;
    private int noOfRows;
    private int noOfCols;
    private int noOfRowIterations;
    private int noOfColumnIterations;


    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public String getNameRowsCols() {
        return nameRowsCols;
    }

    public void setNameRowsCols(String nameRowsCols) {
        this.nameRowsCols = nameRowsCols;
    }

    public String getRowsProperty() {
        return rowsProperty;
    }

    public void setRowsProperty(String rowsProperty) {
        this.rowsProperty = rowsProperty;
    }

    public String getColsProperty() {
        return colsProperty;
    }

    public void setColsProperty(String colsProperty) {
        this.colsProperty = colsProperty;
    }

    public String getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(String tableStyle) {
        this.tableStyle = tableStyle;
    }

    public String getRowIterations() {
        return rowIterations;
    }

    public void setRowIterations(String rowIterations) {
        this.rowIterations = rowIterations;
    }

    public String getColumnIterations() {
        return columnIterations;
    }

    public void setColumnIterations(String columnIterations) {
        this.columnIterations = columnIterations;
    }

    public String getColumnIterationsProperty() {
        return columnIterationsProperty;
    }

    public void setColumnIterationsProperty(String columnIterationsProperty) {
        this.columnIterationsProperty = columnIterationsProperty;
    }

    public String getRowIterationsProperty() {
        return rowIterationsProperty;
    }

    public void setRowIterationsProperty(String rowIterationsProperty) {
        this.rowIterationsProperty = rowIterationsProperty;
    }

    public String getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(String cellStyle) {
        this.cellStyle = cellStyle;
    }

    public String getRowStyle() {
        return rowStyle;
    }

    public void setRowStyle(String rowStyle) {
        this.rowStyle = rowStyle;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(String cellWidth) {
        this.cellWidth = cellWidth;
    }

    public String getCellHeight() {
        return cellHeight;
    }

    public void setCellHeight(String cellHeight) {
        this.cellHeight = cellHeight;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getValign() {
        return valign;
    }

    public void setValign(String valign) {
        this.valign = valign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if(LOG.isTraceEnabled()) {
                LOG.trace(StringUtil.beanToString(this,null,TagSupport.class,true));
            }
            Object bean = (Object) pageContext.findAttribute(name);
            if(bean!=null && property!=null) {
                Object beanProperty = null;
                try {
                    beanProperty = (Object) PropertyUtils.getProperty(bean,property);
                } catch (IllegalAccessException e) {
                    LOG.error("Unable to get property "+property,e);
                } catch (InvocationTargetException e) {
                    LOG.error("Unable to get property "+property,e);
                } catch (NoSuchMethodException e) {
                    LOG.error("Unable to get property "+property,e);
                }
                if(beanProperty!=null) {
                    bean = beanProperty;
                }
            }
            String rows = this.rows;
            String cols = this.cols;
            if((rows==null || cols==null) && nameRowsCols!=null) {
                Object rowsColsBean = (Object) pageContext.findAttribute(nameRowsCols);
                if(rowsColsBean!=null && rows==null && rowsProperty!=null) {
                    try {
                        Object rowsValue = (Object) PropertyUtils.getProperty(rowsColsBean,rowsProperty);
                        if(rowsValue!=null) {
                            rows = rowsValue.toString();
                        }
                    } catch (IllegalAccessException e) {
                        LOG.error("Unable to get property "+rowsProperty,e);
                    } catch (InvocationTargetException e) {
                        LOG.error("Unable to get property "+rowsProperty,e);
                    } catch (NoSuchMethodException e) {
                        LOG.error("Unable to get property "+rowsProperty,e);
                    }
                }
                if(rowsColsBean!=null && cols==null && colsProperty!=null) {
                    try {
                        Object colsValue = (Object) PropertyUtils.getProperty(rowsColsBean,colsProperty);
                        if(colsValue!=null) {
                            cols = colsValue.toString();
                        }
                    } catch (IllegalAccessException e) {
                        LOG.error("Unable to get property "+colsProperty,e);
                    } catch (InvocationTargetException e) {
                        LOG.error("Unable to get property "+colsProperty,e);
                    } catch (NoSuchMethodException e) {
                        LOG.error("Unable to get property "+colsProperty,e);
                    }
                }
            }
            String rowIterations = this.rowIterations;
            String columnIterations = this.columnIterations;
            if((rowIterations==null || columnIterations==null) && nameRowsCols!=null) {
                Object rowsColsBean = (Object) pageContext.findAttribute(nameRowsCols);
                if(rowsColsBean!=null && rowIterations==null && rowIterationsProperty!=null) {
                    try {
                        Object rowIterationsValue = (Object) PropertyUtils.getProperty(rowsColsBean,rowIterationsProperty);
                        if(rowIterationsValue!=null) {
                            rowIterations = rowIterationsValue.toString();
                        }
                    } catch (IllegalAccessException e) {
                        LOG.error("Unable to get property "+rowIterationsProperty,e);
                    } catch (InvocationTargetException e) {
                        LOG.error("Unable to get property "+rowIterationsProperty,e);
                    } catch (NoSuchMethodException e) {
                        LOG.error("Unable to get property "+rowIterationsProperty,e);
                    }
                }
                if(rowsColsBean!=null && columnIterations==null && columnIterationsProperty!=null) {
                    try {
                        Object columnIterationsValue = (Object) PropertyUtils.getProperty(rowsColsBean,columnIterationsProperty);
                        if(columnIterationsValue!=null) {
                            columnIterations = columnIterationsValue.toString();
                        }
                    } catch (IllegalAccessException e) {
                        LOG.error("Unable to get property "+columnIterationsProperty,e);
                    } catch (InvocationTargetException e) {
                        LOG.error("Unable to get property "+columnIterationsProperty,e);
                    } catch (NoSuchMethodException e) {
                        LOG.error("Unable to get property "+columnIterationsProperty,e);
                    }
                }
            }

            if(bean instanceof Object[]) {
                iterateObject=(Object[])bean;
            }else if(bean instanceof Collection) {
                iterateObject=((Collection)bean).toArray();
            }else {
                throw new IllegalArgumentException("Bean is not an array or collection");
            }
            Integer rowsValue=StringUtil.asInteger(rows,null);
            if(rowsValue==null || rowsValue.intValue()<1) {
                noOfRows=iterateObject.length;
            }else {
                noOfRows=rowsValue.intValue();
            }
            Integer colsValue=StringUtil.asInteger(cols,null);
            if(colsValue==null || colsValue.intValue()<1) {
                noOfCols=iterateObject.length;
            }else {
                noOfCols=colsValue.intValue();
            }
            noOfRowIterations=StringUtil.asInteger(rowIterations,new Integer(1)).intValue();
            noOfColumnIterations=StringUtil.asInteger(columnIterations,new Integer(1)).intValue();
            if(iterateObject.length>0) {
                index = 0;
                columnIndex = 0;
                rowIndex = 0;
                if(iterateObject[index]!=null) {
                    pageContext.setAttribute(id,iterateObject[index]);
                    if(indexId!=null) {
                        pageContext.setAttribute(indexId,new Integer(index));
                    }
                }else {
                    pageContext.removeAttribute(id);
                    if(indexId!=null) {
                        pageContext.removeAttribute(indexId);
                    }
                }
                out.write("<table"+(tableStyle!=null?" class=\""+tableStyle+"\"":"")+(width!=null?" width=\""+width+"\"":"")+(height!=null?" height=\""+height+"\"":"")+">");
                out.write("<tr"+(rowStyle!=null?" class=\""+rowStyle+"\"":"")+">");
                if(noOfRowIterations<=1 && noOfColumnIterations<=1) {
                    out.write("<td"+(cellStyle!=null?" class=\""+cellStyle+"\"":"")+(align!=null?" align=\""+align+"\"":"")+(valign!=null?" valign=\""+valign+"\"":"")+(cellWidth!=null?" width=\""+cellWidth+"\"":"")+(cellHeight!=null?" height=\""+cellHeight+"\"":"")+">");
                }
                return EVAL_BODY_INCLUDE;
            }
        } catch (IOException e) {
            LOG.error("Unable to write html",e);
        }
        return SKIP_BODY;
    }

    public int doAfterBody() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if(LOG.isTraceEnabled()) {
                LOG.trace("doAfterBody start: "+StringUtil.objectToString(this,null,TagSupport.class,true));
            }
            if(noOfRowIterations<=1 && noOfColumnIterations<=1) {
                out.write("</td>");
            }
            index++;
            if(index%noOfCols==0) {
                columnIndex++;
                if(columnIndex<noOfColumnIterations) {
                    index--;
                }else {
                    columnIndex=0;
                    out.write("</tr>");
                    rowIndex++;
                    if(rowIndex<noOfRowIterations) {
                        do {
                            index--;
                        }while(index%noOfCols>0);
                    }else {
                        rowIndex=0;
                    }
                }
            }else if(index>=iterateObject.length) {
                columnIndex++;
                if(columnIndex<noOfColumnIterations) {
                    index--;
                }else {
                    columnIndex=0;
                    out.write("</tr>");
                    rowIndex++;
                    if(rowIndex<noOfRowIterations) {
                        do {
                            index--;
                        }while(index%noOfCols>0);
                    }else {
                        rowIndex=0;
                    }
                }
            }else {
                columnIndex++;
                if(columnIndex<noOfColumnIterations) {
                    index--;
                }else {
                    columnIndex=0;
                }
            }

            if(index<iterateObject.length && index<(noOfCols*noOfRows)) {
                if(columnIndex==0 && index%noOfCols==0) {
                    out.write("<tr"+(rowStyle!=null?" class=\""+rowStyle+"\"":"")+">");
                }
                if(noOfRowIterations<=1 && noOfColumnIterations<=1) {
                    out.write("<td"+(cellStyle!=null?" class=\""+cellStyle+"\"":"")+(align!=null?" align=\""+align+"\"":"")+(valign!=null?" valign=\""+valign+"\"":"")+(cellWidth!=null?" width=\""+cellWidth+"\"":"")+(cellHeight!=null?" height=\""+cellHeight+"\"":"")+">");
                }
                if(iterateObject[index]!=null) {
                    pageContext.setAttribute(id,iterateObject[index]);
                    if(indexId!=null) {
                        pageContext.setAttribute(indexId,new Integer(index));
                    }
                }else {
                    pageContext.removeAttribute(id);
                    if(indexId!=null) {
                        pageContext.removeAttribute(indexId);
                    }
                }
                if(LOG.isTraceEnabled()) {
                    LOG.trace("doAfterBody end: "+StringUtil.objectToString(this,null,TagSupport.class,true));
                }
                return EVAL_BODY_AGAIN;
            }
        } catch (IOException e) {
            LOG.error("Unable to write html",e);
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if(iterateObject!=null && iterateObject.length>0) {
                out.write("</table>");
            }
        } catch (IOException e) {
            LOG.error("Unable to write html",e);
        }
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        rows = null;
        cols = null;
        nameRowsCols = null;
        rowsProperty = null;
        colsProperty = null;
        tableStyle = null;
        rowIterations = null;
        columnIterations = null;
        rowIterationsProperty = null;
        columnIterationsProperty = null;
        rowStyle = null;
        cellStyle = null;
        width = null;
        height = null;
        cellWidth = null;
        cellHeight = null;
        align = null;
        valign = null;
        name = null;
        property = null;
        id = null;
        indexId = null;

        iterateObject = null;
        index = 0;
        rowIndex = 0;
        columnIndex = 0;
        noOfRows = 0;
        noOfCols = 0;
        noOfRowIterations = 0;
        noOfColumnIterations = 0;
    }
}