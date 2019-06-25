import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.regex.*;

public class main {

    public static String HMAC_HASH(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    public static byte[] hex2bin(String hex) throws NumberFormatException {
        if (hex.length() % 2 > 0) {
            throw new NumberFormatException("Hexadecimal input string must have an even length.");
        }
        byte[] r = new byte[hex.length() / 2];
        for (int i = hex.length(); i > 0;) {
            r[i / 2 - 1] = (byte) (digit(hex.charAt(--i)) | (digit(hex.charAt(--i)) << 4));
        }
        return r;
    }

    private static int digit(char ch) {
        int r = Character.digit(ch, 16);
        if (r < 0) {
            throw new NumberFormatException("Invalid hexadecimal string: " + ch);
        }
        return r;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        if (args.length == 1) {
            String mac = args[0].replaceAll("\\:", "");
            if (!Pattern.matches("^(\\w+){12}$", mac)) {
                System.out.println("Invalid Mac Address!");
                System.exit(1);
            }
            try {
                String key = HMAC_HASH(hex2bin(mac), hex2bin("8544E3B47ECA58F9583043F8")).substring(0, 24);
                String result = "";
                int s = 0;
                for (int i = 0; i < key.length(); i++) {
                    s++;
                    result += key.substring(i, i + 1).toLowerCase();
                    if (s == 4 && i != key.length() - 1) {
                        result += "-";
                        s = 0;
                    }
                }
                System.out.println(result);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid Mac Address!");
                System.exit(1);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                System.exit(1);
            }
        } else {
            System.out.println("Supermicro IPMI License Generator");
            System.out.println("https://github.com/kasuganosoras/SuperMicro-IPMI-LicenseGenerator");
            System.out.println("Argument is missing, please append your BMC-MAC after this command.");
        }
    }
}
