<%@ page session="true" %>
<p class="bold">Registrering av ny användare</p>
<p class="normal">(Samtliga uppgifter måste fyllas i)</p>
<form name="editEntry" action="portal" method="POST">
    <input type="hidden" name="do" value="newuseraccount">
    <table>
    <input type="hidden" name="newuser" value="true">
    <tr><td>Username</td><td>
    <input type="text" name="username" value="">
    </td></tr>
    <tr><td>First name</td><td>
    <input type="text" name="firstname" value="">
    </td></tr>
    <tr><td>Last name</td><td>
    <input type="text" name="lastname" value="">
    </td></tr>
    <tr><td>Password</td><td>
    <input type="password" name="password1" value="">
    </td></tr>
    <tr><td>Password (again)</td><td>
    <input type="password" name="password2" value="">
    </td></tr>
    <tr><td></td><td>
    <input type="hidden" name="welcometext" value="<%=
        "You can change this text and a lot more "+
        "by selecting Preferences in the menu "+
        "to the left.\n" +
        "When you feel that you have entered all neccesary information "+
        "you can make your gallery visible on the front page by "+
        "marking it as official in the preferences"%>">
    <input type="submit" value="Register">
    <input type="button" value="Cancel" onClick="window.location='portal?do=default'">
    </td></tr>
    <table>
</form>
