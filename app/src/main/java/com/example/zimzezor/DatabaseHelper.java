package com.example.zimzezor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "server_db";
    private static final int DATABASE_VERSION = 13;   // ilk başta birdi veritabanı değiştirdikçe güncelledim.

    // Tablonun adı ve sütun adları
    public static final String TABLE_NAME = "veriler";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_NAME = "name";

    // Veritabanı oluşturulurken çağrılacak SQL komutu
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  // ID sütunu, otomatik artan şekilde
                    COLUMN_URL + " TEXT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Veritabanı ilk oluşturulduğunda çağrılır
        db.execSQL(TABLE_CREATE);
        insertInitialData(db);
    }
    private void insertInitialData(SQLiteDatabase db) {
        // Engelsiz zemin kat harita
        ContentValues values = new ContentValues();values.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1S5120GMyAPRw3hqgyw_JZQuKNUgM5ofA");
        values.put(COLUMN_NAME, "engelsizzeminkat");db.insert(TABLE_NAME, null, values);

        // lyeclabs harita
        ContentValues values2 = new ContentValues();values2.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=19aQuVu_uz7_NT_w_UYpplAjR4AkwRF1J");
         values2.put(COLUMN_NAME, "lyeclabs");db.insert(TABLE_NAME, null, values2);

         //zeminZON-lyec arası video
        ContentValues values3 = new ContentValues();values3.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=19Xe0jlQoe9BYemEBJ5oIC4y4RUnJZlU5");
        values3.put(COLUMN_NAME, "zeminZonlyecvideo");db.insert(TABLE_NAME, null, values3);

        //lyeclabsgirisfoto
        ContentValues values4 = new ContentValues();values4.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1W_IqVBK4UrZ3gtVjgeT8qXFZA7whCrBk");
        values4.put(COLUMN_NAME, "lyeclabsgiris");db.insert(TABLE_NAME, null, values4);

        //lyec-zeminZONvideo
        ContentValues values5 = new ContentValues();values5.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1RWs_rancEq_y23XkKXsCN-pUsnymf5Zr");
        values5.put(COLUMN_NAME, "lyeczeminZonvideo");db.insert(TABLE_NAME, null, values5);

        //zemingirisfoto
        ContentValues values6 = new ContentValues();values6.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1sJRV83SP92gTfA15Mb2suEdq3aBfLBEV");
        values6.put(COLUMN_NAME, "zemingiris");db.insert(TABLE_NAME, null, values6);

        //1.kat harita
        ContentValues values7 = new ContentValues();values7.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=10pavenp_p-fDXVAmJl3usOIKMHIC1wKZ");
        values7.put(COLUMN_NAME, "kat1map");db.insert(TABLE_NAME, null, values7);

        //zeminkatZON-danısma video
        ContentValues values8 = new ContentValues();values8.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1T6XAtLJr2q73m6E6bcFcUPp0U9YMv1Dj");
        values8.put(COLUMN_NAME, "zeminkatZon_danisma");db.insert(TABLE_NAME, null, values8);

        //zeminkatZON-bekleme salonu video
        ContentValues values9 = new ContentValues();values9.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1c17gxctkubpPFuFp1_PfNKiOv5Zp56bu");
        values9.put(COLUMN_NAME, "zeminkatZon_bekleme");db.insert(TABLE_NAME, null, values9);

        //zeminkatZON-sesli kütüphane video
        ContentValues values10 = new ContentValues();values10.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=131wVY1PT9D0pckKWKx1L98UUB3BY2ID7");
        values10.put(COLUMN_NAME, "zeminkatZon_sesli");db.insert(TABLE_NAME, null, values10);

        //zeminkatZON-mutfak video
        ContentValues values11 = new ContentValues();values11.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1Th4cA4-pVm01ECvBxq478TNXHdg6tS89");
        values11.put(COLUMN_NAME, "zeminkatZon_mutfak");db.insert(TABLE_NAME, null, values11);

        //zeminkatZON-wcvideo
        ContentValues values12 = new ContentValues();values12.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1L4V0i2rC09Dy9Z8MfNwJzD2atBtQ48cG");
        values12.put(COLUMN_NAME, "zeminkatZon_wc");db.insert(TABLE_NAME, null, values12);

        //zeminkatZON-asansorvideo
        ContentValues values13 = new ContentValues();values13.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1cikJBnsvMClsjA5wA4qZPjs-NdiiYHt-");
        values13.put(COLUMN_NAME, "zeminkatZon_asansor");db.insert(TABLE_NAME, null, values13);

        //zeminkatZON-1.katvideo
        ContentValues values14 = new ContentValues();values14.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=19pxv5ZDOVhUfSiB9YpiXk-LLPkG1lJBG");
        values14.put(COLUMN_NAME, "zeminkatZon_kat1");db.insert(TABLE_NAME, null, values14);

        //zemin asansor foto
        ContentValues values15 = new ContentValues();values15.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1EfWTRUXoBPfsJnfwiS4M0v2qF2zhK5Ps");
        values15.put(COLUMN_NAME, "zeminAsansor");db.insert(TABLE_NAME, null, values15);

        //zemin bekleme salonu foto
        ContentValues values16 = new ContentValues();values16.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1tWcUMRQ1n3ue2hxnEgRzbTebGSAeVQ5e");
        values16.put(COLUMN_NAME, "zeminBekleme");db.insert(TABLE_NAME, null, values16);

        //zemin danışma foto
        ContentValues values17 = new ContentValues();values17.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1UWLSkR6vGczjFZmjv-87opyyjEw7JWol");
        values17.put(COLUMN_NAME, "zeminDanisma");db.insert(TABLE_NAME, null, values17);

        //zemin sesli kütüphane foto
        ContentValues values18 = new ContentValues();values18.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1_AlXmitxUt2NznTQCKYd3D5DsdBVoaFN");
        values18.put(COLUMN_NAME, "zeminSesliKutup");db.insert(TABLE_NAME, null, values18);

        //zemin mutfak foto
        ContentValues values19 = new ContentValues();values19.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1fkfMeMAj0EZFxeiQZrtsUBThqb65VD2w");
        values19.put(COLUMN_NAME, "zeminMutfak");db.insert(TABLE_NAME, null, values19);

        //zemin wc foto
        ContentValues values20 = new ContentValues();values20.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1D13lCgNnlJeum9od65_xHrdqK_zakL4b");
        values20.put(COLUMN_NAME, "zeminWC");db.insert(TABLE_NAME, null, values20);

        //zemin 1.kat foto
        ContentValues values21 = new ContentValues();values21.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1SsCNI0NyMBtPEGfXWs9yJgDbJmWTpFNQ");
        values21.put(COLUMN_NAME, "kat1");db.insert(TABLE_NAME, null, values21);

        //kat1 wc foto
        ContentValues values22 = new ContentValues();values22.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1q4RDmj55CzL85ubO9llSAzcZTOXryrPZ");
        values22.put(COLUMN_NAME, "kat1WC");db.insert(TABLE_NAME, null, values22);

        //kat1 revir foto
        ContentValues values23 = new ContentValues();values23.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1cR7YwAMUZfbMn_Xe9w4M2EuW8kzA3IFg");
        values23.put(COLUMN_NAME, "kat1Revir");db.insert(TABLE_NAME, null, values23);

        //kat1 resim foto
        ContentValues values24 = new ContentValues();values24.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1TyqTFak6TmifxyXKm_ILLT9SBQemgtbk");
        values24.put(COLUMN_NAME, "kat1Resim");db.insert(TABLE_NAME, null, values24);

        //kat1 muzik foto
        ContentValues values25 = new ContentValues();values25.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1aRm9Y7S1Qvcf7JnhY34utfl70cr3Nf4u");
        values25.put(COLUMN_NAME, "kat1Muzik");db.insert(TABLE_NAME, null, values25);

        //kat1 idari foto
        ContentValues values26 = new ContentValues();values26.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1QG09lK4FPgdwkckmTJM3_a2V0nxhJzgf");
        values26.put(COLUMN_NAME, "kat1idari");db.insert(TABLE_NAME, null, values26);

        //kat1 bilgisayar foto
        ContentValues values27 = new ContentValues();values27.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1C5OJjhWD2mmshPSjGQlz2fq0Y52wc6C2");
        values27.put(COLUMN_NAME, "kat1bilgisayar");db.insert(TABLE_NAME, null, values27);

        //kat1 asansor foto
        ContentValues values28 = new ContentValues();values28.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1-YID4S1GGiqfpeJC5azgkbwCsaO3Cx3p");
        values28.put(COLUMN_NAME, "kat1asansor");db.insert(TABLE_NAME, null, values28);

        //kat1 aktivite foto
        ContentValues values29 = new ContentValues();values29.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1gP9JAuHvm4IkA8yV7t_vEuYKydkCZDHq");
        values29.put(COLUMN_NAME, "kat1aktivite");db.insert(TABLE_NAME, null, values29);

        //kat1 bilgisayar video
        ContentValues values30 = new ContentValues();values30.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1R_7EHdHG6jNrWNPtya0ChlXad_7u0seD");
        values30.put(COLUMN_NAME, "kat1_bilgisayar");db.insert(TABLE_NAME, null, values30);

        //kat1 revir video
        ContentValues values31 = new ContentValues();values31.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1CbECeE9WpbtN7o7PuOA9DHZzUdNbUAxX");
        values31.put(COLUMN_NAME, "kat1_revir");db.insert(TABLE_NAME, null, values31);

        //kat1 spor video
        ContentValues values32 = new ContentValues();values32.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=19XiDJ4sno72xlytQ3rLI2WTndUhic6WN");
        values32.put(COLUMN_NAME, "kat1_spor");db.insert(TABLE_NAME, null, values32);

        //kat1 wc video
        ContentValues values33 = new ContentValues();values33.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1bREyhlUEhWwTwP1BcwkuPDvYCL3yhB-u");
        values33.put(COLUMN_NAME, "kat1_wc");db.insert(TABLE_NAME, null, values33);

        //kat1 resim video
        ContentValues values34 = new ContentValues();values34.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=18usNZaI_h2_8U58GGPZwSs4FMWCUYp44");
        values34.put(COLUMN_NAME, "kat1_resim");db.insert(TABLE_NAME, null, values34);

        //kat1 muzik video
        ContentValues values35 = new ContentValues();values35.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1pQMdmzNQEIRQRCtPCgtmalkcWpOBiP9F");
        values35.put(COLUMN_NAME, "kat1_muzik");db.insert(TABLE_NAME, null, values35);

        //kat1 asansor video
        ContentValues values36 = new ContentValues();values36.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1miYEMkhClrZxAi_RzmAFIKkaUMg6i1z_");
        values36.put(COLUMN_NAME, "kat1_asansor");db.insert(TABLE_NAME, null, values36);

        //kat1 idari video
        ContentValues values37 = new ContentValues();values37.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1n7jetGKVyMygZ_tuvS0C9PXijKo96gI7");
        values37.put(COLUMN_NAME, "kat1_idari");db.insert(TABLE_NAME, null, values37);



        //lyeclabs- kat1 video
        ContentValues values38 = new ContentValues();values38.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1jL6vFUJFJ1c0vNoQspn7ruaUitO94q1I");
        values38.put(COLUMN_NAME, "lyec_kat1video");db.insert(TABLE_NAME, null, values38);

        //kat1-tolunaydemirci video
        ContentValues values39 = new ContentValues();values39.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1Y5vN9zgDfvo4127iEi_8ld8q6Ikfbpi0");
        values39.put(COLUMN_NAME, "kat1_tolunayvideo");db.insert(TABLE_NAME, null, values39);

        //kat1-zemin video
        ContentValues values40 = new ContentValues();values40.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1y7HdLcRmMXG9ylYJiUsjDTZpWO_uQAOn");
        values40.put(COLUMN_NAME, "kat1_zeminvideo");db.insert(TABLE_NAME, null, values40);

        //lyec-zemin video
        ContentValues values41 = new ContentValues();values41.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1KLE7EEilBttoCURdXn4LjdIEvGNtpGHP");
        values41.put(COLUMN_NAME, "lyec_zeminkatvideo");db.insert(TABLE_NAME, null, values41);

        //tolunay demirci photo
        ContentValues values42 = new ContentValues();values42.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1xNMlMffYa-baXxbalvaGzsMCRsUg9Jgh");
        values42.put(COLUMN_NAME, "tolunaydemirci");db.insert(TABLE_NAME, null, values42);

        //zemin merdiven foto
        ContentValues values43 = new ContentValues();values43.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=11xHFpi4ZnRve0gQfJn3dtO_Aeac9JSl_");
        values43.put(COLUMN_NAME, "zeminmerdivenfoto");db.insert(TABLE_NAME, null, values43);

        //kat1_lyec video
        ContentValues values44 = new ContentValues();values44.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1ge8n3fXOdjxoMFjfjc2fOPyYeEAo_Z3N");
        values44.put(COLUMN_NAME, "kat1_lyecvideo");db.insert(TABLE_NAME, null, values44);

        //kat1_bekleme video
        ContentValues values45 = new ContentValues();values45.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1gABQ5BmWvdFfU4ymtDbSdNSyC4Xxl-bM");
        values45.put(COLUMN_NAME, "kat1_beklemevideo");db.insert(TABLE_NAME, null, values45);

        //kat1_danisma video
        ContentValues values46 = new ContentValues();values46.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1KcqzSDKWsxN1NKOaa6MO34-mtJS2NKce");
        values46.put(COLUMN_NAME, "kat1_danismavideo");db.insert(TABLE_NAME, null, values46);

        //kat1_mutfak video
        ContentValues values47 = new ContentValues();values47.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1A_jUc1ToJXfgyrYvaU8BVtFFKKqFgYYy");
        values47.put(COLUMN_NAME, "kat1_mutfakvideo");db.insert(TABLE_NAME, null, values47);

        //kat1_sesli video
        ContentValues values48 = new ContentValues();values48.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=13C9KhI9wEfUTFtj6VWbUa_kQwKywNMIH");
        values48.put(COLUMN_NAME, "kat1_seslivideo");db.insert(TABLE_NAME, null, values48);

        //zemin_hoca video video
        ContentValues values49 = new ContentValues();values49.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=16TBZvFriiDdAnPgFyeEbu1Sj38U-p-wV");
        values49.put(COLUMN_NAME, "zemin_hocavideo");db.insert(TABLE_NAME, null, values49);

        //lyec_hoca video video
        ContentValues values50 = new ContentValues();values50.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1w2OsypO9b8nczgoS5-cYhrZDLeX72OvT");
        values50.put(COLUMN_NAME, "lyec_hocavideo");db.insert(TABLE_NAME, null, values50);

        //zemin_resim video
        ContentValues values51 = new ContentValues();values51.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1PQe_RdeQjtvVsYfKhs8tj36usNJ1SOCG");
        values51.put(COLUMN_NAME, "zemin_resimvideo");db.insert(TABLE_NAME, null, values51);

        //zemin_idari video
        ContentValues values52 = new ContentValues();values52.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1ma6IfO5zC2JZSiNT60b2Y1O_eN31oNa8");
        values52.put(COLUMN_NAME, "zemin_idarivideo");db.insert(TABLE_NAME, null, values52);

        //zemin_muzik video
        ContentValues values53 = new ContentValues();values53.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1caZHD7onVWNaAYgNOSgnxUJBKc7qPVYM");
        values53.put(COLUMN_NAME, "zemin_muzikvideo");db.insert(TABLE_NAME, null, values53);

        //zemin_revir video video
        ContentValues values54 = new ContentValues();values54.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1ArA4M9Rcb8M5jmcIKCK8D9bXV-N9ZLfB");
        values54.put(COLUMN_NAME, "zemin_revirvideo");db.insert(TABLE_NAME, null, values54);

        //zemin_spor video video
        ContentValues values55 = new ContentValues();values55.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1gC37l_RXQTl0vgfCwmt5bfPPL92viB4X");
        values55.put(COLUMN_NAME, "zemin_sporvideo");db.insert(TABLE_NAME, null, values55);

        //zemin_bilgisayar video video
        ContentValues values56 = new ContentValues();values56.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=18bqb06sX7Aiu1NwVwnAWqUetVsZmblBp");
        values56.put(COLUMN_NAME, "zemin_bilgisayarvideo");db.insert(TABLE_NAME, null, values56);


        //lyec_idari video video
        ContentValues values57 = new ContentValues();values57.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1kxwcEhaLSuDcvRfyznlyTuyYVQ1Bdnnu");
        values57.put(COLUMN_NAME, "lyec_idarivideo");db.insert(TABLE_NAME, null, values57);

        //lyec_danisma video video
        ContentValues values58 = new ContentValues();values58.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1oCR50vGK8GZLcZ8axJvF1EpDsy9-IAwt");
        values58.put(COLUMN_NAME, "lyec_danismavideo");db.insert(TABLE_NAME, null, values58);

        //lyec_bekleme video video
        ContentValues values59 = new ContentValues();values59.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1IO469ZyIY4_DGxUKBrDld4TMA2rKJJEW");
        values59.put(COLUMN_NAME, "lyec_beklemevideo");db.insert(TABLE_NAME, null, values59);

        //lyec_bilgisayar video video
        ContentValues values60 = new ContentValues();values60.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=18Nc8A-5G72jVTtP_Sb9Vjw69Y5N-x35V");
        values60.put(COLUMN_NAME, "lyec_bilgisayarvideo");db.insert(TABLE_NAME, null, values60);

        //lyec_asansor video video
        ContentValues values61 = new ContentValues();values61.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1eS7qT0WAK9zOBKdzWDXJWS7FpThPEYx6");
        values61.put(COLUMN_NAME, "lyec_asansorvideo");db.insert(TABLE_NAME, null, values61);

        //lyec_spor video video
        ContentValues values62 = new ContentValues();values62.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1ecyMgadlG8VkRIjm9sMsEcLFeakjm0cF");
        values62.put(COLUMN_NAME, "lyec_sporvideo");db.insert(TABLE_NAME, null, values62);

        //lyec_sesli video video
        ContentValues values63 = new ContentValues();values63.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1u0W4ew0sNXhVTo09mIuhB8SW0Bar9Kub");
        values63.put(COLUMN_NAME, "lyec_seslivideo");db.insert(TABLE_NAME, null, values63);

        //lyec_revir video video
        ContentValues values64 = new ContentValues();values64.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=18Nc8A-5G72jVTtP_Sb9Vjw69Y5N-x35V");
        values64.put(COLUMN_NAME, "lyec_revirvideo");db.insert(TABLE_NAME, null, values64);

        //lyec_mutfak video video
        ContentValues values65 = new ContentValues();values65.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1d0sPU9OaXrnJiNDgZq8YP5aTXzapxpSf");
        values65.put(COLUMN_NAME, "lyec_mutfakvideo");db.insert(TABLE_NAME, null, values65);

        //lyec_resim video video
        ContentValues values66 = new ContentValues();values66.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1uUkUEFUt-wNpf0nl6bqlqdtDXQz0QlxG");
        values66.put(COLUMN_NAME, "lyec_resimvideo");db.insert(TABLE_NAME, null, values66);

        //lyec_muzik video video
        ContentValues values67 = new ContentValues();values67.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=1k1CzxWvaYpLTDE2n2Gcfc9BpEO8ftBm1");
        values67.put(COLUMN_NAME, "lyec_muzikvideo");db.insert(TABLE_NAME, null, values67);

        //lyec_kat1WC video video
        ContentValues values68 = new ContentValues();values68.put(COLUMN_URL, "https://drive.google.com/uc?export=view&id=19HBgKH7oPtVY-VLu5ravLP1183RkwNy9");
        values68.put(COLUMN_NAME, "lyec_kat1WCvideo");db.insert(TABLE_NAME, null, values68);



        //burası gradle dosyalarına konulabilir
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Veritabanı güncellenmesi gerektiğinde çağrılır. versiyonu değiştirince çalışır
        //db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN new_column TEXT");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
