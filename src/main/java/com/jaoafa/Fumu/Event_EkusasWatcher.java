package com.jaoafa.Fumu;

import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.UserActivityEndEvent;
import net.dv8tion.jda.api.events.user.UserActivityStartEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateAvatarEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class Event_EkusasWatcher {
	@SubscribeEvent
	public void onUserUpdateAvatarEvent(UserUpdateAvatarEvent event) {
		if (event.getUser().getIdLong() != Main.getWatchUserID()) {
			return;
		}
		System.out.println("onUserUpdateAvatarEvent()");
		new Task_AutoEkusasFetch().changeAvatar(null, null);
	}

	@SubscribeEvent
	public void onUserUpdateNameEvent(UserUpdateNameEvent event) {
		if (event.getUser().getIdLong() != Main.getWatchUserID()) {
			return;
		}
		System.out.println("onUserUpdateNameEvent()");
		new Task_AutoEkusasFetch().changeName(null, null, null);
	}

	@SubscribeEvent
	public void onGuildMemberUpdateNicknameEvent(GuildMemberUpdateNicknameEvent event) {
		if (event.getUser().getIdLong() != Main.getWatchUserID()) {
			return;
		}
		System.out.println("onGuildMemberUpdateNicknameEvent()");
		new Task_AutoEkusasFetch().changeNickName(null, null, null);
	}

	@SubscribeEvent
	public void onUserUpdateOnlineStatusEvent(UserUpdateOnlineStatusEvent event) {
		if (event.getUser().getIdLong() != Main.getWatchUserID()) {
			return;
		}
		System.out.println(
				"UserUpdateOnlineStatusEvent() | " + event.getOldValue().name() + " -> " + event.getNewValue().name());
		new Task_AutoEkusasFetch().changeStatus(null, event.getNewValue());
	}

	@SubscribeEvent
	public void onUserActivityStartEvent(UserActivityStartEvent event) {
		if (event.getUser().getIdLong() != Main.getWatchUserID()) {
			return;
		}
		System.out.println(
				"UserActivityStartEvent() | " + event.getNewActivity().getName());
		new Task_AutoEkusasFetch().changeActivity(null, event.getNewActivity());
	}

	@SubscribeEvent
	public void onUserActivityEndEvent(UserActivityEndEvent event) {
		if (event.getUser().getIdLong() != Main.getWatchUserID()) {
			return;
		}
		System.out.println(
				"UserActivityEndEvent() | " + event.getOldActivity().getName());
		new Task_AutoEkusasFetch().changeActivity(null, null);
	}
}
