<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_lezioni.Lezione, application_logic_layer.gestione_lezioni.Lezione, storage_layer.LezioneDao"%>


<%
	Utente account = (Utente) request.getSession().getAttribute("account");

	String id = request.getParameter("id_lezione");
	int idLezione = Integer.parseInt(id);

	Lezione lezione = LezioneDao.getLezioneById(idLezione);
%>

<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="../style.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UniQuestions</title>
<script>
	function validateForm() {

		var domanda = document.forms["InvioDomanda"]["domanda"].value;
		
		
		if (domanda == '') {
			alert("Il campo domanda non raggiunge la lunghezza minima");
			return false;
		}
		
		if(domanda.length > 6555){
			alert("Il campo domanda supera la lunghezza massima");
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
		</div>

		<div id="contenitore_ricerca" align="center">
			<form action="../RicercaAq" method="post">
				<h3>Ricerca AQ</h3>
				<input id="barra_ricerca" type="text" placeholder="Cerca"
					name="ricerca">
			</form>
		</div>


	</header>



	<%
		if (account.getTipo().equals("docente")) {
	%>
	<div id="contenitore_link" align="center">
		<p>
			<a href="../VisualizzaCorsi?action=<%="i_miei_corsi"%> class="active">I
				miei corsi</a> | <a href="VisualizzaDomande"> Domande</a>
		</p>
	</div>


	<%
		}
	%>

	<%
		if (account.getTipo().equals("studente")) {
	%>
	<div id="contenitore_link" align="center">
		<p>
			<a href="../VisualizzaCorsi?action=<%="i_miei_corsi"%>"
				class="active">I miei corsi</a> | <a
				href="../VisualizzaCorsi?action=<%="corsi_di_studio"%>">Corsi di
				studio</a> | <a href="VisualizzaRisposte">Risposte</a>
		</p>
	</div>
	<%
		}
	%>

	<div id="profilo_view" align="center">

		<div align="center">
			<h2><%=lezione.getNome()%></h2>

			<h5><%=lezione.getDescrizione()%></h5>

			<form name="InvioDomanda"
				action="../InvioDomanda?id_lezione=<%=idLezione%>&id_utente=<%=account.getId()%>"
				method="post"
				onsubmit="return validateForm()">
				<h5>Domanda</h5>
				<p>
					<textarea rows="7" cols="50" name="domanda"></textarea>
				</p>
					<button type="submit" class="tastoRispondi">Invio Domanda</button>
			</form>
		</div>

	</div>

	<div class="container2 signin2">
		&copy; 2019 UniQuestions Inc. All right reserved<br>
	</div>

	<div id="racchiudi_facebook_instagram1" align="right">
		<a href="https://www.facebook.com/" target="_blank"><img
			src="images/facebook.png" width="20"></a> <a
			href="https://www.instagram.com/" target="_blank"><img
			src="images/instagram.png" width="19"></a>
	</div>
</body>
</html>