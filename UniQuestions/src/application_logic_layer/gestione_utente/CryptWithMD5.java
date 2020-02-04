package application_logic_layer.gestione_utente;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe per la codifica delle password in MD5.
 *
 * @author UmbertoLibrera
 */
public class CryptWithMD5 {

  private static MessageDigest md;

  /**
   * Funzione che cripta la stringa passata.
   * @return
   */
  public static String cryptWithMD5(String pass) {
    try {
      md = MessageDigest.getInstance("MD5");
      byte[] passBytes = pass.getBytes();
      md.reset();
      byte[] digested = md.digest(passBytes);
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < digested.length; i++) {
        if ((0xff & digested[i]) < 0x10) {
          sb.append("0" + Integer.toHexString((0xFF & digested[i])));
        } else {
          sb.append(Integer.toHexString(0xFF & digested[i]));
        }
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
