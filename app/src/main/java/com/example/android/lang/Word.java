package com.example.android.lang;

public class Word {
    private String mDefaultTranslation;
    private String mFrenchTranslation;
    private Integer mImageResource=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;
    private int mAudioResourceid;

    /* Creating constructors for the default,french translation*/
    public Word(String defaultTranslation, String FrenchTranslation,int audioresourceid){
        mDefaultTranslation=defaultTranslation;
        mFrenchTranslation=FrenchTranslation;
        mAudioResourceid=audioresourceid;

    }
    /* Creating constructors for the default,french translation,audiooresource and the imageresource*/
    public Word(String defaultTranslation, String FrenchTranslation,Integer image,int audioresourceid){
        mDefaultTranslation=defaultTranslation;
        mFrenchTranslation=FrenchTranslation;
        mImageResource=image;
        mAudioResourceid=audioresourceid;

    }
    /*Returns English translation of the word */
    public String getDefaultTranslation(){
       return mDefaultTranslation;
    }

    /*Returns french translation of the word */
    public String getFrenchTranslation() {
        return mFrenchTranslation;
    }

    //Returns ImageResource
    public Integer getImageResource(){
        return mImageResource;
    }

    //Returns an image if a listview has an image or not
    public boolean hasImage(){
        return mImageResource!=NO_IMAGE_PROVIDED;
    }
    //returns image resource id
    public int getmAudioResourceid(){
        return mAudioResourceid;
    }
}

