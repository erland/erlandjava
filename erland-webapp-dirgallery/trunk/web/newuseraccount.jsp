<%@ page session="true" %>
<p class="bold">Registrering av ny användare</p>
<p class="normal">(Samtliga uppgifter måste fyllas i)</p>
<form name="editEntry" action="portal" method="POST">
    <input type="hidden" name="do" value="newuseraccount">
    <table>
    <input type="hidden" name="newuser" value="true">
    <tr><td>Användarnamn</td><td>
    <input type="text" name="username" value="">
    </td></tr>
    <tr><td>Förnamn</td><td>
    <input type="text" name="firstname" value="">
    </td></tr>
    <tr><td>Efternamn</td><td>
    <input type="text" name="lastname" value="">
    </td></tr>
    <tr><td>Lösenord</td><td>
    <input type="password" name="password1" value="">
    </td></tr>
    <tr><td>Lösenord (igen)</td><td>
    <input type="password" name="password2" value="">
    </td></tr>
    <tr><td></td><td>
    <input type="hidden" name="welcometext" value="<%=
        "Du kan ändra denna text och mycket annat "+
        "genom att välja Inställningar i menyn "+
        "till vänster.\n" +
        "När du har matat in all information så kan "+
        "du göra ditt bildarkiv synligt på förstasidan genom att "+
        "markera det som officiellt i inställningar"%>">
    <input type="submit" value="Registrera">
    <input type="button" value="Avbryt" onClick="window.location='portal?do=default'">
    </td></tr>
    <table>
</form>
