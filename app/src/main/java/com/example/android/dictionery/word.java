package com.example.android.dictionery;

public class word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImage=no_image;
    private static final int no_image=-500;
    private int mAudioResourseId;
    public word(String defaultTranslation,String miwoktransltion,int Image,int AudioResourseId)
    {
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwoktransltion;
        mImage=Image;
        mAudioResourseId=AudioResourseId;
    }
     public word(String defaultTranslation,String miwoktransltion,int AudioResourseId)
     {
         mDefaultTranslation=defaultTranslation;
         mMiwokTranslation=miwoktransltion;
         mAudioResourseId=AudioResourseId;
     }

     public String getmDefaultTranslation()
     {
         return mDefaultTranslation;
     }
     public String getmMiwokTranslation()
     {
         return mMiwokTranslation;
     }
     public int getmImageResourceId(){return mImage;}

     public int getmAudioResourseId()
     {
         return mAudioResourseId;
     }

    @Override
    public String toString() {
        return "word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImage=" + mImage +
                ", mAudioResourseId=" + mAudioResourseId +
                '}';
    }

    public boolean hasImage()
    {
        if(mImage==no_image)
        {
            return false;
        }
        else
            return true;
    }
}
