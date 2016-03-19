package com.dasugames.eighthdaycollage.resource;

import java.io.FileOutputStream;
import java.io.InputStream;

import com.dasugames.eighthdaycollage.R;

import android.util.Config;

public class DefaultMusicResource extends MusicResource {

	public DefaultMusicResource() {

		super("android.resource://"+"com.example.eighthdaycollage/music_test_fun.m4a");
	}

	@Override
	protected void clearData(){
		// nothing to do here given that this is a static resource
	}
	
}
