<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_lezioni.Lezione, application_logic_layer.gestione_lezioni.Lezione, storage_layer.QuesitoDao"%>


<%
	Utente account = (Utente) request.getSession().getAttribute("account");

	String nome_corso = request.getParameter("nome_corso");
	String id_quesito = request.getParameter("id_quesito");
	int id = Integer.parseInt(id_quesito);
	Lezione lezione = null;
	String domanda = request.getParameter("domanda");
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

		var risposta = document.forms["InvioRisposta"]["risposta"].value;
		
		
		if (risposta == '') {
			alert("Il campo risposta non raggiunge la lunghezza minima");
			return false;
		}
		
		if(risposta.length > 6555){
			alert("Il campo risposta supera la lunghezza massima");
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
	</header>




	<div id="contenitore_link" align="center">
		<p>
			<a href="../VisualizzaCorsi?action=<%="i_miei_corsi"%> class="active">I
				miei corsi</a> | <a href="VisualizzaDomande"> Domande</a>
		</p>
	</div>


	<div align="center">
		<div id="profilo_view" align="center">

			<div align="center">
				<%
					lezione = QuesitoDao.getLezioneByIdQuesito(id);
				%>
				<h2><%=lezione.getNome()%></h2>
				<h4><%=lezione.getData()%></h4>
				<h5>Domanda</h5>
				<p><%=domanda%></p>

				<form name="InvioRisposta"
					action="../InvioRisposta?id_quesito=<%=id%>" method="post"
					onsubmit="return validateForm()">
					<h5>Risposta</h5>
					<p>
						<textarea rows="7" cols="50" name="risposta"></textarea>
					</p>
					<button type="submit" class="tastoRispondi">Invio Risposta</button>
				</form>
			</div>

		</div>
		<p>
			<a href="../VisualizzaDomande">Indietro</a>
		</p>
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