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
import android.view.MotionEvent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.seefood.R;
import com.example.seefood.displayProfiles.displayRestaurantProfile;
import com.example.seefood.models.MealModel;
import com.example.seefood.models.RestaurantModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class manageMenuItemFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    public manageMenuItemFragment() {
        // Required empty public constructor
    }

    Button photoButton;
    Button cancelButton;
    Button addButton;
    ImageView restPhoto;
    Button deleteItemButton;

    String whereMeal = "Breakfast";

    Boolean galleryImage = false;
    Boolean cameraSelected = false;
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


    MealModel myMeal;

    private static final int LOCATION_REQUEST = 222;
    private static final int GALLERY_REQUEST_CODE = 123;
    private Uri selectedImage;
    private Bitmap cameraImage;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser;
    String pathURL;
    String uid;

    String chosenMenu;
    int positionInMenu;

    RestaurantModel rm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_menu_item, container, false);

        currentUser = firebaseAuth.getCurrentUser();
        uid = currentUser.getUid();

        getRestaurant();

        mStorageRef = FirebaseStorage.getInstance().getReference("mealPhotos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Restaurants");

        foodName = view.findViewById(R.id.foodNameField);
        calories = view.findViewById(R.id.calorieField);
        description = view.findViewById(R.id.mealDescription);
        restPhoto = view.findViewById(R.id.restPhoto);

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
        myMeal = bundle.getParcelable("meal");
        positionInMenu = bundle.getInt("indexPosition");
        chosenMenu = bundle.getString("foodType");

        Glide.with(getContext())
                .load(myMeal.getPhotoURL())
                .into(restPhoto);

        if(myMeal.getAllergies().get("Dairy")){
            dairybox.setChecked(true);
        }
        if(myMeal.getAllergies().get("Eggs")){
            eggbox.setChecked(true);
        }
        if(myMeal.getAllergies().get("Fish")){
            fishbox.setChecked(true);
        }
        if(myMeal.getAllergies().get("Shellfish")){
            shellbox.setChecked(true);
        }
        if(myMeal.getAllergies().get("Tree Nuts")){
            treebox.setChecked(true);
        }
        if(myMeal.getAllergies().get("Wheat")){
            wheatbox.setChecked(true);
        }
        if(myMeal.getAllergies().get("Soy")){
            soybox.setChecked(true);
        }
        if(myMeal.getAllergies().get("Peanuts")){
            peanutbox.setChecked(true);
        }

        foodName.setText(myMeal.getName());
        calories.setText(myMeal.getCalories());
        description.setText(myMeal.getDescription());

        whereMeal = chosenMenu;


        mealTypeSpinner = view.findViewById(R.id.mealTypeSpinner);
        ArrayAdapter<CharSequence> mealAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mealType, android.R.layout.simple_spinner_item);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypeSpinner.setAdapter(mealAdapter);
        mealTypeSpinner.setOnItemSelectedListener(this);

        mealTypeSpinner.setSelection(getIndex(mealTypeSpinner, chosenMenu));

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
//                final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                Fragment nextStep = new displayRestaurantProfile();
//                ft.replace(R.id.container_fragment, nextStep);
//                ft.commit();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("PrevMenu", FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

        deleteItemButton = view.findViewById(R.id.deleteItemButton);
        deleteItemButton.setVisibility(View.GONE);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemFromMenu();
            }
        });



        foodName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                foodName.setText(null);
                return false;
            }
        });

        calories.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                calories.setText(null);
                return false;
            }
        });

        description.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                description.setText(null);
                return false;
            }
        });




        return view;
    }

    private void deleteItemFromMenu(){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Menu Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getContext(), dispRest.printRest(dispRest), Toast.LENGTH_LONG).show();
                        int locationIndex = bundle.getInt("indexPosition");
                        //Objects.requireNonNull(dispRest.getOfferings().get(whereMeal)).set(locationIndex, myMeal);
                        Objects.requireNonNull(dispRest.getOfferings().get(whereMeal)).remove(locationIndex);
                        Collections.sort(dispRest.getOfferings().get(whereMeal), new Comparator<MealModel>(){
                            public int compare(MealModel m1, MealModel m2){
                                return m1.getName().compareTo(m2.getName());
                            }
                        });
                        db.collection("Restaurants").document(Objects.requireNonNull(firebaseAuth.getUid())).set(dispRest);
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.container_fragment, new displayRestaurantProfile());
                        ft.commit();

                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    //Toast.makeText(getContext(), rm.printRest(rm), Toast.LENGTH_LONG).show();
    }

    private void getRestaurant() {
        CollectionReference rp = db.collection("Restaurants");
        Query queryRestaurant = rp.whereEqualTo("owner", uid);
        queryRestaurant.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        dispRest = document.toObject(RestaurantModel.class);
                        deleteItemButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mealTypeSpinner.setSelection(getIndex(mealTypeSpinner, chosenMenu));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private int getIndex(Spinner spinner, String mealString){
        int index = 0;
        for(int i=0; i<spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).equals(mealString)){
                index = i;
            }
            //return index;
        }
        return index;
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
                    cameraSelected = false;
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
                        cameraSelected = true;
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

    private void findDownloadURL() {
        StorageReference ref = mStorageRef.child(pathURL);
        if(selectedImage != null || cameraImage != null){
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
        } else {
            //Do nothing
        }

        //return photoURL;
    }

    private void uploadPhoto() {

        if (galleryImage == true) {
            firebaseAuth = FirebaseAuth.getInstance();
            final String uid = firebaseAuth.getUid();
            if (selectedImage != null) {
                String substr = myMeal.getPhotoName().substring(0,myMeal.getPhotoName().indexOf("."));
                StorageReference fileReference = mStorageRef.child(substr + "." + getFileExtension(selectedImage));
                //pathURL = fileReference.toString();
                pathURL = substr + "." + getFileExtension(selectedImage);
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

        } else if(cameraSelected ==  true){
            firebaseAuth = FirebaseAuth.getInstance();
            final String uid = firebaseAuth.getUid();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            cameraImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] b = stream.toByteArray();
            String substr = myMeal.getPhotoName().substring(0,myMeal.getPhotoName().indexOf("."));
            StorageReference fileReference = mStorageRef.child(substr + ".jpg");
            pathURL = substr + ".jpg";
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
            Toast.makeText(getContext(), "Update Applied", Toast.LENGTH_SHORT).show();
            injectData();
        }
    }

    public void injectData(){
        findDownloadURL();

        int locationIndex = bundle.getInt("indexPosition");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Objects.requireNonNull(dispRest.getOfferings().get(whereMeal)).set(locationIndex, myMeal);
                Collections.sort(dispRest.getOfferings().get(whereMeal), new Comparator<MealModel>(){
                    public int compare(MealModel m1, MealModel m2){
                        return m1.getName().compareTo(m2.getName());
                    }
                });
                db.collection("Restaurants").document(Objects.requireNonNull(firebaseAuth.getUid())).set(dispRest);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container_fragment, new displayRestaurantProfile());
                ft.commit();
            }
        },500);
    }

    public void validateInput(){
        if(cameraImage == null && selectedImage == null){
            //Toast.makeText(getContext(), "Please Select an Image", Toast.LENGTH_SHORT).show();
            if(!foodName.getText().toString().trim().equals("") && !calories.getText().toString().trim().equals("") && !description.getText().toString().trim().equals("")) {
                myMeal.setName(foodName.getText().toString());
                myMeal.setCalories(calories.getText().toString());
                myMeal.setDescription(description.getText().toString());
                myMeal.setType(whereMeal);

                if (dairybox.isChecked()) {
                    myMeal.getAllergies().put("Dairy", true);
                } else {
                    myMeal.getAllergies().put("Dairy", false);
                }

                if (eggbox.isChecked()) {
                    myMeal.getAllergies().put("Eggs", true);
                } else {
                    myMeal.getAllergies().put("Eggs", false);
                }

                if (fishbox.isChecked()) {
                    myMeal.getAllergies().put("Fish", true);
                } else {
                    myMeal.getAllergies().put("Fish", false);
                }

                if (shellbox.isChecked()) {
                    myMeal.getAllergies().put("Shellfish", true);
                } else {
                    myMeal.getAllergies().put("Shellfish", false);
                }

                if (treebox.isChecked()) {
                    myMeal.getAllergies().put("Tree Nuts", true);
                } else {
                    myMeal.getAllergies().put("Tree Nuts", false);
                }

                if (wheatbox.isChecked()) {
                    myMeal.getAllergies().put("Wheat", true);
                } else {
                    myMeal.getAllergies().put("Wheat", false);
                }

                if (soybox.isChecked()) {
                    myMeal.getAllergies().put("Soy", true);
                } else {
                    myMeal.getAllergies().put("Soy", false);
                }

                if (peanutbox.isChecked()) {
                    myMeal.getAllergies().put("Peanuts", true);
                } else {
                    myMeal.getAllergies().put("Peanuts", false);
                }

                uploadPhoto();
            } else {
                Toast.makeText(getContext(), "Error: Form not complete", Toast.LENGTH_SHORT).show();
            }
        } else {
            if(!foodName.getText().toString().trim().equals("") && !calories.getText().toString().trim().equals("") && !description.getText().toString().trim().equals("")){
                myMeal.setName(foodName.getText().toString());
                myMeal.setCalories(calories.getText().toString());
                myMeal.setDescription(description.getText().toString());
                myMeal.setType(whereMeal);

                if(dairybox.isChecked()){
                    myMeal.getAllergies().put("Dairy", true);
                } else {
                    myMeal.getAllergies().put("Dairy", false);
                }

                if(eggbox.isChecked()){
                    myMeal.getAllergies().put("Eggs", true);
                } else {
                    myMeal.getAllergies().put("Eggs", false);
                }

                if(fishbox.isChecked()){
                    myMeal.getAllergies().put("Fish", true);
                } else {
                    myMeal.getAllergies().put("Fish", false);
                }

                if(shellbox.isChecked()){
                    myMeal.getAllergies().put("Shellfish", true);
                } else {
                    myMeal.getAllergies().put("Shellfish", false);
                }

                if(treebox.isChecked()){
                    myMeal.getAllergies().put("Tree Nuts", true);
                } else {
                    myMeal.getAllergies().put("Tree Nuts", false);
                }

                if(wheatbox.isChecked()){
                    myMeal.getAllergies().put("Wheat", true);
                } else {
                    myMeal.getAllergies().put("Wheat", false);
                }

                if(soybox.isChecked()){
                    myMeal.getAllergies().put("Soy", true);
                } else {
                    myMeal.getAllergies().put("Soy", false);
                }

                if(peanutbox.isChecked()){
                    myMeal.getAllergies().put("Peanuts", true);
                } else {
                    myMeal.getAllergies().put("Peanuts", false);
                }

                uploadPhoto();
            } else {
                Toast.makeText(getContext(), "Error: Form not complete", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
