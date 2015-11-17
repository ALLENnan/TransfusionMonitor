package com.nan.Server;

import java.io.FileInputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("restriction")
public class SoundThread extends Thread {
	private String ipStr;
	FileInputStream sound;
	AudioStream as;
	boolean sign;

	public SoundThread(String ipStr, boolean sign) {
		this.ipStr = ipStr;
		this.sign =sign;
	}

	@Override
	public void run() {

		try {
			sound = new FileInputStream("./music/sound.au");
			as = new AudioStream(sound);
			AudioPlayer.player.start(as);
			Thread.sleep(1200);
			sound = new FileInputStream("./music/sound1.au");
			as = new AudioStream(sound);
			AudioPlayer.player.start(as);
			Thread.sleep(1000);
			for (int i = 0; i < ipStr.length(); i++) {
				char ipchar = ipStr.charAt(i) ;
				if (ipchar<48||ipchar>57) {
					continue;
				}
				String musicPath = "./music/" + ipchar + ".WAV";
				sound = new FileInputStream(musicPath);
				as = new AudioStream(sound);
				AudioPlayer.player.start(as);
				Thread.sleep(700);
			}
			
			if (sign) {
				sound = new FileInputStream("./music/sound3.au");
				as = new AudioStream(sound);
				AudioPlayer.player.start(as);
			} else {
				sound = new FileInputStream("./music/sound2.au");
				as = new AudioStream(sound);
				AudioPlayer.player.start(as);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
