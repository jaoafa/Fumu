package com.jaoafa.Fumu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class Task_AutoEkusasFetch extends TimerTask {
	@Override
	public void run() {
		System.out.println("Task_AutoEkusasFetch()");
		JDA jda = Main.getJDA();
		User user = jda.getUserById(Main.getWatchUserID());
		if (user == null) {
			System.out.println("Task_AutoEkusasFetch() | user not found.");
			return;
		}
		User me = jda.getSelfUser();

		changeStatus(jda, null);
		changeActivity(jda, null);
		changeAvatar(jda, user);
		changeName(jda, user, me);
		changeNickName(jda, user, me);
	}

	void changeStatus(JDA jda, OnlineStatus status) {
		if (jda == null) {
			jda = Main.getJDA();
		}
		if (status == null) {
			Member member = jda.getGuildById(597378876556967936L).getMember(jda.getUserById(Main.getWatchUserID()));
			status = member.getOnlineStatus();
		}
		jda.getPresence().setStatus(status);
	}

	void changeActivity(JDA jda, Activity activity) {
		if (jda == null) {
			jda = Main.getJDA();
		}
		if (activity == null) {
			Member member = jda.getGuildById(597378876556967936L).getMemberById(Main.getWatchUserID());
			if (member.getActivities().isEmpty()) {
				activity = null;
			} else {
				for (int i = 0; i < member.getActivities().size(); i++) {
					System.out.println("changeActivity() | " + i + ": " + member.getActivities().get(i).getName());
					if (member.getActivities().get(i).getName().equals("Custom Status")) {
						continue;
					}
					activity = member.getActivities().get(i);
				}
			}
		}
		jda.getPresence().setActivity(activity);
	}

	void changeAvatar(JDA jda, User user) {
		if (jda == null) {
			jda = Main.getJDA();
		}
		if (user == null) {
			user = jda.getUserById(Main.getWatchUserID());
			if (user == null) {
				System.out.println("changeAvatar() | user not found.");
				return;
			}
		}
		String avatarUrl = user.getAvatarUrl();
		System.out.println("changeAvatar() | getAvatarUrl : " + avatarUrl);

		if (avatarUrl != null) {
			try {
				URL url = new URL(avatarUrl);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("User-Agent", "Fumu (https://jaoafa.com, v0.0.1)");
				conn.connect();

				int status = conn.getResponseCode();
				System.out.println("changeAvatar() | getResponseCode : " + status);
				if (status == HttpsURLConnection.HTTP_OK) {
					Icon icon = Icon.from(conn.getInputStream());

					jda.getSelfUser().getManager().setAvatar(icon).queue(v -> {
						System.out.println("changeAvatar() | setAvatar(icon) : Successful!");
					}, failure -> {
						System.out
								.println("changeAvatar() | setAvatar(icon) : Failed / " + failure.getMessage());
					});
				} else {
					System.out.println("changeAvatar() | else {");
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

					StringBuilder output = new StringBuilder();
					String line;

					while ((line = reader.readLine()) != null) {
						output.append(line);
					}
					System.out.println(output.toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			jda.getSelfUser().getManager().setAvatar(null).queue(v -> {
				System.out.println("changeAvatar() | setAvatar(null) : Successful!");
			}, failure -> {
				System.out.println("changeAvatar() | setAvatar(null) : Failed / " + failure.getMessage());
			});
		}
	}

	void changeName(JDA jda, User user, User me) {
		if (jda == null) {
			jda = Main.getJDA();
		}
		if (user == null) {
			user = jda.getUserById(Main.getWatchUserID());
			if (user == null) {
				System.out.println("changeName() | user not found.");
				return;
			}
		}
		if (me == null) {
			me = jda.getSelfUser();
		}
		String username = user.getName();
		String usernameMe = me.getName();
		if (!username.equals(usernameMe)) {
			System.out.println("changeName() | username : " + username + " != " + usernameMe);
			jda.getSelfUser().getManager().setName(username).queue(v -> {
				System.out.println("changeName() | setName() : Successful!");
			}, failure -> {
				System.out.println("changeName() | setName() : Failed / " + failure.getMessage());
			});
		} else {
			System.out.println("changeName() | username : " + username + " == " + usernameMe);
		}
	}

	void changeNickName(JDA jda, User user, User me) {
		if (jda == null) {
			jda = Main.getJDA();
		}
		if (user == null) {
			user = jda.getUserById(Main.getWatchUserID());
			if (user == null) {
				System.out.println("changeAvatar() | user not found.");
				return;
			}
		}
		if (me == null) {
			me = jda.getSelfUser();
		}
		Member member = jda.getGuildById(597378876556967936L).getMember(user);
		Member memberMe = jda.getGuildById(597378876556967936L).getMember(me);
		String nickname = member.getNickname();
		String nicknameMe = memberMe.getNickname();
		if (!nickname.equals(nicknameMe)) {
			System.out.println("changeNickName() | nickname : " + nickname + " != " + nicknameMe);
			jda.getGuildById(597378876556967936L).modifyNickname(memberMe, nickname).queue(v -> {
				System.out.println("changeNickName() | modifyNickname() : Successful!");
			}, failure -> {
				System.out.println("changeNickName() | modifyNickname() : Failed / " + failure.getMessage());
			});
		} else {
			System.out.println("changeNickName() | nickname : " + nickname + " == " + nicknameMe);
		}
	}
}
