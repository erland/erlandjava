<%@ page session="true" %>
<p class="bold">Registrering av ny anv�ndare</p>
<p class="normal">(Samtliga uppgifter m�ste fyllas i)</p>
<form name="editEntry" action="portal" method="POST">
    <input type="hidden" name="do" value="newuseraccount">
    <table>
    <input type="hidden" name="newuser" value="true">
    <tr><td>Anv�ndarnamn</td><td>
    <input type="text" name="username" value="">
    </td></tr>
    <tr><td>F�rnamn</td><td>
    <input type="text" name="firstname" value="">
    </td></tr>
    <tr><td>Efternamn</td><td>
    <input type="text" name="lastname" value="">
    </td></tr>
    <tr><td>L�senord</td><td>
    <input type="password" name="password1" value="">
    </td></tr>
    <tr><td>L�senord (igen)</td><td>
    <input type="password" name="password2" value="">
    </td></tr>
    <tr><td></td><td>
    <input type="hidden" name="welcometext" value="<%=
        "Du kan �ndra denna text och mycket annat "+
        "genom att g� in i Inst�llningar i menyn till "+
        "v�nster.\n" +
        "N�r du anser dig matat in alla n�dv�ndiga uppgifter "+
        "kan du g�ra din dagbok synlig p� f�rsta sidan genom "+
        "att markera den som officiell under inst�llningar"%>">
    <input type="submit" value="Registrera">
    <input type="button" value="Cancel" onClick="window.location='portal?do=default'">
    </td></tr>
    <table>
</form>
