package com.materialviewinc.playband.data;

import android.provider.BaseColumns;

public class PlaybandContract {

    public class AlbumEntry implements BaseColumns {

        public static final String TABLE_NAME="album";

        public static final String ID=BaseColumns._ID;
        public static final String TITLE="title";
        public static final String SUBTITLE="subtitle";
        public static final String IMAGE="image";


    }

    public class MusicEntry implements BaseColumns {

        public static final String TABLE_NAME="music";

        public static final String ID=BaseColumns._ID;
        public static final String TITLE="title";
        public static final String IMAGE="res";
        public static final String ALBUM_ID="albumid";




    }

}
