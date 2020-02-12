package com.jaoafa.Fumu;

import java.util.Random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

public class Event_MessageReceived {
	@SubscribeEvent
	public void onMessageReceivedEvent(MessageReceivedEvent event) {
		if (!event.isFromGuild()) {
			return; // FromGuild
		}
		if (event.getGuild().getIdLong() != 597378876556967936L) {
			return; // jMS Gamers Club
		}
		if (event.getChannel().getIdLong() != 597419057251090443L
				&& event.getChannel().getIdLong() != 597420715129569310L) {
			return; // general | freechat
		}
		Random random = new Random();
		int num = random.nextInt(100);
		System.out.println("onMessageReceivedEvent() | num = " + num);
		if (num == 2) {
			// 1%の確率 (0-99 -> 2)
			event.getChannel().sendMessage("なるほど").queue();
			return;
		}
		if (num >= 2) {
			return; // 2%の確率 (0-99 -> 0, 1)
		}

		event.getChannel().sendMessage("ふむ").queue();
	}
}
