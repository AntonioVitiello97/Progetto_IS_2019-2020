<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_lezioni.Lezione, application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento, storage_layer.QuesitoDao"%>


<%
	Utente account = (Utente) request.getSession().getAttribute("account");
	Collection<Lezione> lezioni = (Collection<Lezione>) request.getAttribute("lezioni");
	String nome_corso = (String) request.getAttribute("nome_corso");
	String id_corso = (String) request.getAttribute("id_corso");
	int id = Integer.parseInt(id_corso);
%>
<!DOCTYPE>
<html>
<head>
<link rel="stylesheet" href="./style.css">

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UniQuestions</title>
</head>
<body>

	<header>

		<a href="./gestione_utente/VisualizzaProfiloView.jsp"
			style="text-decoration: none; color: black;">
			<div id="Benvenuto" align="center">
				<i class="fa fa-user" style="font-size: 35"></i>
				<p>
					Benvenuto
					<%=account.getUsername()%></p>
				<form action="./Logout" method="get">
					<input class="tastologout" type="submit" value="Logout">
				</form>
		</a>
		</div>

		<%
			if (!account.getTipo().equals("docente")) {
		%>
		<div id="contenitore_ricerca" align="center">
			<form action="RicercaAq" method="post">
				<h3>Ricerca AQ</h3>
				<input id="barra_ricerca" type="text" placeholder="Cerca"
					name="ricerca">
			</form>
		</div>
		<%
			}
		%>
	</header>



	<%
		if (account.getTipo().equals("docente")) {
	%>
	<div id="contenitore_link" align="center">
		<p>
			<a href="VisualizzaCorsi?action=<%="i_miei_corsi"%> class="active">I
				miei corsi</a> | <a href="VisualizzaDomande"> Domande</a>
		</p>
	</div>

	<div id="racchiudiText" align="center">
		<h4><%=nome_corso%></h4>
		<p>
			<a
				href="./gestione_lezioni/InserisciLezioneView.jsp?id_corso=<%=id%>">Aggiungi
				Lezione</a>
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
			<a href="VisualizzaCorsi?action=<%="i_miei_corsi"%>" class="active">I
				miei corsi</a> | <a href="VisualizzaCorsi?action=<%="corsi_di_studio"%>">Corsi
				di studio</a> | <a href="VisualizzaRisposte">Risposte</a>
		</p>
	</div>
	<%
		}
	%>



	<div id="corsi_window">
		<ul>
			<%
				if (account.getTipo().equals("docente")) {

					if (lezioni.size() > 0) {

						Iterator<Lezione> it = lezioni.iterator();

						while (it.hasNext()) {

							Lezione lezione = (Lezione) it.next();
			%>
			<li>
				<div class="row">
					<div id="first_box_element" align="left">
						<i class="fas fa-angle-right"></i>
						<%=lezione.getNome()%>
					</div>

					<div id="second_box_element" align="center">
						<%
							double media = Double.parseDouble(lezione.getValutazione());
										int val = (int) media;
										for (int i = 0; i < val; i++) {
						%>
						<i class="fas fa-star"></i>
						<%
							}
										int no_val = 5 - val;
										for (int j = 0; j < no_val; j++) {
						%>
						<i class="far fa-star"></i>
						<%
							}
						%>
					</div>
					<div id="third_box_element" align="right">

						<%
							int c = QuesitoDao.getCountDomandeByIdLezione(lezione.getId());
						%>
						<%=c%>
						<i class="fa fa-question-circle" style="font-size: 20px"></i>
					</div>

					<div style="float: right; font-family: futura">
						<a
							href="EliminaLezione?id_lezione=<%=lezione.getId()%>&id_corso=<%=id_corso%>&nome_corso=<%=nome_corso%>"><input
							class="tastorispondi" type="submit" value="Elimina"></a>
					</div>
				</div>
			</li>
			<%
				}
					}
				}
			%>

			<%
				if (account.getTipo().equals("studente")) {
					if (lezioni.size() > 0) {
						Iterator<Lezione> it = lezioni.iterator();
						while (it.hasNext()) {
							Lezione lezione = (Lezione) it.next();
			%>
			<li>
				<div class="row">
					<div id="first_box_element">
						<i class="fas fa-angle-right"></i> <a href="#"><%=lezione.getNome()%></a>
					</div>

					<div id="second_box_element">
						<i class="far fa-question-circle"></i><a
							href="./gestione_quesiti/InvioDomandaView.jsp?id_lezione=<%=lezione.getId()%>">
							Domanda</a>
					</div>

					<div id="third_box_element">
						<i class="far fa-star"></i><a
							href="./gestione_lezioni/InserisciValutazioneView.jsp?id_lezione=<%=lezione.getId()%>">
							Valuta</a>
					</div>
				</div>
			</li>
			<%
				}
					} else {
			%>
			<div align="center">
				<p>Non ci sono lezioni disponibili</p>
			</div>
			<%
				}
				}
			%>

		</ul>
	</div>

	<div class="container2 signin2">
		&copy; 2019 UniQuestions Inc. All right reserved<br>
	</div>


</body>
</html>