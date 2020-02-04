<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, storage_layer.UtenteDao"%>
<head>

<%
	Utente account = (Utente) request.getSession().getAttribute("account");
	/*(Collection<Utente> docenti = (Collection<Utente>) request.getAttribute("docenti");*/
	Collection<Utente> docenti = UtenteDao.getAllDocenti();
	/*if (docenti == null) {
		//response.sendRedirect("../InserisciCorso");
		//Non ci sono docenti
		/*Collection<Utente> docenti2 = UtenteDao.getAllDocenti();
		request.removeAttribute("docenti");
        request.setAttribute("docenti2", docenti);
		System.out.println("Non ci sono docenti");*/
	//}
%>

<script>
	function validateForm() {

		var docenti = document.forms["AggiungiCorso"]["docenti"].value;
		var nome = document.forms["AggiungiCorso"]["nomeCorso"].value;
		
		if (nome == '') {
			alert("Il campo nome non raggiunge la lunghezza minima");
			return false;
		}
		
		if(nome.length > 50){
			alert("Il campo nome raggiunge la lunghezza massima");
			return false;
		}
		
		if (!docenti) {
			alert("Seleziona un docente");
			return false;
		}

	}
</script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>
	$(document)
			.ready(
					function() {
						$("#type")
								.change(
										function() {
											var val = $(this).val();
											if (val == "triennale") {
												$("#anno")
														.html(
																"<option value='test'>Primo Anno</option><option value='test2'>Secondo Anno</option><option value='test3'>Terzo Anno</option>");
											} else if (val == "magistrale") {
												$("#anno")
														.html(
																"<option value='test'>Primo Anno</option><option value='test2'>Secondo Anno</option>");
											}
										});
					});
</script>
<meta charset="UTF-8">
<title>UniQuestions</title>
<link rel="stylesheet" href="../style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



</head>
<body>

	<header>

		<div id="Benvenuto" align="center">
			<a href="../gestione_utente/VisualizzaProfiloView.jsp"
				style="text-decoration: none; color: black;"> <i
				class="fa fa-user" style="font-size: 35"></i>
				<p>
					Benvenuto
					<%=account.getNome()%></p>
				<form action="../Logout" method="get">
					<input class="tastologout" type="submit" value="Logout">
				</form>
			</a>
		</div>
	</header>


	<div id="inserisci_lezione" align="center">
		<h1>Aggiungi Corso</h1>
		<form name="AggiungiCorso" id="formCorsi" action="../InserisciCorso" method="post" onsubmit="return validateForm()">
			<label for="nomeCorso"><b>Nome Corso</b></label> <input type="text"
				name="nomeCorso"> 
			<label for="corsoDiLaurea"><b>Corso di laurea</b></label>
			<div class="custom-select">
				<select id="type" name="itemCorsoDiLaurea">
					<option value="triennale">Informatica - Triennale</option>
					<option value="magistrale">Informatica - Magistrale</option>
				</select>

			</div>


			<label for="docente"><b>Docente</b></label>
			<div class="custom-select">
				<select name="itemDocenti" id="docenti" size="3" multiple="multiple"
					tabindex="1">
					<%
						if (docenti.size() > 0) {
							Iterator<Utente> it = docenti.iterator();
							while (it.hasNext()) {
								Utente docente = (Utente) it.next();
					%>
					<option><%=docente.getNome()%>
						<%=docente.getCognome()%></option>
					<%
						}
						}
					%>
				</select>
			</div>

			<label for="annoAccademico"><b>Anno Accademico</b></label>
			<div class="custom-select">
				<select name="itemAnnoAccademico">
					<option>2019/2020</option>
					<option>2018/2019</option>
					<option>2017/2018</option>
					<option>2016/2017</option>
				</select>
			</div>

			<label for="annoDiStudio"><b>Anno di studio</b></label>
			<div class="custom-select">
				<select id="anno" name="itemAnnoDiStudi">
					<option>Primo anno</option>
					<option>Secondo anno</option>
					<option>Terzo anno</option>
				</select>
			</div>

			<label for="semestre"><b>Semestre</b></label>
			<div class="custom-select">
				<select name="itemSemestre">
					<option>Primo semestre</option>
					<option>Secondo semestre</option>
				</select>

			</div>

			<input class="registerbtn" type="submit" value="Aggiungi"
				tabindex="2">

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