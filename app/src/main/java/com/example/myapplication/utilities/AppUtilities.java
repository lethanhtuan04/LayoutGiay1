package com.example.myapplication.utilities;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class AppUtilities {
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final int SELECT_PICTURE = 200;
    public static final int TAKE_PICTURE = 300;
    private static final int PERMISSION_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1000;
    public static String currentPhotoPath;
    public static Uri photoURI;

    private static final String PREFERENCES = "";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    public static String encode(@NotNull String plainString) {
        return Base64.getEncoder().withoutPadding().encodeToString(plainString.getBytes());
    }

    @NotNull
    public static String decode(String encodedString) {
        if (null == encodedString)
            return null;
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }

    public static void saveSession(@NotNull Context context, String email, String password) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL, AppUtilities.encode(email));
        editor.putString(PASSWORD, AppUtilities.encode(password));
        editor.commit();
    }

    @Nullable
//    public static Account getSession(@NotNull Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
//        String email = sharedPreferences.getString(EMAIL, "");
//        String password = sharedPreferences.getString(PASSWORD, "");
//        if (email.equals("") || password.equals(""))
//            return null;
//        AccountDbHelper accountDbHelper = new AccountDbHelper(context);
//        return accountDbHelper.login(decode(email), password);
//    }

    public static void clearSession(@NotNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(EMAIL);
        editor.remove(PASSWORD);
        editor.commit();
    }

    @NotNull
    public static String getDateTimeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        return date;
    }

    public static LocalDateTime stringToTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.parse(str, formatter);
    }

    @NotNull
    @Contract(" -> new")
    public static String generatePassword() {
        byte[] array = new byte[5]; // length is bounded by 7
        new Random().nextBytes(array);
        String str1 = new String(array, StandardCharsets.UTF_8);
        String str2 = decode(getDateTimeNow());
        return str1 + str2;
    }

    public static boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity,
                    "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.",
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    public static void setTakePhoto(View view) {
        try {
            dispatchTakePictureIntent(view.getContext(), TAKE_PICTURE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dispatchTakePictureIntent(Context context, int requestCode) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = createImageFile(context);
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(context,
                        "com.shopeeapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Activity activity = (Activity) context;
                activity.startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    @NotNull
    private static File createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static void setChoosePhoto(View view) {
        Activity activity = (Activity) view.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                activity.requestPermissions(permissions, PERMISSION_CODE);
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activity.startActivityForResult(intent, IMAGE_PICK_CODE);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            activity.startActivityForResult(intent, IMAGE_PICK_CODE);
        }
    }


    public static Uri setPic(ImageView imgAvt) {
        // Get the dimensions of the View
        int targetW = imgAvt.getWidth();
        int targetH = imgAvt.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imgAvt.setImageBitmap(bitmap);
        return photoURI;
    }

}

