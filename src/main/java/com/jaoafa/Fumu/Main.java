package com.jaoafa.Fumu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.AnnotatedEventManager;

public class Main {
	private static JDA jda = null;
	private static long WatchUserID = 189377054955798528L;

	public static void main(String[] args) {
		File f = new File("conf.properties");
		Properties props;
		try {
			InputStream is = new FileInputStream(f);

			// プロパティファイルを読み込む
			props = new Properties();
			props.load(is);
		} catch (FileNotFoundException e) {
			// ファイル生成
			props = new Properties();
			props.setProperty("token", "PLEASETOKEN");
			props.setProperty("WatchUserID", "PLEASE");
			try {
				props.store(new FileOutputStream("conf.properties"), "Comments");
				System.out.println("Please Config Token!");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
				return;
			}
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// キーを指定して値を取得する
		String token = props.getProperty("token");
		if (token.equalsIgnoreCase("PLEASETOKEN")) {
			System.out.println("Please Token!");
			return;
		}
		String WatchUserIDStr = props.getProperty("WatchUserID");
		if (WatchUserIDStr == null || WatchUserIDStr.equalsIgnoreCase("PLEASE")) {
			WatchUserID = 189377054955798528L;
		} else {
			WatchUserID = Long.parseLong(WatchUserIDStr);
		}

		// 分けてイベント自動登録できるように？
		// 全部JDA移行
		try {
			JDABuilder jdabuilder = new JDABuilder(AccountType.BOT)
					.setAutoReconnect(true)
					.setBulkDeleteSplittingEnabled(false)
					.setToken(token)
					.setContextEnabled(false)
					.setEventManager(new AnnotatedEventManager());

			jdabuilder.addEventListeners(new Event_MessageReceived());
			jdabuilder.addEventListeners(new Event_EkusasWatcher());

			jda = jdabuilder.build().awaitReady();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Runtime.getRuntime().addShutdownHook(
				new Thread(
						() -> {
							System.out.println("Exit");
						}));

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Task_AutoEkusasFetch(), 10000, 3600000); // 1時間
	}

	public static void setJDA(JDA jda) {
		Main.jda = jda;
	}

	public static JDA getJDA() {
		return jda;
	}

	public static long getWatchUserID() {
		return WatchUserID;
	}
}
