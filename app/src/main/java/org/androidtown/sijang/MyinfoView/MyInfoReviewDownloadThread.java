package org.androidtown.sijang.MyinfoView;

import android.os.Handler;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by CYSN on 2017-10-14.
 */

public class MyInfoReviewDownloadThread extends Thread {
    private Handler handler;

    public MyInfoReviewDownloadThread(Handler handler) {
        this.handler = handler;
    }

    public void run() {
        while (true) {
            try {
                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                // Create a reference with an initial file path and name
                StorageReference pathReference = storageRef.child("images/list.txt");
                File localFile = File.createTempFile("list", "txt");

                pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Local temp file has been created
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        e.getStackTrace();
                        // Handle any errors
                    }
                });

                // Create a reference to a file from a Google Cloud Storage URI
                StorageReference gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg");

                // Create a reference from an HTTPS URL
                // Note that in the URL, characters are URL escaped!
                StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg");
            }
            catch (Exception e){
                e.getStackTrace();
            }
        }
    }
}
