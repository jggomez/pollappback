package us.pollapp.inturik.util;

public class Constant {

	// Datos Twitter pollapp
	public static final String CONSUMER_KEY = "YjbFuhig1rLvE2ZnidvQ";
	public static final String CONSUMER_SECRET = "Hz3SiNE0IRbLfKMMZRJA60yUovvaQWrX3yiwg2pnz4";
	public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
	public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API
			+ "/oauth2/token";
	public static final String URL_SEARCH = URL_ROOT_TWITTER_API
			+ "/1.1/statuses/user_timeline.json?screen_name=pollapp1";

	// LLaves cache
	public static final String KEY_CACHE_MODELS = "KeyCacheModels";
	public static final String KEY_CACHE_TOKEN_TWEETS = "KeyCacheTokenTweets";
	public static final String KEY_CACHE_MATCH_ROUND1 = "KeyCacheMatchR1";
	public static final String KEY_CACHE_MATCH_ROUND2 = "KeyCacheMatchR2";
	public static final String KEY_CACHE_MATCH_ROUND3 = "KeyCacheMatchR3";

	public static final int SECONDS_EXPIRATION_TWEETS = 900; // 15 minutos
	public static final int SECONDS_EXPIRATION_MODELS = 86400; // 24 horas
	public static final int SECONDS_EXPIRATION_MATCH = 86400; // 24 horas

	// Envio de correo
	public static final String EMAIL_POLLAPP = "inturiksas@gmail.com";
	public static final String EMAIL_SUBJECT_GUEST = "%s te ha invitado a jugar en la polla de futbol %s !";
	public static final String EMAIL_BODY_GUEST = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> <html xmlns='http://www.w3.org/1999/xhtml'> <head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8' /> <title>Untitled Document</title> <style> .grande { font-family:Arial, Helvetica, sans-serif; color:#2D254C; font-size:30px; text-align:center;} .mediano { font-family:Arial, Helvetica, sans-serif; color:#2D254C; font-size:24px;} .peque { font-family:Arial, Helvetica, sans-serif; color:#2D254C; font-size:18px;} </style> </head> <body> <table width='800' align='center' cellpadding='0' cellspacing='0' style='border:1px solid #2D254C;' bgcolor='#FFFFFF'> <tr> <td><table width='800' border='0' cellspacing='0' cellpadding='0'> <tr> <td colspan='2'><img src='http://www.pollapp.us/img/plantillas/pollapp-logo.jpg' width='800' height='91' alt='pollapp' /></td> </tr> <tr> <td width='437' valign='top'><img src='http://www.pollapp.us/img/plantillas/modelo-pollapp2.jpg' width='437' height='509' /></td> <td width='363' valign='top'><table width='363' border='0' cellspacing='0' cellpadding='0'> <tr> <td height='413' colspan='2' valign='top' align='center'><span class='grande'><br /> �Saludos!</span><br /> <br /> <span class='peque'>Tienes una invitaci&oacute;n de %s para participar en la polla %s.</span><br /> <br /> <span class='mediano'>�Visita PollApp desde tu celular<br /> y empieza a disfrutar de tu pasi&oacute;n!</span><br /> <br /> <span class='mediano'>Si a&uacute;n no tienes PollApp <br /> en tu celular <br /></span> <span class='grande'>�desc&aacute;rgala ya!</span></td> </tr> <tr> <td width='162' valign='top'><a href='https://play.google.com/store/apps/details?id=us.pollapp.inturik'><img src='http://www.pollapp.us/img/plantillas/google-play.jpg' width='162' height='96' border='0' /></a></td> <td width='201' valign='top'><a href='https://itunes.apple.com/es/app/pollapp/id876949198?mt=8'><img src='http://www.pollapp.us/img/plantillas/app-store.jpg' width='201' height='96' border='0' /></a></td> </tr> </table></td> </tr> </table></td> </tr> </table> </body> </html>";
	public static final String EMAIL_SUBJECT_WELCOME = "Bienvenido a PollApp!";
	public static final String EMAIL_BODY_WELCOME = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> <html xmlns='http://www.w3.org/1999/xhtml'> <head> <meta http-equiv='Content-Type' content='text/html; charset=utf-8' /> <title>Untitled Document</title> </head> <body> <table width='800' align='center' cellpadding='0' cellspacing='0' style='border:1px solid #2D254C;' bgcolor='#FFFFFF'> <tr> <td><table width='800' border='0' cellspacing='0' cellpadding='0'> <tr> <td colspan='2'><img src='http://www.pollapp.us/img/plantillas/pollapp-logo.jpg' width='800' height='91' alt='pollapp' /></td> </tr> <tr> <td width='342' valign='top'><img src='http://www.pollapp.us/img/plantillas/modelo-pollapp.jpg' width='342' height='509' /></td> <td width='458' valign='top'><table width='458' border='0' cellspacing='0' cellpadding='0'> <tr> <td valign='top' height='381' align='center'> <span class='font-family:Arial, Helvetica, sans-serif; color:#2D254C; font-size:30px;'><br /> �Saludos %s !</span> <br /><br /> <span class='font-family:Arial, Helvetica, sans-serif; color:#2D254C; font-size:18px;'>Bienvenido a PollApp, la aplicaci&oacute;n donde podr&aacute;s divertirte prediciendo los marcadores con tus amigos mientras disfrutan de sus deportes favoritos.<br /> <br /> Tus datos registrados son:<br /> %s. <br /> E-mail: %s <br /> Contrase&ntilde;a: %s </span> <p><span class='font-family:Arial, Helvetica, sans-serif; color:#2D254C; font-size:24px;'>�Visita PollApp desde tu celular y<br /> empieza a disfrutar de tu pasi&oacute;n!</span></p> <p class='peque'>Si tienes preguntas, escr&iacute;benos a info@pollapp.us,<br /> con mucho gusto te atenderemos.<br /> </p></td> </tr> <tr> <td valign='top'><img src='http://www.pollapp.us/img/plantillas/cesped.jpg' width='458' height='128' /></td> </tr> </table></td> </tr> </table></td> </tr> </table> </body> </html>";

	// Patron para validar el password
	public static final String PATTERN_PASSWORD = "(?=^.{6,}$)((?=.*\\d)|(?=.*\\W+))(?![.\n])((?=.*[A-Z])|(?=.*[a-z])).*$";
	
	//Puntos por estraella para la votaci�n de las modelos
	public static final int POINTS_STAR = 5; 	
	
	//Correos para envio de errores
	public static final String EMAIL_SEND_ERROR = "juan.gomez01@gmail.com";

}
