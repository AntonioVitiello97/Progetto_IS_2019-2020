<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente"%>


<%
	Utente account = (Utente) request.getSession().getAttribute("account");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>UniQuestions</title>
<link rel="stylesheet" href="../style.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">

<script>
	function validateForm() {
		var vecchia_password = document.forms["ModificaPassword"]["vecchia_password"].value;
		var nuova_password = document.forms["ModificaPassword"]["nuova_password"].value;

		
		if (vecchia_password == '') {
			alert("Il campo vecchia password non raggiunge la lunghezza minima");
			return false;
		}
		
		if (nuova_password == '') {
			alert("Il campo nuova password non raggiunge la lunghezza minima");
			return false;
		}

		if (nuova_password.length < 6) {
			alert("Inserire una password di almeno 6 caratteri");
			return false;
		}

	}
</script>

</head>
<body>

	<header>
		<div id="Benvenuto" align="center">
			<a href="VisualizzaProfiloView.jsp"
				style="text-decoration: none; color: black;"> <i
				class="fa fa-user" style="font-size: 35"></i>
				<p>
					Benvenuto
					<%=account.getUsername()%></p>
				<form action="./Logout" method="get">
					<input class="tastologout" type="submit" value="Logout">
				</form>
			</a>
		</div>

		<br> <br> <br>

	</header>
	<form name="ModificaPassword" action="../ModificaPassword?username=<%=account.getUsername()%>" method="post" onsubmit="return validateForm()">


		<div class="container" align="center">

			<h1>Modifica password</h1>
			<hr>

			<label for="password"><b>Vecchia password</b></label> 
			<input type="password" placeholder="Password" name="vecchia_password"> 
				
			<label for="password"><b>Nuova password</b></label>
			<input type="password" placeholder="Password" name="nuova_password" >

			<button type="submit" class="registerbtn">Modifica</button>
			
			<p>
				<a href="VisualizzaProfiloView.jsp">Indietro</a>
			</p>
		</div>

		<div class="container signin">
			&copy; 2019 UniQuestions Inc. All right reserved<br>
		</div>
	</form>

</body>
</html>