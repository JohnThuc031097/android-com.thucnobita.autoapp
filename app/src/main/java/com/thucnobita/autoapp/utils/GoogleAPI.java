//package com.thucnobita.autoapp.utils;
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//
//import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//
//public class GoogleAPI {
//    private static final String TAG_NAME = "GOOGLE-API";
//    /** Application name. */
//    private static final String APPLICATION_NAME = "Google API for AutoApp";
//    /** Global instance of the JSON factory. */
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    /** Directory to store authorization tokens for this application. */
//    private static final String TOKENS_DIRECTORY_PATH = String.format("%s/%s/%s",
//            Constants.FOLDER_ROOT,
//            Constants.FOLDER_NAME_APP,
//            Constants.FOLDER_NAME_CREDENTIAL);
//    private static final String CREDENTIALS_FILE_PATH = String.format("%s/%s/%s/%s",
//            Constants.FOLDER_ROOT,
//            Constants.FOLDER_NAME_APP,
//            Constants.FOLDER_NAME_CREDENTIAL,
////            "client_secret.json");
//            "service_account.p12");
//
//    /**
//     * Global instance of the scopes required by this quickstart.
//     * If modifying these scopes, delete your previously saved tokens/ folder.
//     */
//    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
////    private static final Set<String> SCOPES = DriveScopes.all();
//
//    private Drive _service;
//
//    public GoogleAPI() {
//
//    }
//
//    public boolean build(Context context){
//        try{
//            // Build a new authorized API client service.
//            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//            this._service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(context, HTTP_TRANSPORT))
//                    .setApplicationName(APPLICATION_NAME)
//                    .build();
//            return true;
//        }catch (GeneralSecurityException | IOException e){
//            Log.e(TAG_NAME, e.getMessage());
//        }
//        return false;
//    }
//
//    public Drive getService(){
//        return _service;
//    }
//
//    /**
//     * Creates an authorized Credential object.
//     * @param HTTP_TRANSPORT The network HTTP Transport.
//     * @return An authorized Credential object.
//     * @throws IOException If the credentials.json file cannot be found.
//     */
//    private static Credential getCredentials(Context context, final NetHttpTransport HTTP_TRANSPORT) throws IOException, GeneralSecurityException {
//        // Load client secrets.
////        InputStream in = GoogleAPI.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        InputStream in = new FileInputStream(CREDENTIALS_FILE_PATH);
////        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//
//        // Build flow and trigger user authorization request.
////        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
////                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
////                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
////                .setAccessType("offline")
////                .setApprovalPrompt("force")
////                .build();
////        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
////        AuthorizationCodeInstalledApp credential = new AuthorizationCodeInstalledApp(flow, receiver);
////        {
////            @Override
////            protected void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) {
////                String url = (authorizationUrl.build());
////                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                context.startActivity(browserIntent);
////            }
////        };
//        //returns an authorized Credential object.
////        return credential.authorize("thucnobita");
//        return new GoogleCredential.Builder()
//                .setTransport(HTTP_TRANSPORT)
//                .setJsonFactory(JSON_FACTORY)
//                .setServiceAccountId("autoapp-android@skilful-alpha-324009.iam.gserviceaccount.com")
//                .setServiceAccountScopes(SCOPES)
//                .setServiceAccountPrivateKeyFromP12File(in)
//                .build();
//    }
//}
