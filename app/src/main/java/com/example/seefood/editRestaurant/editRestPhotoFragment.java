package com.example.seefood.editRestaurant;

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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
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

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class editRestPhotoFragment extends Fragment {

    public editRestPhotoFragment() {
        // Required empty public constructor
    }

    private static final int LOCATION_REQUEST = 222;
    private static final int GALLERY_REQUEST_CODE = 123;
    private Uri selectedImage;
    private Bitmap cameraImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    String pathURL;
    Boolean galleryImage = false;
    Boolean cameraPhoto =  false;

    Button nextButton;
    Button addPhoto;
    ImageView restPhoto;

    Bundle bundle;

    RestaurantModel dispRest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_rest_photo, container, false);

        nextButton = view.findViewById(R.id.nextButton);
        addPhoto = view.findViewById(R.id.addRestPhoto);
        restPhoto = view.findViewById(R.id.imageView4);

        nextButton.setVisibility(View.GONE);

        bundle = getArguments();
        assert bundle != null;
        dispRest = bundle.getParcelable("restaurant");

        Glide.with(getContext())
                .load(dispRest.getPhotoURL())
                .into(restPhoto);

        mStorageRef = FirebaseStorage.getInstance().getReference("restProfilePhotos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Restaurants");

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

        return view;
    }

    private void uploadPhoto() {

        if (galleryImage == true) {
            firebaseAuth = FirebaseAuth.getInstance();
            final String uid = firebaseAuth.getUid();
            if (selectedImage != null) {
                StorageReference fileReference = mStorageRef.child(uid + "." + getFileExtension(selectedImage));
                //pathURL = fileReference.toString();
                pathURL = uid + "." + getFileExtension(selectedImage);
                fileReference.putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getActivity(), "Successfully Uploaded Image", Toast.LENGTH_LONG).show();
                                //photoURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
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

                            }
                        });
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
            pathURL = dispRest.getPhotoURL();
            injectData();
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void choosePhoto(){
        selectImage(getContext());
    }

    private void selectImage(Context context){
        final CharSequence[] options = {"Take a Photo", "Get from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose a photo for the meal");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(options[item].equals("Take a Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    galleryImage = false;
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Get from Gallery")) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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
                        cameraImage = (Bitmap) data.getExtras().get("data");
                        //selectedImage = getImageUri(getContext(), cameraImage);
                        restPhoto.setImageBitmap(cameraImage);
                        //Toast.makeText(getContext(), "Not Supported, Please Select from Gallery.", Toast.LENGTH_SHORT).show();
                        nextButton.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    checkLocationRequest();
                    //Toast.makeText(getActivity(), "Reached CASE 1", Toast.LENGTH_LONG).show();
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage =  data.getData();
                        restPhoto.setImageURI(selectedImage);
                        galleryImage = true;
                        nextButton.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
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
                dispRest.setPhotoURL(url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "ERROR!!!!", Toast.LENGTH_LONG).show();
            }
        });
        //return photoURL;
    }

    public void injectData(){
        findDownloadURL();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //dispRest.setPhotoURL(pathURL);
                db.collection("Restaurants").document(Objects.requireNonNull(firebaseAuth.getUid())).set(dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_fragment, new displayRestaurantProfile());
                ft.commit();
            }
        },500);
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
