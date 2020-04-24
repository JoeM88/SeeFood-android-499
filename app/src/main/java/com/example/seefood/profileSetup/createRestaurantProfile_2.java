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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.example.seefood.models.RestaurantModel;
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

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class createRestaurantProfile_2 extends Fragment {

    public createRestaurantProfile_2() {
        // Required empty public constructor
    }

    String photoURL;

    Button nextButton;
    Button addPhoto;
    ImageView restPhoto;

    private static final int LOCATION_REQUEST = 222;
    private static final int GALLERY_REQUEST_CODE = 123;

    private Uri selectedImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;

    Bundle bundle;

    RestaurantModel rm = new RestaurantModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_restaurant_profile_2, container, false);
        nextButton = view.findViewById(R.id.nextButton);
        addPhoto = view.findViewById(R.id.addRestPhoto);
        restPhoto = view.findViewById(R.id.imageView4);

        bundle = getArguments();

        rm.setRestName(bundle.getString("name"));
        rm.setStreetAddress(bundle.getString("address"));
        rm.setCity(bundle.getString("city"));
        rm.setState(bundle.getString("state"));
        rm.setZipCode(bundle.getString("zipCode"));
        rm.setPhoneNumber(bundle.getString("phone"));

        mStorageRef = FirebaseStorage.getInstance().getReference("restProfilePhotos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Restaurants");


        Toast.makeText(getContext(), rm.printRest(rm), Toast.LENGTH_LONG).show();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToStep3();
                if(selectedImage != null){
                    uploadPhoto();

                } else {
                    Toast.makeText(getContext(), "Please select a photo", Toast.LENGTH_LONG).show();
                }
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                profilePhoto();
            }
        });
        return view;
    }

    private void goToStep3() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        bundle.putString("photoURL", photoURL);
        String uid = firebaseAuth.getUid();
        bundle.putString("photoName", uid);
        bundle.putString("owner", uid);
        Fragment nextStep = new createRestaurantProfile_3();
        nextStep.setArguments(bundle);
        ft.replace(R.id.createProf_container, nextStep);
        ft.commit();
    }

    private void profilePhoto() {
        selectImage(getContext());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_CANCELED){
            switch(requestCode) {
                case 0:
                    if(resultCode == RESULT_OK && data != null){
                        Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                        restPhoto.setImageBitmap(cameraImage);
                    }
                    break;
                case 1:
                    checkLocationRequest();
                    Toast.makeText(getActivity(), "Reached CASE 1", Toast.LENGTH_LONG).show();
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage =  data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImage != null) {
//                            Cursor cursor = getContext().getContentResolver().query(selectedImage,
//                                    filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                cursor.close();
//                            }
//                        }
                        restPhoto.setImageURI(selectedImage);

                    }
                    break;
            }
        }
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

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void uploadPhoto(){
        firebaseAuth = FirebaseAuth.getInstance();
        final String uid = firebaseAuth.getUid();
        if(selectedImage != null){
            StorageReference fileReference = mStorageRef.child(uid + "." + getFileExtension(selectedImage));
            fileReference.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "Successfully Uploaded Image", Toast.LENGTH_LONG).show();
                            photoURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            goToStep3();

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

                        }
                    });
        }
    }



}