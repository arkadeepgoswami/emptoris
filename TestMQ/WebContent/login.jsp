<html lang="en">
<head>
<title>Login Form</title>
</head>
<body>
<h2>Hello, please log in:</h2>
<form method="post" action="j_security_check">
<table role="presentation">
<tr>
<td>Please type your user name: </td>
<td><input type="text" name="j_username"
size="20"/></td>
</tr>
<tr>
<td>Please type your password: </td>
<td><input type="password" name="j_password"
size="20"/></td>
</tr>
</table>
<p></p>
<input type="submit" value="Submit"/>
&nbsp;
<input type="reset" value="Reset"/>
</form>
</body>
</html>