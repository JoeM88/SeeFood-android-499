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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.seefood.R;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.models.MealModel;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class editMenuFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    public editMenuFragment() {
        // Required empty public constructor
    }

    Button photoButton;
    Button cancelButton;
    Button addButton;
    ImageView restPhoto;

    String whereMeal = "Breakfast";

    Boolean galleryImage = false;
    Bundle bundle;
    Spinner mealTypeSpinner;

    RestaurantModel dispRest;

    EditText foodName;
    EditText calories;
    EditText description;
    String setDateTime;

    CheckBox dairybox;
    CheckBox eggbox;
    CheckBox fishbox;
    CheckBox shellbox;
    CheckBox treebox;
    CheckBox wheatbox;
    CheckBox soybox;
    CheckBox peanutbox;


    MealModel myMeal = new MealModel();

    private static final int LOCATION_REQUEST = 222;
    private static final int GALLERY_REQUEST_CODE = 123;
    private Uri selectedImage;
    private Bitmap cameraImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth;
    String pathURL;

    HashMap<String, Boolean> allergies = new HashMap<String, Boolean>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_menu, container, false);

        mStorageRef = FirebaseStorage.getInstance().getReference("mealPhotos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Restaurants");

        mealTypeSpinner = view.findViewById(R.id.mealTypeSpinner);
        ArrayAdapter<CharSequence> mealAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mealType, android.R.layout.simple_spinner_item);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypeSpinner.setAdapter(mealAdapter);
        mealTypeSpinner.setOnItemSelectedListener(this);

        allergies.put("Dairy", false);
        allergies.put("Eggs", false);
        allergies.put("Fish", false);
        allergies.put("Shellfish", false);
        allergies.put("Tree Nuts", false);
        allergies.put("Wheat", false);
        allergies.put("Soy", false);
        allergies.put("Peanuts", false);

        foodName = view.findViewById(R.id.foodNameField);
        calories = view.findViewById(R.id.calorieField);
        description = view.findViewById(R.id.mealDescription);

        dairybox = view.findViewById(R.id.dairyBox);
        eggbox = view.findViewById(R.id.eggBox);
        fishbox = view.findViewById(R.id.fishBox);
        shellbox = view.findViewById(R.id.shellfishBox);
        treebox = view.findViewById(R.id.treenutsBox);
        wheatbox = view.findViewById(R.id.wheatBox);
        soybox = view.findViewById(R.id.soyBox);
        peanutbox = view.findViewById(R.id.peanutBox);


        bundle = getArguments();
        assert bundle != null;
        dispRest = bundle.getParcelable("restaurant");

        String type = bundle.getString("type");

        foodName = view.findViewById(R.id.foodNameField);
        restPhoto = view.findViewById(R.id.restPhoto);
        setDateTime = dateTime();

        photoButton = view.findViewById(R.id.photoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!foodName.getText().toString().trim().equals("")){
                    choosePhoto();
                } else {
                    Toast.makeText(getContext(), "Please enter a name first.", Toast.LENGTH_LONG).show();
                }
                //choosePhoto();
            }
        });

        cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment nextStep = new displayRestaurantProfile();
                ft.replace(R.id.container_fragment, nextStep);
                ft.commit();
            }
        });

        addButton = view.findViewById(R.id.submitButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //uploadPhoto();
                validateInput();
            }
        });
        return view;
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
                    }
                    break;
                case 1:
                    checkLocationRequest();
                    //Toast.makeText(getActivity(), "Reached CASE 1", Toast.LENGTH_LONG).show();
                    if (resultCode == RESULT_OK && data != null) {
                        selectedImage =  data.getData();
                        restPhoto.setImageURI(selectedImage);
                        galleryImage = true;
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

    public String dateTime(){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String secs = "" + System.currentTimeMillis() / 1000;
        return date + secs;
    }

    private void findDownloadURL() {
        StorageReference ref = mStorageRef.child(pathURL);
        myMeal.setPhotoName(pathURL);
//        String answer = ref.getDownloadUrl().toString();
//        Toast.makeText(getContext(), "YOU GOT THIS --> " + answer, Toast.LENGTH_LONG).show();
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                //Toast.makeText(getContext(),  url, Toast.LENGTH_LONG).show();
                //photoURL = url;
                //rm.setPhotoURL(url);
                //Toast.makeText(getContext(), url, Toast.LENGTH_LONG).show();
                myMeal.setPhotoURL(url);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "ERROR: URL NOT SET.", Toast.LENGTH_LONG).show();
            }
        });
        //return photoURL;
    }

    private void uploadPhoto() {

        if (galleryImage == true) {
            firebaseAuth = FirebaseAuth.getInstance();
            final String uid = firebaseAuth.getUid();
            if (selectedImage != null) {
                StorageReference fileReference = mStorageRef.child(uid + setDateTime + "." + getFileExtension(selectedImage));
                //pathURL = fileReference.toString();
                pathURL = uid + setDateTime + "." + getFileExtension(selectedImage);
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

        } else {
            firebaseAuth = FirebaseAuth.getInstance();
            final String uid = firebaseAuth.getUid();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            cameraImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] b = stream.toByteArray();

            StorageReference fileReference = mStorageRef.child(uid + setDateTime + ".jpg");
            pathURL = uid + setDateTime + ".jpg";
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

        }

    }

    public void injectData(){
        myMeal.setAllergies(allergies);
        findDownloadURL();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dispRest.getOfferings().get(whereMeal).add(myMeal);
                db.collection("Restaurants").document(Objects.requireNonNull(firebaseAuth.getUid())).set(dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_fragment, new displayRestaurantProfile());
                ft.commit();
            }
        },500);
    }

    public void validateInput(){
        if(cameraImage == null && selectedImage == null){
            Toast.makeText(getContext(), "Please Select an Image", Toast.LENGTH_SHORT).show();
        } else {
            if(!foodName.getText().toString().trim().equals("") && !calories.getText().toString().trim().equals("") && !description.getText().toString().trim().equals("")){
                myMeal.setName(foodName.getText().toString());
                myMeal.setCalories(calories.getText().toString());
                myMeal.setDescription(description.getText().toString());
                myMeal.setType(whereMeal);

                if(dairybox.isChecked()){
                    allergies.put("Dairy", true);
                } else {
                    allergies.put("Dairy", false);
                }

                if(eggbox.isChecked()){
                    allergies.put("Eggs", true);
                } else {
                    allergies.put("Eggs", false);
                }

                if(fishbox.isChecked()){
                    allergies.put("Fish", true);
                } else {
                    allergies.put("Fish", false);
                }

                if(shellbox.isChecked()){
                    allergies.put("Shellfish", true);
                } else {
                    allergies.put("Shellfish", false);
                }

                if(treebox.isChecked()){
                    allergies.put("Tree Nuts", true);
                } else {
                    allergies.put("Tree Nuts", false);
                }

                if(wheatbox.isChecked()){
                    allergies.put("Wheat", true);
                } else {
                    allergies.put("Wheat", false);
                }

                if(soybox.isChecked()){
                    allergies.put("Soy", true);
                } else {
                    allergies.put("Soy", false);
                }

                if(peanutbox.isChecked()){
                    allergies.put("Peanuts", true);
                } else {
                    allergies.put("Peanuts", false);
                }

                uploadPhoto();
            } else {
                Toast.makeText(getContext(), "Error: Form not complete", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        whereMeal = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
