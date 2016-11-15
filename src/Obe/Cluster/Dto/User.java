package Obe.Cluster.Dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

	private String name = "";//唯一标示???
	private int flag = 0;//0->free 1->dating
	private List<User> perfer = new ArrayList<User>();
	
	//for Programmer
	private List<Map.Entry<String,Integer>> tags;
	private List<String> feature;
	private String nickname = "";
	private String text = "";//user describtion
	
//	//for lifespec
//	private String text = "";//user tags --> lifespec
	private List<Map.Entry<String,Integer>> wordsCount;//tags counts -->lifespec
	private List<String> list;
	private List<UserProb> up = new ArrayList<UserProb>();

	//for SMEX
	private int perferIndex = 0;//SMEX中记录perfer的下标
	private List<User> acquaintance = new ArrayList<User>();//SMEX中记录girls认识的boys
	private int promoted = 0;//0->第一轮告白未完成 1->第一轮告白机会用尽，进行第二轮机会
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public List<User> getPerfer() {
		return perfer;
	}
	public void setPerfer(List<User> perfer) {
		this.perfer = perfer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPerferIndex() {
		return perferIndex;
	}
	public void setPerferIndex(int perferIndex) {
		this.perferIndex = perferIndex;
	}
	public List<User> getAcquaintance() {
		return acquaintance;
	}
	public void setAcquaintance(List<User> acquaintance) {
		this.acquaintance = acquaintance;
	}
	public int getPromoted() {
		return promoted;
	}
	public void setPromoted(int promoted) {
		this.promoted = promoted;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public List<String> getFeature() {
		return feature;
	}
	public void setFeature(List<String> feature) {
		this.feature = feature;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<Map.Entry<String,Integer>> getTags() {
		return tags;
	}
	public void setTags(List<Map.Entry<String,Integer>> tags) {
		this.tags = tags;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public List<Map.Entry<String,Integer>> getWordsCount() {
		return wordsCount;
	}
	public void setWordsCount(List<Map.Entry<String,Integer>> wordsCount) {
		this.wordsCount = wordsCount;
	}
	public List<UserProb> getUp() {
		return up;
	}
	public void setUp(List<UserProb> up) {
		this.up = up;
	}


}
