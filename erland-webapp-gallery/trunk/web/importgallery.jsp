<%@ page session="true" import="erland.webapp.common.CommandInterface,
                                erland.webapp.gallery.entity.gallery.Gallery,
                                erland.webapp.gallery.act.gallery.ViewGalleryAction"%>
<p class="normal">Detta �r en funktion f�r att importera ditt bild galleri fr�n en text file som har
exporterats fr�n bildhanteringsprogrammet <a class="bold-link" href="http://www.photools.com/" target="_blank">IMatch</a>.
<p class="normal">Text filen skall skapas i IMatch enligt f�ljande:
<ol>
<li>V�lj den databas eller kategori du vill exportera
<li>V�lj meny: Database->Import and Export...
<li>V�lj export modul: Export to Text Format
<li>V�lj f�ljande bild attribut(image attributes)
<ul>
<li>Full File Name
<li>Last Modified
<li>Image Object Identifier (OID)
<li>Categories (fully qualified)
</ul>
</li>
<li>V�lj f�ljande bild egenskaper(image properties)
<ul>
<li>Title
<li>Description
</ul>
</li>
<li>Spara export filen p� en web server som �r �tkommlig fr�n internet. Ange hela s�kv�gen(webadressen) till filen nedan.
</ol>
<p class="normal">Om ditt bild galleri skall finnas tillg�ngligt f�r andra p� internet s� m�ste dina bilder ligga p� en web server n�gonstans.
Bild s�kv�garna som exporteras fr�n IMatch kan sedan mappas om till s�kv�garna till web servern
genom att senare s�tta upp en mappning i Lagringsplatser menyn till v�nster. Om bild galleriet endast skall anv�ndas fr�n din lokala dator s�
skall du markera alternativet "Bilder ej tillg�ngliga p� internet" nedan.
<form name="editEntry" action="portal" method="POST">
    <input type="hidden" name="do" value="importgallery">
    <input type="hidden" name="gallery" value="<%=request.getParameter("gallery")%>">
    <table>
    <tr><td>S�kv�g till importfilen</td><td>
    <input type="text" name="file" size="80" value="http://erland.homeip.net/imatch.txt">
    </td></tr>
    <tr><td>Ta bort existerande kategeorier</td><td>
    <input type="checkbox" name="clearcategories" value="true">
    </td></tr>
    <tr><td>Ta bort exsisterande bild l�nkar</td><td>
    <input type="checkbox" name="clearpictures" value="true" checked>
    </td></tr>
    <tr><td>Bilder ej tillg�ngliga p� internet</td><td>
    <input type="checkbox" name="locallinks" value="true">
    </td></tr>
    <tr><td>Anv�nd filnamn som bild titel om ingen riktig bild titel �r tillg�nglig</td><td>
    <input type="checkbox" name="filenameaspicturetitle" value="true" checked>
    </td></tr>
    <tr><td>Anv�nd filnamn som bild beskrivning om ingen riktig bild beskrivning �r tillg�nglig</td><td>
    <input type="checkbox" name="filenameaspicturedescription" value="true" checked>
    </td></tr>
    <tr><td>Kapa l�nga bild namn (mer �n 30 tecken)</td><td>
    <input type="checkbox" name="cutlongpicturetitles" value="true" checked>
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="Spara">
    <input type="button" value="Avbryt" onClick="window.location='portal?do=searchgalleryentries<%=request.getParameter("gallery")!=null?"&gallery="+request.getParameter("gallery"):""%>&start=0&max=9'">
    </td></tr>
    <table>
    <p class="normal">Please note that importing 1000 pictures may take a number of minutes</p>
</form>
