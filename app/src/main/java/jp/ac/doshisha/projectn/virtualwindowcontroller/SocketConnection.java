package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.os.AsyncTask;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * UWP擬似窓システムとのソケット通信実装用クラス
 * @author Ryoto Tomioka
 * @version 1.0
 */
public class SocketConnection extends AsyncTask<String, Integer, String>{

    static private String PORT;
    static private String IP_ADDRESS;

    private Socket socket;

    public static void setIpAddress(String ip) {
        IP_ADDRESS = ip;
    }

    public static String getIpAddress() {
        return IP_ADDRESS;
    }

    public static String getPORT() {
        return PORT;
    }

    public static void setPORT(String PORT) {
        SocketConnection.PORT = PORT;
    }

    /**
     * ソケット通信の実行<br>
     * modeコマンドとdataコマンドを指定してSocketConnection.execute(mode, data)を実行することにより擬似窓の遠隔制御が実現できます。<br>
     * modeコマンドとdataコマンドの書式についてはUWP擬似窓システムの仕様書を参照してください。
     * @param str コマンドリスト：1つめのstrはmodeコマンドである'LIVE', 'IMAGE, 'VIDEO', 'BLANK'を指定します。2めのstrにはdataコマンドを指定します。
     */
    @Override
    protected String doInBackground(String... str) {
        InetSocketAddress endpoint = new InetSocketAddress(IP_ADDRESS, Integer.parseInt(PORT));
        try {
            socket = new Socket();
            socket.connect(endpoint);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(str[0].equals("LIVE")) {
            sendCommand("LIVE");
        }
        return null;
    }

    /**
     * コマンド送出とチェック<br>
     * @param command 送出コマンド
     */
    private void sendCommand(String command) {
        try {
            // サーバーに数値を送信
            OutputStreamWriter ow = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(ow);
            ow.write(command);
            ow.flush();

            // close
            bw.close();
            ow.close();

            // サーバーからの"OK"を待機
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
