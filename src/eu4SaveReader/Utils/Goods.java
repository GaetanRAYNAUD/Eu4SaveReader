package eu4SaveReader.Utils;

import java.util.HashMap;

public final class Goods {
	
	private Goods() {}
	
	public static final HashMap<String, String> goodsNames = new HashMap<String, String>();
	static {
		goodsNames.put("chinaware", "Chinaware");
		goodsNames.put("cloth", "Cloth");
		goodsNames.put("coffee", "Coffee");
		goodsNames.put("copper", "Copper");
		goodsNames.put("cotton", "Cotton");
		goodsNames.put("fish", "Fish");
		goodsNames.put("fur", "Fur");
		goodsNames.put("gold", "Gold");
		goodsNames.put("grain", "Grain");
		goodsNames.put("iron", "Iron");
		goodsNames.put("ivory", "Ivory");
		goodsNames.put("naval_supplies", "Naval Supplies");
		goodsNames.put("salt", "Salt");
		goodsNames.put("slaves", "Slaves");
		goodsNames.put("spices", "Spices");
		goodsNames.put("sugar", "Sugar");
		goodsNames.put("tea", "Tea");
		goodsNames.put("tobacco", "Tobacco");
		goodsNames.put("wine", "Wine");
		goodsNames.put("wool", "Wool");
		goodsNames.put("cocoa", "Cocoa");
		goodsNames.put("tropical_wood", "Tropical Wood");
		goodsNames.put("dyes", "Dyes");
		goodsNames.put("silk", "Silk");
		goodsNames.put("incense", "Incense");
		goodsNames.put("livestock", "Livestock");
		goodsNames.put("glass", "Glass");
		goodsNames.put("paper", "Paper");
		goodsNames.put("gems", "Gems");
		goodsNames.put("coal", "Coal");
	}
	
	public static final HashMap<Integer, String> goodsId = new HashMap<Integer, String>();
	static {
		goodsId.put(1, "grain");
		goodsId.put(2, "wine");
		goodsId.put(3, "wool");
		goodsId.put(4, "cloth");
		goodsId.put(5, "fish");
		goodsId.put(6, "fur");
		goodsId.put(7, "salt");
		goodsId.put(8, "naval_supplies");
		goodsId.put(9, "copper");
		goodsId.put(10, "gold");
		goodsId.put(11, "iron");
		goodsId.put(12, "slaves");
		goodsId.put(13, "ivory");
		goodsId.put(14, "tea");
		goodsId.put(15, "chinaware");
		goodsId.put(16, "spices");
		goodsId.put(17, "coffee");
		goodsId.put(18, "cotton");
		goodsId.put(19, "sugar");
		goodsId.put(20, "tobacco");
		goodsId.put(21, "cocoa");
		goodsId.put(22, "silk");
		goodsId.put(23, "dyes");
		goodsId.put(24, "tropical_wood");
		goodsId.put(25, "livestock");
		goodsId.put(26, "incense");
		goodsId.put(27, "glass");
		goodsId.put(28, "paper");
		goodsId.put(29, "gems");
		goodsId.put(30, "coal");
		goodsId.put(31, "unknown");
	}
	
	public static final String getGoodFromId(int id) {
		return goodsNames.get(goodsId.get(id));
	}
}
