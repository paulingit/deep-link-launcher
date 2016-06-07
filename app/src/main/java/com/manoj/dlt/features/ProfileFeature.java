package com.manoj.dlt.features;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manoj.dlt.Constants;
import com.manoj.dlt.interfaces.IProfileFeature;

import java.util.UUID;

public class ProfileFeature implements IProfileFeature
{
    private static ProfileFeature _instance;
    private FileSystem _fileSystem;
    private String _userId;

    private ProfileFeature(Context context)
    {
        _fileSystem = new FileSystem(context, Constants.GLOBAL_PREF_KEY);
        _userId = _fileSystem.read(Constants.USER_ID_KEY);
        if(_userId == null)
        {
            _userId = generateUserId();
            _fileSystem.write(Constants.USER_ID_KEY, _userId);
        }
    }

    public ProfileFeature getInstance(Context context)
    {
        if(_instance == null)
        {
            _instance = new ProfileFeature(context);
        }
        return _instance;
    }

    @Override
    public String getUserId()
    {
        return _userId;
    }

    @Override
    public DatabaseReference getCurrentUserFirebaseBaseRef()
    {
        DatabaseReference baseRef = FirebaseDatabase.getInstance().getReference(Constants.getFirebaseUserRef());
        baseRef.child("users").child(_userId);
    }

    private String generateUserId()
    {
        //TODO: better implementation
        String rand = UUID.randomUUID().toString();
        return rand.substring(0,4);
    }
}
