package com.example.seefood.profileSetup;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.seefood.MainActivity;
import com.example.seefood.R;
import com.example.seefood.models.CustomerModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class createCustomerProfileFragment extends Fragment {
    public createCustomerProfileFragment() {
        // Required empty public constructor
    }

    private String photoURL;

    private ImageView imageView;
    private Uri selectedImage;
    private Bitmap cameraImage;
    private EditText editText;
    private static final int LOCATION_REQUEST = 222;
    private static final int GALLERY_REQUEST_CODE = 123;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CustomerModel cm = new CustomerModel();
    //final String uid = firebaseAuth.getUid();
    String uid;

    Boolean galleryImage = false;
    Boolean cameraPhoto = false;

    String pathURL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_customer_profile, container, false);
        Button photoButton = view.findViewById(R.id.photoButton);
        Button submitButton = view.findViewById(R.id.submitProfileButton);
        imageView = view.findViewById(R.id.my_avatar);
        editText = view.findViewById(R.id.editText);
        mStorageRef = FirebaseStorage.getInstance().getReference("profilePhotos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Customer");
        uid = firebaseAuth.getUid();

        Toast.makeText(getContext(), uid, Toast.LENGTH_LONG).show();

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
//                PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    }, PERMISSION_WRITE_STORAGE);
//        }
        photoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                profilePhoto();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                uploadProfile();
                Toast.makeText(getContext(), uid, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void selectImage(Context context) {
        //Toast.makeText(getActivity(), "You reached the second thing.", Toast.LENGTH_LONG).show();
        final CharSequence[] options = { "Take a Photo", "Get from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(options[item].equals("Take a Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Get from Gallery")) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    Intent pickPhoto = new Intent();
//                    Intent.setType("image/*");

//                    startActivityForResult(pickPhoto , 1);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void profilePhoto(){

        selectImage(getContext());
    }

    private void injectData(){

        findDownloadURL();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> list = new ArrayList<String>();
                ArrayList<String> rec = new ArrayList<>();
                cm.setDiplayName(editText.getText().toString());
                cm.setFavorites(list);
                cm.setPhotoName(uid);
                cm.setDisplayID(uid);
                cm.setRecentPlaces(rec);
                String uploadID = uid;
                mDatabaseRef.child(uploadID).setValue(cm);
                db.collection("Customer").document(firebaseAuth.getUid()).set(cm);
                goHome(null);
            }
        }, 500);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode != RESULT_CANCELED){
            switch(requestCode) {
                case 0:
                    if(resultCode == RESULT_OK && data != null){
                        cameraImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(cameraImage);
                        cameraPhoto = true;
                        galleryImage = false;
                    }
                    break;
                case 1:
                    checkLocationRequest();
                    Toast.makeText(getActivity(), "Reached CASE 1", Toast.LENGTH_LONG).show();
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage =  data.getData();
                        imageView.setImageURI(selectedImage);
                        cameraPhoto = false;
                        galleryImage = true;
                    }
                    break;
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void findDownloadURL() {
        StorageReference ref = mStorageRef.child(pathURL);
//        String answer = ref.getDownloadUrl().toString();
//        Toast.makeText(getContext(), "YOU GOT THIS --> " + answer, Toast.LENGTH_LONG).show();
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                //Toast.makeText(getContext(),  url, Toast.LENGTH_LONG).show();
                //photoURL = url;
                cm.setPhotoUrl(url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "ERROR!!!!", Toast.LENGTH_LONG).show();
            }
        });
        //return photoURL;
    }

    private void uploadProfile(){
//        firebaseAuth = FirebaseAuth.getInstance();
//        final String uid = firebaseAuth.getUid();
        if(!editText.getText().toString().equals(""))
        {
            if(galleryImage == true){
                if(selectedImage != null){
                    StorageReference fileReference = mStorageRef.child(uid + "." + getFileExtension(selectedImage));
                    pathURL = uid + "." + getFileExtension(selectedImage);
                    fileReference.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getActivity(), "Successfully Uploaded Image", Toast.LENGTH_LONG).show();
//                                ArrayList<String> list = new ArrayList<String>();
//                                ArrayList<String> rec = new ArrayList<>();
//                                CustomerModel cm = new CustomerModel();
//                                cm.setDiplayName(editText.getText().toString());
//                                cm.setFavorites(list);
//                                cm.setPhotoName(uid);
//                                cm.setDisplayID(uid);
//                                cm.setPhotoUrl(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                                cm.setRecentPlaces(rec);
//                                String uploadID = uid;
//                                mDatabaseRef.child(uploadID).setValue(cm);
//                                db.collection("Customer").document(firebaseAuth.getUid()).set(cm);
                                    //goHome(null);
                                    injectData();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                                }
                            });
                } else {
                    Toast.makeText(getActivity(), "No file selected.", Toast.LENGTH_LONG).show();
                }
            } else if(cameraPhoto == true){
                firebaseAuth = FirebaseAuth.getInstance();
                final String uid = firebaseAuth.getUid();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                cameraImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] b = stream.toByteArray();

                StorageReference fileReference = mStorageRef.child(uid + ".jpg");
                pathURL = uid + ".jpg";
                fileReference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(getActivity(), "uploaded", Toast.LENGTH_SHORT).show();

                        //findDownloadURL();
                        injectData();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(CameraActivity.this,"failed",Toast.LENGTH_LONG).show();


                    }
                });
            } else {
                Toast.makeText(getContext(), "Please select an image...", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Form Incomplete", Toast.LENGTH_SHORT).show();
        }


    }

    private void goHome(View view){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(LOCATION_REQUEST)
    private void checkLocationRequest() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,"Please grant permission",
                    LOCATION_REQUEST, perms);
        }
    }
}
