<%@ page session="true" %>
<p class="normal">Logga in f�r att titta p� dina aktiediagram eller f�r att uppdatera dina aktieinnehav<br>
Om du vill ha tillg�ng till aktiediagramsidan kan du h�ra av dig till <a class="bold-link" href="mailto:erland.i@telia.com">mig</a><br><br>

<form name="loginForm" action="portal" method="POST">
<input type="hidden" name="do" value="login">
<input type="hidden" name="application" value="stock">
<table>
<tr><td>Anv�ndarnamn</td><td>
<input type="text" name="name" value="">
</td></tr>
<tr><td>L�senord</td><td>
<input type="password" name="password" value="">
</td></tr>
<tr><td></td><td>
<input type="submit" value="Logga in">
</td></tr>
</table>
</form>
