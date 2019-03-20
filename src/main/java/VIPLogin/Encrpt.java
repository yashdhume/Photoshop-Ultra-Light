package VIPLogin;

public class Encrpt {
    char[] key = "a_5$]-xU44qmLut;".toCharArray();
    Encrpt(){}
    public String encrypt(byte[] bufferIn, int total) {
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < total; i++) {
            cipher.append((char) (bufferIn[i] ^ key[i % key.length]));
        }
        return cipher.toString();
    }
}
