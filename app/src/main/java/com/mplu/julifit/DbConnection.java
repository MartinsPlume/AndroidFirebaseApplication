package com.mplu.julifit;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DbConnection {

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public DocumentReference docRef;
    public CollectionReference colRef;

    public DbConnection(String collection, String document){
        docRef = db.collection(collection).document(document);
    }

    public DbConnection(String collection){
        colRef = db.collection(collection);
    }

    public void setDocument(User user){
        docRef.set(user);
    }

    public DocumentReference getDocRef(){
        return docRef;
    }

    public CollectionReference getColRef() {
        return colRef;
    }

}
