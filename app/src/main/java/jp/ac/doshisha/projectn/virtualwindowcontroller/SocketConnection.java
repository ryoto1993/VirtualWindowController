package jp.ac.doshisha.projectn.virtualwindowcontroller;

import android.os.AsyncTask;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * UWP擬似窓システムとのソケット通信実装用クラス
 * @author Ryoto Tomioka
 * @version 1.0
 */
public class SocketConnection extends AsyncTask<String, Void, String>{

    static private String PORT;
    static private String IP_ADDRESS;

    private Socket socket;
    Handler handler = new Handler();

    static void setIpAddress(String ip) {
        IP_ADDRESS = ip;
    }

    static void setPORT(String PORT) {
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
            System.out.println("IP:" + IP_ADDRESS + " PORT:" + PORT);
            socket = new Socket();
            socket.connect(endpoint, 1000);
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(() -> Toast.makeText(StartActivity.getAppContext(), R.string.toast_connection_error, Toast.LENGTH_LONG).show());
            return null;
        }

        if (str.length == 1) {
            return sendCommand(str[0]);
        }
        else if(str.length == 2) {
            return sendCommand(str[0] + "\r\n" + str[1]);
        }
        else {
            return null;
        }

    }

    /**
     * コマンド送出とチェック<br>
     * @param command 送出コマンド
     */
    private String sendCommand(String command) {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            bw.write(command + "\n");
            bw.flush();

            // サーバからのOKを待機
            String resData = br.readLine();

            in.close();
            out.close();
            socket.close();

            return resData;
        }
        catch (Exception e) {
            System.out.println("Exception: " + e);
            return "FAILED TO CONNECT.";
        }

    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println(s);
    }
}


