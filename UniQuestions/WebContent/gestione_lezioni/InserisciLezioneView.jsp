<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_lezioni.Lezione"%>

<%
	Utente account = (Utente) request.getSession().getAttribute("account");

	int id_corso = Integer.valueOf(request.getParameter("id_corso"));
%>
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="../style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UniQuestions</title>

<script>
	function validateForm() {

		var nomeLezione = document.forms["AggiungiLezione"]["nomeLezione"].value;
		var dataLezione = document.forms["AggiungiLezione"]["dataLezione"].value;
		var argomentoLezione = document.forms["AggiungiLezione"]["argomentoLezione"].value;
		var reg = /(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d/;
		
		if (nomeLezione == '') {
			alert("Il campo nome non raggiunge la lunghezza minima");
			return false;
		}
		
		if(nomeLezione.length > 55){
			alert("Il campo nome supera la lunghezza massima");
			return false;
		}
		
		if(dataLezione == ''){
			alert("Il campo data non raggiunge la lunghezza minima");
			return false;
		}
		
		
		if(dataLezione.length > 10){
			alert("Il campo data supera la lunghezza massima");
			return false;
		}
		
		 if (!dataLezione.match(reg)) {
		 	alert("Il campo data non rispetta il formato");
		    return false;
		 }
		
		 if(argomentoLezione == ''){
				alert("Il campo argomento non raggiunge la lunghezza minima");
				return false;
		}
		 
		 if(argomentoLezione.length > 500){
				alert("Il campo argomento raggiunge la lunghezza massima");
				return false;
		}


	}
</script>


</head>
<body>

	<header>

		<div id="Benvenuto" align="center">
			<a href="../gestione_utente/VisualizzaProfiloView.jsp"
				style="text-decoration: none; color: black;"> <i
				class="fa fa-user" style="font-size: 35"></i>
				<p>
					Benvenuto
					<%=account.getUsername()%></p>
				<form action="../Logout" method="get">
					<input class="tastologout" type="submit" value="Logout">
				</form>
			</a>
	</header>


	<div id="inserisci_lezione" align="center">
		<h1>Aggiungi Lezione</h1>

		<form name="AggiungiLezione" id="formLezioneView"  onsubmit="return validateForm()" action="../AggiungiLezione?id_corso=<%=id_corso%>" method="post" >
				<label for="nomeLezione"><b>Nome Lezione</b></label> 
				<input id="nomelezione" type="text" name="nomeLezione" > 
				
				<label for="dataLezione"><b>Data Lezione</b></label> 
				<input id="data" type="text" name="dataLezione" >
				
				<label for="argomentoLezione"><b>Argomento Lezione</b></label>
				<textarea id="argomento" rows="3" cols="50" name="argomentoLezione"></textarea>

				<button type="submit" class="registerbtn">Aggiungi</button>
		</form>
		
		<p>
			<a href="../gestione_corsi_insegnamento/VisualizzaCorsiView.jsp">Indietro</a>
		</p>
	</div>

	<div class="container2 signin2">
		&copy; 2019 UniQuestions Inc. All right reserved<br>
	</div>

</body>
</html>