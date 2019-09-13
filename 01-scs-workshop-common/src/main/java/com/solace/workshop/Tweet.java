/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.solace.workshop;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tweet {
	private Timestamp timestamp;
	private String username;
	private String text;
	private UUID uuid;
	private ArrayList<String> hashtags;
	private ArrayList<String> userMentions;

	private Tweet() {
		timestamp = new Timestamp(System.currentTimeMillis());
		uuid = UUID.randomUUID();
	}

	public Tweet(String username, String text) {
		this();
		setText(text);
		setUsername(username);
		setHashtags(extractHashtags(text));
		setUserMentions(extractMentions(text));
	}

	// Method to extract user mentions (which start with @) from a text field
	private ArrayList<String> extractMentions(String text) {
		ArrayList<String> mentions = new ArrayList<String>();

		Pattern pattern = Pattern.compile("@\\w+");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			mentions.add(matcher.group());
		}
		return mentions;
	}

	// Method to extract hashtags (which start with #) from a text field
	private ArrayList<String> extractHashtags(String text) {
		ArrayList<String> hashtags = new ArrayList<String>();

		Pattern pattern = Pattern.compile("#\\w+");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			hashtags.add(matcher.group());
		}
		return hashtags;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void updateTimestamp() {
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	public UUID getUuid() {
		return uuid;
	}

	public void updateUuid() {
		this.uuid = UUID.randomUUID();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(ArrayList<String> hashtags) {
		this.hashtags = hashtags;
	}

	public ArrayList<String> getUserMentions() {
		return userMentions;
	}

	public void setUserMentions(ArrayList<String> userMentions) {
		this.userMentions = userMentions;
	}

	@Override
	public String toString() {
		return "Tweet [timestamp=" + timestamp + ", username=" + username + ", text=" + text + ", hashtags=" + hashtags
				+ ", userMentions=" + userMentions + ", uuid=" + uuid + "]";
	}

}