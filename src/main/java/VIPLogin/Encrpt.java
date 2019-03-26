package VIPLogin;
//XOR encryption to so no password is stored passed through sockets
public class Encrpt {
    private char[] key = "a_5$]-xU44qmLut;".toCharArray();
    public Encrpt(){}
    public String encrypt(byte[] bufferIn, int total) {
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < total; i++) {
            cipher.append((char) (bufferIn[i] ^ key[i % key.length]));
        }
        return cipher.toString();
    }
}
