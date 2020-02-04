<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,application_logic_layer.gestione_utente.Utente"%>


<%/*
 	Utente account = (Utente) request.getSession().getAttribute("account");
  	
	if(account!=null){
		response.sendRedirect("./gestione_corsi_insegnamento/VisualizzaCorsiView.jsp");
	} 	
 */%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<title>UniQuestions</title>
<link rel="stylesheet" href="style.css">
</head>

<body>

	<div class="banner">
		<div style="float: left" align="center">
			<div></div>
			<a href="https://www.facebook.com/dinfunisa/" target="_blank"><img
				src="images/logo_dinf.png" width="60"></a>
			<p>Dipartimento d'Informatica</p>
		</div>

		<div style="float: right" align="center">
			<a href="https://www.facebook.com/unisalerno" target="_blank"><img
				src="images/logo_unisa.png" width="60"></a>
			<p>Università degli Studi di Salerno</p>
		</div>
	</div>


	<div class="logo" align="center">

		<p>Benvenuti su UniQuestions, l'unica piattaforma che ascolta gli
			studenti</p>

	</div>



	<div class="container" align="center">


		<a href="gestione_utente/LoginView.html"><input
			class="registerbtn" type="submit" value="Login"></a> <br> <a
			href="gestione_utente/RegistrazioneView.html"><input
			class="registerbtn" type="submit" value="Registrati"></a>
	</div>

	<div id="racchiudi_facebook_instagram" align="center">
		<a href="https://www.facebook.com/unisalerno" target="_blank"><i
			class="fa fa-facebook" style="font-size: 20px; margin: 1%"></i></a> <a
			href="https://www.instagram.com/unisalerno" target="_blank"><i
			class="fa fa-instagram" style="font-size: 20px; margin: 1%"></i></a>
	</div>

	<div class="container1 signin1">
		&copy; 2019 UniQuestions Inc. All right reserved<br>
	</div>
</body>
</html>