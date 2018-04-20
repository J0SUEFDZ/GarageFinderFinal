package rodrigojosuetec.garage_finder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class AccessAPI {

    private final String url_base = "https://infinite-meadow-70795.herokuapp.com/";
    int estadoRequest = -1;
    private JSONObject jsonObjectResponse = new JSONObject();
    private JSONArray jsonArrayResponse = new JSONArray();

    private static final AccessAPI ourInstance = new AccessAPI();

    public static AccessAPI getInstance() {
        return ourInstance;
    }


    public boolean register(String username, String email, String password, String telefono){
        jsonObjectResponse = new JSONObject();
        HashMap<String, String> Parametros = new HashMap<String, String>();
        Parametros.put("name", username);
        Parametros.put("email", email);
        Parametros.put("password", password);
        Parametros.put("telefono", telefono);
        return makePOSTRequest("user/register", "POST", true, true, Parametros, HttpsURLConnection.HTTP_CREATED);

    }


    public boolean login(String email, String password){
        jsonObjectResponse = new JSONObject();
        HashMap<String, String> Parametros = new HashMap<String, String>();
        Parametros.put("email", email);
        Parametros.put("encrypted_password", password);
        return makePOSTRequest("user/login", "POST", true, true, Parametros, HttpsURLConnection.HTTP_OK);
    }

    public boolean login_token(String email, String auth_token){
        jsonObjectResponse = new JSONObject();
        HashMap<String, String> Parametros = new HashMap<String, String>();
        Parametros.put("email", email);
        Parametros.put("authentication_token", auth_token);
        return makePOSTRequest("user/login_token", "POST", true, true, Parametros, HttpsURLConnection.HTTP_OK);
    }

    public boolean login_facebook(String name, String email, String auth_token){
        jsonObjectResponse = new JSONObject();
        HashMap<String, String> Parametros = new HashMap<String, String>();
        Parametros.put("name", name);
        Parametros.put("email", email);
        Parametros.put("authentication_token", auth_token);
        return makePOSTRequest("user/login_facebook", "POST", true, true, Parametros, HttpsURLConnection.HTTP_OK);
    }




    //Para obtener respuesta del servidor
    public JSONObject getJsonObjectResponse(){
        Log.d("estado: ", ""+estadoRequest);
        return jsonObjectResponse;
    }




    //Hacer POST al server
    private boolean makePOSTRequest(String urlPOST, String metodo, boolean doInput, boolean doOutput, HashMap<String, String> Parametros, int responseCode){
        String result = "";
        URL url;
        HttpsURLConnection httpsURLConnection;

        try {
            url = new URL(url_base + urlPOST);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();

            //crea el objeto JSON para enviar los parámetros
            JSONObject parametros = new JSONObject();
            for(String s: Parametros.keySet()){
                parametros.put(s, Parametros.get(s));
            }

            //DEFINE PARAMETROS DE CONEXION
            httpsURLConnection.setReadTimeout(15000);
            httpsURLConnection.setConnectTimeout(15000);
            httpsURLConnection.setRequestMethod(metodo);
            httpsURLConnection.setDoInput(doInput);
            httpsURLConnection.setDoOutput(doOutput);

            //Obtiene el resultado de la solicitud
            OutputStream outputStream = httpsURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String para = jsonToString(parametros);
            bufferedWriter.write(para);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int rCode = httpsURLConnection.getResponseCode();
            if(responseCode == rCode){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer("");
                String linea = "";
                while((linea = bufferedReader.readLine()) != null){
                    stringBuffer.append(linea);
                    break;
                }
                bufferedReader.close();
                result = stringBuffer.toString();
            }else{
                result = "Error " + responseCode;
            }

            jsonObjectResponse = new JSONObject(result);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Pasar a tostring los archivos json
    private  String jsonToString(JSONObject params) throws JSONException, UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> iterator = params.keys();
        while ((iterator.hasNext())){
            String key = iterator.next();
            Object value = params.get(key);
            if(first){
                first = false;
            }else {
                result.append("&");
            }

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }


    // Hacer GETS al server
    private boolean makeGETRequest(String urlPOST, String metodo, int responseCode) {
        String result = "";
        URL url;
        HttpsURLConnection httpsURLConnection;

        try {
            url = new URL(url_base + urlPOST);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();

            //DEFINE PARAMETROS DE CONEXION
            httpsURLConnection.setReadTimeout(15000);
            httpsURLConnection.setConnectTimeout(15000);
            httpsURLConnection.setRequestMethod(metodo);

            //Connect to our url
            httpsURLConnection.connect();

            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(httpsURLConnection.getInputStream());

            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            //Check if the line we are reading is not null
            int rCode = httpsURLConnection.getResponseCode();
            if (responseCode == rCode) {
                String inputLine = "";
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                result = stringBuilder.toString();
            } else {
                result = "Error " + responseCode;
            }


            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();

            jsonObjectResponse = new JSONObject(result);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hacer DELETES al server
    private boolean makeDELETERequest(String urlPOST, String metodo, int responseCode){
        URL url;
        HttpsURLConnection httpsURLConnection;

        try {
            url = new URL(url_base + urlPOST);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("DELETE");
            httpsURLConnection.setDoOutput(false);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.connect();
            int r = httpsURLConnection.getResponseCode();
            String rC = Integer.toString(httpsURLConnection.getResponseCode());
            if(r==200){
                return true;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }










}
