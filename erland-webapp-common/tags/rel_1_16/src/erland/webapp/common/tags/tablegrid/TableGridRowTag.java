package erland.webapp.common.tags.tablegrid;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

import erland.util.Log;
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

public class TableGridRowTag extends TagSupport {
    private String row;
    private String cellStyle;
    private String align;
    private String valign;
    private String cellWidth;
    private String cellHeight;

    private int rowNo;
    private TableGridTag grid;

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCellStyle() {
        return cellStyle;
    }

    public void setCellStyle(String cellStyle) {
        this.cellStyle = cellStyle;
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

    public String getTableCellStyle() {
        TableGridTag grid = getTable();
        return grid!=null?grid.getCellStyle():null;
    }
    public String getTableAlign() {
        TableGridTag grid = getTable();
        return grid!=null?grid.getAlign():null;
    }
    public String getTableValign() {
        TableGridTag grid = getTable();
        return grid!=null?grid.getValign():null;
    }
    public int getTableRowIndex() {
        TableGridTag grid = getTable();
        return grid!=null?grid.getRowIndex():0;
    }
    public String getTableCellWidth() {
        TableGridTag grid = getTable();
        return grid!=null?grid.getCellWidth():null;
    }
    public String getTableCellHeight() {
        TableGridTag grid = getTable();
        return grid!=null?grid.getCellHeight():null;
    }
    public TableGridTag getTable() {
        if(grid==null) {
            grid = (TableGridTag)findAncestorWithClass(this,TableGridTag.class);
        }
        return grid;
    }

    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if(Log.isEnabled(this,Log.DEBUG)) {
                Log.println(this,StringUtil.beanToString(this,null,TagSupport.class,true));
            }
            rowNo=StringUtil.asInteger(row,new Integer(0)).intValue();
            if(rowNo==getTableRowIndex()) {
                String cellStyle = this.cellStyle!=null?this.cellStyle:getTableCellStyle();
                String align = this.align!=null?this.align:getTableAlign();
                String valign = this.valign!=null?this.valign:getTableValign();
                String width = this.cellWidth!=null?this.cellWidth:getTableCellWidth();
                String height = this.cellHeight!=null?this.cellHeight:getTableCellHeight();
                out.write("<td"+(cellStyle!=null?" class=\""+cellStyle+"\"":"")+(align!=null?" align=\""+align+"\"":"")+(valign!=null?" valign=\""+valign+"\"":"")+(width!=null?" width=\""+width+"\"":"")+(height!=null?" height=\""+height+"\"":"")+">");
                return EVAL_BODY_INCLUDE;
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        rowNo=-1;
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            if(rowNo>=0) {
                out.write("</td>");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return EVAL_PAGE;
    }

    public void release() {
        super.release();
        row = null;
        cellStyle = null;
        align = null;
        valign = null;
        cellWidth = null;
        cellHeight = null;

        rowNo = 0;
        grid = null;
    }
}