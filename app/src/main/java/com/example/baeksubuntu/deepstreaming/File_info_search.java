package com.example.baeksubuntu.deepstreaming;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by 안탄 on 2018-09-23.
 */

public class File_info_search {

    String[] data_projection = { MediaStore.Video.Media.DATA };
    String[] display_projection = { MediaStore.Video.Media.DISPLAY_NAME };
    String[] id_projection =  { MediaStore.Video.VideoColumns._ID };

    private Cursor cursor;
    private int column_index;

    public File_info_search(){

    }

    public String get_Info(Uri file_uri, int search_index, Activity getActivity){

        switch (search_index){
            case 0:
                cursor = getActivity.managedQuery(file_uri, data_projection, null, null, null);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                break;

            case 1:
                cursor = getActivity.managedQuery(file_uri, display_projection, null, null, null);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME);
                break;

            case 2:
                cursor = getActivity.managedQuery(file_uri, id_projection, null, null, null);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID);
                break;

            default:
                cursor = getActivity.managedQuery(file_uri, display_projection, null, null, null);
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME);
                break;
        }

        cursor.moveToFirst();
        return cursor.getString(column_index);

    }
}
