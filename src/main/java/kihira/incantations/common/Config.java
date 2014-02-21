package kihira.incantations.common;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

	private Configuration config;

	public int scrollItemID;
	public int writingDeskID;
	public int researchNotesID;
	public int quillItemID;
	public int inkVialItemID;

	public Config(File configFile) {
	 	config = new Configuration(configFile);
		loadGeneral();
		config.save();
	}

	private void loadGeneral() { }
}
