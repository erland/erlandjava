<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.entity.gallery.Gallery,
                                erland.webapp.gallery.act.gallery.ViewGalleryAction"%>
<p class="normal">Detta är en funktion för att importera ditt bild galleri från en text file som har
exporterats från bildhanteringsprogrammet <a class="bold-link" href="http://www.photools.com/" target="_blank">IMatch</a>.
<p class="normal">Text filen skall skapas i IMatch enligt följande:
<ol>
<li>Välj den databas eller kategori du vill exportera
<li>Välj meny: Database->Import and Export...
<li>Välj export modul: Export to Text Format
<li>Välj följande bild attribut(image attributes)
<ul>
<li>Full File Name
<li>Last Modified
<li>Image Object Identifier (OID)
<li>Categories (fully qualified)
</ul>
</li>
<li>Välj följande bild egenskaper(image properties)
<ul>
<li>Title
<li>Description
</ul>
</li>
<li>Spara export filen på en web server som är åtkommlig från internet. Ange hela sökvägen(webadressen) till filen nedan.
</ol>
<p class="normal">Om ditt bild galleri skall finnas tillgängligt för andra på internet så måste dina bilder ligga på en web server någonstans.
Bild sökvägarna som exporteras från IMatch kan sedan mappas om till sökvägarna till web servern
genom att senare sätta upp en mappning i Lagringsplatser menyn till vänster. Om bild galleriet endast skall användas från din lokala dator så
skall du markera alternativet "Bilder ej tillgängliga på internet" nedan.
<form name="editEntry" action="portal" method="POST">
    <input type="hidden" name="do" value="importgallery">
    <input type="hidden" name="gallery" value="<%=request.getParameter("gallery")%>">
    <table>
    <tr><td>Sökväg till importfilen</td><td>
    <input type="text" name="file" size="80" value="http://erland.homeip.net/imatch.txt">
    </td></tr>
    <tr><td>Ta bort existerande kategeorier</td><td>
    <input type="checkbox" name="clearcategories" value="true">
    </td></tr>
    <tr><td>Ta bort exsisterande bild länkar</td><td>
    <input type="checkbox" name="clearpictures" value="true" checked>
    </td></tr>
    <tr><td>Bilder ej tillgängliga på internet</td><td>
    <input type="checkbox" name="locallinks" value="true">
    </td></tr>
    <tr><td>Använd filnamn som bild titel om ingen riktig bild titel är tillgänglig</td><td>
    <input type="checkbox" name="filenameaspicturetitle" value="true" checked>
    </td></tr>
    <tr><td>Använd filnamn som bild beskrivning om ingen riktig bild beskrivning är tillgänglig</td><td>
    <input type="checkbox" name="filenameaspicturedescription" value="true" checked>
    </td></tr>
    <tr><td>Kapa långa bild namn (mer än 30 tecken)</td><td>
    <input type="checkbox" name="cutlongpicturetitles" value="true" checked>
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="Spara">
    <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=request.getParameter("gallery")!=null?"&gallery="+request.getParameter("gallery"):""%>&start=0&max=9'">
    </td></tr>
    <table>
    <p class="normal">Please note that importing 1000 pictures may take a number of minutes</p>
</form>
