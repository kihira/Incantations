package kihira.incantations.incantation;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class Symbol {

	private ResourceLocation symbolTexture;
	private String identifier;
	private String translated;
	private short width = 10;
	private int uPos = 0;
	private int vPos = 0;

	public static final HashMap<String, Symbol> symbolMap = new HashMap<String, Symbol>();

	public Symbol(String identifier, ResourceLocation resourceLocation) {
		if (symbolMap.get(identifier) == null) {
			this.symbolTexture = resourceLocation;
			this.identifier = identifier;
			symbolMap.put(identifier, this);
		}
		else throw new IllegalArgumentException("The symbol identifier " + identifier + " has already been registered!");
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getTranslation() {
		return translated;
	}

	public Symbol setTranslation(String translation) {
		translated = translation;
		return this;
	}

	public ResourceLocation getTexture() {
		return symbolTexture;
	}

	public Symbol setWidth(int newWidth) {
		width = (short) newWidth;
		return this;
	}

	public short getWidth() {
		return width;
	}

	public Symbol setUV(int uPos, int vPos) {
		this.uPos = uPos;
		this.vPos = vPos;
		return this;
	}

	public int getU() {
		return uPos;
	}

	public int getV() {
		return vPos;
	}
}
