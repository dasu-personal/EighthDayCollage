package com.dasugames.eighthdaycollage.data;



import android.media.AudioManager;
import android.media.MediaPlayer;

import com.dasugames.eighthdaycollage.resource.MusicResource;

public class MusicManager {
	
	MediaPlayer musicPlayer;
	String musicResource;

	
	public MusicManager() {
		musicPlayer = new MediaPlayer();
		
	}

	public void playMusic(String musicResource, boolean isLoop) throws Exception {
		// this should be if the same sound is already playing 
		if ((musicResource != null && musicResource.equals( this.musicResource)) && musicPlayer.isPlaying()) {
			return;
		}

		this.musicResource = musicResource;
		
		stopMusic();
		if (musicResource == null){
			return;
		}

		
		musicPlayer.setDataSource(musicResource);
		// why android why
		// does setDataSource nuke the object or create a new managed thing or something
		musicPlayer.setLooping(isLoop);
		musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		musicPlayer.prepare();
		musicPlayer.start();
	}
	
	public void playMusic() {
		
		
	
		
		if (musicResource == null){
			stopMusic();
			return;
		}

		
		try {
			musicPlayer.setDataSource(musicResource);
			musicPlayer.prepare();
		} catch (Exception e) {
			this.musicResource = null;
			stopMusic();
			return;
		}
		musicPlayer.start();
		
	}
	
	public void stopMusic(){
		if (musicPlayer.isPlaying()) {
			musicPlayer.stop();
		}
		musicPlayer.reset();
	}

}
