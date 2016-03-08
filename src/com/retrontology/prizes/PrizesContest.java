package com.retrontology.prizes;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class PrizesContest {
	
	/* Class Variables */
	private Prizes plugin;
	private File file;
	private FileConfiguration config;
	private String contest;

	public PrizesContest(Prizes plugin, String contest) {
		// Init variables
		this.plugin = plugin;
		this.contest = contest;
	}

}
