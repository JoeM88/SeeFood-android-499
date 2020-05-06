package com.example.seefood.editCustomer;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.displayProfiles.dispCustomerProfile;
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
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class editCustomerProfileFragment extends Fragment {

    public editCustomerProfileFragment() {
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
    Boolean cameraPhoto = false;

    EditText nameField;
    Button addPhoto;
    Button update;
    ImageView myAvatar;

    Bundle bundle;
    CustomerModel customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_customer_profile, container, false);
        nameField = view.findViewById(R.id.editText);
        addPhoto = view.findViewById(R.id.photoButton);
        update = view.findViewById(R.id.submitProfileButton);
        myAvatar = view.findViewById(R.id.my_avatar);

        bundle = getArguments();

        customer = bundle.getParcelable("customer");

        assert customer != null;
        nameField.setText(customer.getDiplayName());

        Glide.with(getContext())
                .load(customer.getPhotoUrl())
                .into(myAvatar);

        mStorageRef = FirebaseStorage.getInstance().getReference("profilePhotos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Customer");

        nameField.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nameField.setText(null);
                return false;
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });

        return view;
    }

    public void injectData(){
        findDownloadURL();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //dispRest.setPhotoURL(pathURL);
                db.collection("Customer").document(Objects.requireNonNull(firebaseAuth.getUid())).set(customer);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_fragment, new dispCustomerProfile());
                ft.commit();
            }
        },500);
    }

    public void checkInput(){
        if(!nameField.getText().toString().trim().equals("")){
            customer.setDiplayName(nameField.getText().toString());
            uploadPhoto();
        } else {
            Toast.makeText(getContext(), "ERROR: Form Incomplete", Toast.LENGTH_SHORT).show();
        }
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
                        myAvatar.setImageBitmap(cameraImage);
                        //Toast.makeText(getContext(), "Not Supported, Please Select from Gallery.", Toast.LENGTH_SHORT).show();
                        galleryImage = false;
                        cameraPhoto = true;

                    }
                    break;
                case 1:
                    checkLocationRequest();
                    //Toast.makeText(getActivity(), "Reached CASE 1", Toast.LENGTH_LONG).show();
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage =  data.getData();
                        myAvatar.setImageURI(selectedImage);
                        galleryImage = true;
                        cameraPhoto = false;
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
                customer.setPhotoUrl(url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getContext(), "ERROR!!!!", Toast.LENGTH_LONG).show();
            }
        });
        //return photoURL;
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
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
            firebaseAuth = FirebaseAuth.getInstance();
            final String uid = firebaseAuth.getUid();
            pathURL = customer.getPhotoUrl();
            injectData();
        }
    }

}
