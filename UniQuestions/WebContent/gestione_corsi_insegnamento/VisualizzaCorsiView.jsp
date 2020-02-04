<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_corsi_insegnamento.CorsoInsegnamento,  application_logic_layer.gestione_utente.Utente,  storage_layer.QuesitoDao, storage_layer.CorsoInsegnamentoDao"%>



<%
	Utente account = (Utente) request.getSession().getAttribute("account");
	Collection<CorsoInsegnamento> corsi_iscritti = (Collection<CorsoInsegnamento>) request.getAttribute("corsi_iscritti");
	Collection<CorsoInsegnamento> corsi = (Collection<CorsoInsegnamento>) request.getAttribute("corsi");
	Collection<CorsoInsegnamento> corsi_insegnati = (Collection<CorsoInsegnamento>) request.getAttribute("corsi_insegnati");
	if (corsi == null && corsi_insegnati == null && corsi_iscritti == null) {
		response.sendRedirect("../VisualizzaCorsi?action=i_miei_corsi");
		return;
	}
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

		<div id="Benvenuto" align="center">
			<%
				if (!account.getTipo().equals("admin")) {
			%>
			<a href="./gestione_utente/VisualizzaProfiloView.jsp"
				style="text-decoration: none; color: black;"> <%
 	}
 %> <i class="fa fa-user" style="font-size: 35"></i>
				<p>
					Benvenuto
					<%=account.getUsername()%></p>
				<form action="./Logout" method="get">
					<input class="tastologout" type="submit" value="Logout">
				</form>
			</a>
		</div>





		<%
			if (!account.getUsername().equals("admin") && (!account.getTipo().equals("docente"))) {
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
		if (account.getTipo().equals("admin")) {
	%>
	<div id="contenitore_button" align="center">
		<a href="gestione_corsi_insegnamento/InserisciCorsiView.jsp"><input
			class=tastoInserisciCorso type="submit" value="Aggiungi Corso"></a>
	</div>
	<br>
	<%
		}
	%>

	<%
		if (account.getTipo().equals("docente")) {
	%>
	<div id="contenitore_link" align="center">
		<p>
			<a href="" class="active">I miei corsi</a> | <a
				href="VisualizzaDomande"> Domande</a>
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
					if (corsi_insegnati.size() > 0) {
						Iterator<CorsoInsegnamento> it = corsi_insegnati.iterator();
						while (it.hasNext()) {
							CorsoInsegnamento corso = (CorsoInsegnamento) it.next();
			%>
			<li><a
				href="VisualizzaLezioni?id_corso=<%=corso.getId()%>&nome_corso=<%=corso.getNome()%>">
					<div class="row">
						<div id="first_box_element" align="left">
							<i class="fa fa-angle-right"></i>
							<%=corso.getNome()%>
						</div>

						<div id="second_box_element" align="center"></div>

						<div id="third_box_element" align="right">
							<%=QuesitoDao.getCountDomandeByIdCorso(corso.getId())%>
							<i class="fa fa-question-circle" style="font-size: 20px"></i>
						</div>
					</div>
			</a></li>
			<%
				}
					}
				}
			%>
			<%
				if (account.getTipo().equals("admin")) {
					if (corsi.size() > 0) {
						Iterator<CorsoInsegnamento> it = corsi.iterator();
						while (it.hasNext()) {
							CorsoInsegnamento corso = (CorsoInsegnamento) it.next();
			%>
			<li>
				<div>

					<%=corso.getNome()%>
					<div style="float: right; font-family: futura">
						<a href="EliminaCorso?id_corso=<%=corso.getId()%>"><input
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
					if (corsi_iscritti.size() > 0) {
						Iterator<CorsoInsegnamento> it = corsi_iscritti.iterator();
						while (it.hasNext()) {
							CorsoInsegnamento corso = (CorsoInsegnamento) it.next();
			%>
			<li><a href="VisualizzaLezioni?id_corso=<%=corso.getId()%>">
					<div class="row">
						<div id="first_box_element" align="left">
							<i class="fas fa-angle-right"></i>
							<%=corso.getNome()%>

						</div>
						Prof:
						<%
							ArrayList<Utente> docenti = CorsoInsegnamentoDao.getListaDocentiByIdCorso(corso.getId());
										Iterator<Utente> iterator = docenti.iterator();
										while (iterator.hasNext()) {
											Utente docente = iterator.next();
						%>
						<%=docente.getNome()%><%=docente.getCognome()%>
						<%
							}
						%>
					</div>
			</a></li>
			<%
				}
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