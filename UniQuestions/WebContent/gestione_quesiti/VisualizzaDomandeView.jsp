<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente, application_logic_layer.gestione_lezioni.Lezione, storage_layer.QuesitoDao, application_logic_layer.gestione_quesiti.Quesito"%>

<%
	Utente account = (Utente) request.getSession().getAttribute("account");
	Collection<Quesito> quesiti = (Collection<Quesito>) request.getAttribute("quesiti");
	String nome_corso = "";
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
			<a href="./gestione_utente/VisualizzaProfiloView.jsp"
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
	</header>



	<%
		if (account.getTipo().equals("docente")) {
	%>
	<div id="contenitore_link" align="center">
		<p>
			<a href="VisualizzaCorsi?action=<%="i_miei_corsi"%>">I miei corsi</a>
			| <a class="active" href="VisualizzaDomande"> Domande </a>
		</p>
	</div>


	<%
		}
	%>





	<div id="corsi_window">
		<ul>
			<%
				if (account.getTipo().equals("docente")) {

					if (quesiti.size() > 0) {

						Iterator<Quesito> it = quesiti.iterator();

						while (it.hasNext()) {
							Quesito quesito = (Quesito) it.next();
			%>
			<li>
				<div class="row">
					<div id="first_box_element" align="left">
					
						<%
							String domanda = quesito.getDomanda();
							if(domanda.length()>24)
							{
								domanda  = domanda.substring(0,24)+" . . . .";
							}
						%>
						<i class="fas fa-angle-right"></i> <a href="#"><%=domanda%></a>
					</div>

					<div id="second_box_element" align="center">
						<%
							nome_corso = QuesitoDao.getCorsoByIdQuesito(quesito.getId()).getNome();
						%>
						<%=nome_corso%>
					</div>


					<div style="float: right; font-family: futura">
						<a
							href="./gestione_quesiti/InvioRispostaView.jsp?nome_corso=<%=nome_corso%>&domanda=<%=quesito.getDomanda()%>&id_quesito=<%=quesito.getId()%>"><input
							class="tastorispondi" type="submit" value="Rispondi"></a>
					</div>
				</div>
			</li>
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