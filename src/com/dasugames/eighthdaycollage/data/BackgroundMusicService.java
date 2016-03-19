package com.dasugames.eighthdaycollage.data;

import java.io.IOException;

import com.dasugames.eighthdaycollage.resource.MusicResource;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class BackgroundMusicService extends Service implements MediaPlayer.OnErrorListener {

	MediaPlayer musicPlayer;
	MusicResource musicResource;
	private final IBinder backgroundMusicBinder = new BackgroundMusicPlayerBinder();

	public class BackgroundMusicPlayerBinder extends Binder {
		BackgroundMusicService getService() {
			return BackgroundMusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return backgroundMusicBinder;
	}

	public class BackgroundMusicServiceBinder extends Binder {
		public BackgroundMusicService getService() {
			return BackgroundMusicService.this;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		
		musicPlayer = new MediaPlayer();
	    musicPlayer.setLooping(true); // Set looping
	    musicPlayer.setVolume(100,100);


	}

	public void playMusic(MusicResource musicResource) throws Exception {
		if (this.musicResource == musicResource) {
			return;
		}
		this.musicResource = musicResource;
		stopMusic();
		// TODO is it ever possible that musicResource is null
		musicPlayer.setDataSource(musicResource.getUri());
		musicPlayer.start();
		
	}
	
	public void stopMusic(){
		if (musicPlayer.isPlaying()) {
			musicPlayer.stop();
			musicPlayer.release();
		}
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		musicPlayer.start(); 
		return 1;
	}

	public void onStart(Intent intent, int startId) {
		// TO DO
	}

	public IBinder onUnBind(Intent arg0) {
		// TO DO Auto-generated method
		return null;
	}

	public void onStop() {

	}

	public void onPause() {

	}

	@Override
	public void onDestroy() {
		stopMusic();
	}

	@Override
	public void onLowMemory() {

	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

}
