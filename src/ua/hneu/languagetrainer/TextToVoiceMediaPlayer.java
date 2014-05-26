package ua.hneu.languagetrainer;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

public class TextToVoiceMediaPlayer {
	MediaPlayer mediaPlayer;

	public TextToVoiceMediaPlayer() {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}

	public void play(String s) {
		String url = "http://translate.google.com/translate_tts?ie=UTF-8&tl=ja&q="
				+ s;
		mediaPlayer.reset();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.prepare();
			mediaPlayer.start();
			mediaPlayer.setDataSource(url);
		} catch (IllegalArgumentException e) {
			mediaPlayer.reset();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			mediaPlayer.reset();
			Log.e("Text to voice media player", "Internet connection is lmited");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		mediaPlayer.stop();
	}

}
