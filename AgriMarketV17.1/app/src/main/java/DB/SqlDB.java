package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import pojos.OfferClass;
import pojos.User;

public class SqlDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "agri_market_db";

    //TODO User-------------------------------------------------------
    private static final String TABLE_USER = "user";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOBILE= "mobile";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_IMAGE_BYTE_ARRAY = "image_byte_array";
   //TODO Table UserOffers------------------------------------------------------
    private static final String TABLE_USER_OFFERS = "user_offers";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_LOCATION = "user_location";
    private static final String KEY_OFFER_IMAGE_URL = "offer_image_url";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRODUCT_ID ="product_id";
    private static final String KEY_PRICE ="price";
    private static final String KEY_QUNTITY ="quntity";
    //-------------------------------------------------------------------
    Context context;

    public SqlDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USER_ID + " INTEGER ,"
                + KEY_EMAIL + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_MOBILE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_IMAGE_URL + " TEXT,"
                + KEY_IMAGE_BYTE_ARRAY + " BLOB )";

        db.execSQL(CREATE_USER_TABLE);
//------------------------------------------------------------------------------------------
        String CREATE_USER_OFFERS_TABLE = "CREATE TABLE " + TABLE_USER_OFFERS + "("
                + KEY_START_DATE + " TEXT ,"
                + KEY_USER_PHONE + " TEXT,"
                + KEY_USER_LOCATION + " TEXT,"
                + KEY_OFFER_IMAGE_URL + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_PRODUCT_ID + " INTEGER ,"
                + KEY_PRICE + " INTEGER ,"
                + KEY_QUNTITY + " INTEGER )";


        db.execSQL(CREATE_USER_OFFERS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_OFFERS);
        onCreate(db);
    }



    public void signUp(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID,user.getId());
        values.put(KEY_EMAIL,user.getEmail());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_MOBILE, user.getMobile());
        values.put(KEY_LOCATION,user.getLocation());
        values.put(KEY_IMAGE_URL,user.getImageUrl());
        values.put(KEY_IMAGE_BYTE_ARRAY, user.getImage());
        long l=db.insert(TABLE_USER, null, values);
        Log.i("++DB:",((Long)l).toString());
        Toast.makeText(context,((Long)l).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(context, "inserted successfully", Toast.LENGTH_LONG).show();

        Log.i("++imageurl", getUserData().getImageUrl() + getUserData().getEmail() + getUserData().getImage().toString());

        db.close();
    }

    public User getUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        User user=new User();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER  , null);

        Log.i("++", ((Integer) cursor.getCount()).toString());

        if (cursor.moveToNext()) {
            user.setId(cursor.getInt(0));
            user.setEmail(cursor.getString(1));
            user.setName(cursor.getString(2));
            user.setMobile(cursor.getString(3));
            user.setLocation(cursor.getString(4));
            user.setImageUrl(cursor.getString(5));
            user.setImage(cursor.getBlob(6));
        }else {
            Log.i("++GetUser","No user in db");
        }
        db.close();
        return user;
    }
    public void deleteUserByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, KEY_EMAIL + " = '" + email + "'", null);
        db.close();
    }


    public void insertUserOffer(ArrayList<OfferClass> offersList){
        SQLiteDatabase db = this.getWritableDatabase();

        for(OfferClass offer:offersList){

            ContentValues values = new ContentValues();
            values.put(KEY_START_DATE,offer.getStart_date());
            values.put(KEY_USER_PHONE,offer.getUserPhone());
            values.put(KEY_USER_LOCATION,offer.getUserLocation());
            values.put(KEY_OFFER_IMAGE_URL,offer.getImageUrl());
            values.put(KEY_DESCRIPTION,offer.getDescription());
            values.put(KEY_PRODUCT_ID,offer.getProducctId());
            values.put(KEY_PRICE,offer.getPrice());
            values.put(KEY_QUNTITY, offer.getQuntity());
            long l=db.insert(TABLE_USER_OFFERS, null, values);
            Log.i("++offersInserted",((Long)l).toString());
        }

        Toast.makeText(context, "offers inserted successfully", Toast.LENGTH_LONG).show();
        db.close();
    }

    public ArrayList<OfferClass> getUserOffers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<OfferClass> arrayList=new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER_OFFERS  , null);
        while (cursor.moveToNext()) {
            OfferClass offerObj=new OfferClass();
            offerObj.setStart_date(cursor.getString(0));
            offerObj.setUserPhone(cursor.getString(1));
            offerObj.setUserLocation(cursor.getString(2));
            offerObj.setImageUrl(cursor.getString(3));
            offerObj.setDescription(cursor.getString(4));
            offerObj.setProducctId(cursor.getInt(5));
            offerObj.setPrice(cursor.getInt(6));
            offerObj.setQuntity(cursor.getInt(7));
            arrayList.add(offerObj);
        }

        db.close();
        return arrayList;
    }

    public void deleteUserOffers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_USER_OFFERS);
        Log.i("++offersDeleted","Yes");
        db.close();
    }
}