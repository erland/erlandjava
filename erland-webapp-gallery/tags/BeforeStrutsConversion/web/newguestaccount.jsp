<%@ page session="true" %>
<p class="normal">Register a new guest user for your galleries, this user will have access to all of your private pictures and galleries</p>
<form name="editEntry" action="portal" method="POST">
    <input type="hidden" name="do" value="editguestaccount">
    <table>
    <tr><td>Användarnamn</td><td>
    <input type="text" name="guestuser" value="">
    </td></tr>
    <tr><td></td><td>
    <input type="submit" value="Spara">
    <input type="button" value="Avbryt" onClick="window.location='portal?do=default'">
    </td></tr>
    <table>
</form>
