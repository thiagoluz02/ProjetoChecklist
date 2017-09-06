package com.example.thiag.my_checklist.Configuraçãofaribase;



/**
 * Created by thiag on 13/08/2017.
 */

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFireBase {
    private static DatabaseReference referenceFireBase;
    private static FirebaseAuth autentication;

    public static DatabaseReference getFirebase() {
        if (referenceFireBase == null) {
            referenceFireBase = FirebaseDatabase.getInstance().getReference();
        }
        return referenceFireBase;
    }

    public static FirebaseAuth getFirebaseAutenticacao() {
        if (autentication == null) {
            autentication = FirebaseAuth.getInstance();
        }
        return  autentication;
    }

}
