package com.dasugames.eighthdaycollage.resource;

import java.io.File;
import java.io.Serializable;

import android.net.Uri;

/**
 * This will be used for both Looping background ambiance
 * and one time point of interests. But I am not going to worry about implementation yet.
 * @author darren.sue
 *
 */
public class MusicResource extends ManagableResource  implements Serializable {
	private static final long serialVersionUID = 0L;
	 

	private String uri;
	// this is eventually going to be more sophisticated
	public MusicResource(String uri){
		this.uri = uri;
	}
	
	
	protected void clearData(){
		// TODO this implementation should clear whereever the music file is saved internally
		// TODO what to do if uri no longer exists
		(new File(uri)).delete();
	}

	// this is going to be the important return
	public String getUri() {
		return uri;
	}
}
