<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.gallery.Gallery,
                                erland.webapp.gallery.gallery.ViewGalleryCommand"%>
<p class="normal">This will import your picture gallery from a text file which has
been exported from the <a class="bold-link" href="http://www.photools.com/" target="_blank">IMatch</a> image management tool.
<p class="normal">The text file should be created in IMatch as follows:
<ol>
<li>Select the category or database you want to export
<li>Choose menu: Database->Import and Export...
<li>Choose the export module: Export to Text Format
<li>Select the following image attributes and change nothing else
<ul>
<li>Full File Name
<li>Last Modified
<li>Image Object Identifier (OID)
<li>Categories (fully qualified)
</ul>
</li>
<li>Save the export file on a web server where this web service can access it. Enter the path to the file below
</ol>
<p class="normal">If your picture gallery should be available on internet for other users the pictures must also be accessible on a internet server somewhere. The paths used in IMatch can be re-mapped to the internet server paths
by later setting up a picture storage mapping in the Storages menu. If the picture gallery only will be used from your local computer, make sure to
select the option "Pictures not available on internet" below.
<form name="editEntry" action="portal" method="POST">
    <input type="hidden" name="do" value="importgallery">
    <input type="hidden" name="gallery" value="<%=request.getParameter("gallery")%>">
    <table>
    <tr><td>File</td><td>
    <input type="text" name="file" size="80" value="http://erland.homeip.net/imatch.txt">
    </td></tr>
    <tr><td>Remove existing categories</td><td>
    <input type="checkbox" name="clearcategories" value="true">
    </td></tr>
    <tr><td>Remove existing pictures</td><td>
    <input type="checkbox" name="clearpictures" value="true" checked>
    </td></tr>
    <tr><td>Pictures not available on internet</td><td>
    <input type="checkbox" name="locallinks" value="true" checked>
    </td></tr>
    <tr><td>Use file names as picture title</td><td>
    <input type="checkbox" name="filenameaspicturetitle" value="true" checked>
    </td></tr>
    <tr><td>Use file names as picture description</td><td>
    <input type="checkbox" name="filenameaspicturedescription" value="true" checked>
    </td></tr>
    <tr><td>Cut long picture titles (more than 30 characters)</td><td>
    <input type="checkbox" name="cutlongpicturetitles" value="true" checked>
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="Save">
    <input type="button" value="Cancel" onClick="window.location='portal?do=searchgalleryentries<%=request.getParameter("gallery")!=null?"&gallery="+request.getParameter("gallery"):""%>&start=0&max=9'">
    </td></tr>
    <table>
    <p class="normal">Please note that importing 1000 pictures may take a number of minutes</p>
</form>
