package com.incture.mobility.imagecachingpoc.data;

import android.content.Context;
import android.util.Log;

import com.incture.mobility.imagecachingpoc.model.CardItem;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by satiswardash on 12/01/18.
 */

public class SampleDataProvider {

    public static void loadSampleDataToRealm(Context mContext) {
        Realm.init(mContext);
        Realm realm = Realm.getDefaultInstance();

        RealmList<String> images = new RealmList<>();
        images.add("https://firebasestorage.googleapis.com/v0/b/upsc-c98c0.appspot.com/o/cityscapes_old_Czech_history_flags_town_Prague_rivers_1920x1271.jpg?alt=media&token=8e8fd8af-3dfb-40f2-94b2-a4acc6ed000c");
        images.add("https://firebasestorage.googleapis.com/v0/b/upsc-c98c0.appspot.com/o/J8Luoe4%20-%20Imgur.jpg?alt=media&token=97462d1b-9af0-45c0-81a2-3ec0a96660b5");
        images.add("https://firebasestorage.googleapis.com/v0/b/upsc-c98c0.appspot.com/o/Maler_der_Grabkammer_des_Sennudem_001.jpg?alt=media&token=e81d3b60-460c-47e0-9a6a-b025b75b62fa");
        images.add("https://firebasestorage.googleapis.com/v0/b/upsc-c98c0.appspot.com/o/monument-valley-5k.jpg?alt=media&token=fdb65bef-5fc2-4d57-b795-c06f0c7a5278");
        images.add("https://firebasestorage.googleapis.com/v0/b/upsc-c98c0.appspot.com/o/A_mosaic_LAW_by_Frederick_Dielman%2C_1847-1935.JPG?alt=media&token=20666ee5-d7a0-41e2-b0e9-6ce5b0caf89c");

        for (int i=1; i<=2; i++) {
            CardItem item = new CardItem();
            String uid = UUID.randomUUID().toString();
            item.setId(uid);
            item.setTitle("Lorem ipsum Title "+i);
            item.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus pellentesque egestas consectetur. Praesent ut arcu non libero luctus congue quis at enim. Duis posuere condimentum neque, ac bibendum nibh ullamcorper ut. Donec commodo enim bibendum, ultricies tellus nec, dapibus eros. Ut mattis, lorem sed accumsan blandit, ligula ipsum ornare nunc, malesuada accumsan ipsum elit at diam. Sed sed massa nec velit consectetur rutrum. Sed ut tincidunt ligula. Vestibulum ornare nulla ac nulla pretium sollicitudin. Praesent convallis nec erat ac blandit. Quisque ac magna egestas, convallis risus vitae, mollis enim. Maecenas rutrum nulla id leo dignissim efficitur. Morbi tortor odio, sodales et mattis id, faucibus id dui. In efficitur turpis ligula, tincidunt feugiat risus suscipit vitae. Mauris varius, diam ut euismod dignissim, elit lacus pellentesque ante, id varius odio ex a libero. Proin magna nibh, tincidunt at dapibus eu, fermentum in arcu.\n" +
                    "\n" +
                    "Aenean ultricies eros ante, bibendum cursus neque lacinia non. Vestibulum egestas tincidunt tortor. Ut lectus purus, dapibus sed rutrum vitae, sollicitudin sit amet orci. Morbi massa dolor, cursus in diam imperdiet, volutpat rhoncus arcu. In hac habitasse platea dictumst. Etiam eu arcu non arcu condimentum porttitor sed vitae ipsum. Integer vehicula molestie egestas. Sed viverra sagittis purus, ut varius mauris dictum vitae. Aliquam pretium maximus efficitur. Cras semper nunc vitae tellus consequat, id lobortis felis ultrices. Mauris aliquet purus magna, ut consectetur nulla suscipit imperdiet. Sed orci neque, feugiat sit amet velit quis, efficitur pretium nulla. In hac habitasse platea dictumst. Quisque tincidunt varius dui vitae lobortis. Proin ex enim, blandit vitae rhoncus venenatis, viverra vel diam. Ut maximus, leo eu dignissim consectetur, lorem diam consequat purus, quis malesuada sem ipsum sed libero."+i);
            item.setImageUris(images);

            realm.beginTransaction();
            realm.copyToRealm(item);
            realm.commitTransaction();
            Log.i(SampleDataProvider.class.toString(), "loadSampleDataToRealm: UID saved."+uid);
        }
    }
}
