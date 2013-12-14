package incantations.incantation;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class Symbol {

	private ResourceLocation symbolTexture;
	private String identifier;

	public static final HashMap<String, Symbol> symbolMap = new HashMap<String, Symbol>();

	public Symbol(String identifier, ResourceLocation resourceLocation) {
		if (symbolMap.get(identifier) == null) {
			symbolTexture = resourceLocation;
			symbolMap.put(identifier, this);
		}
		else throw new IllegalArgumentException("The symbol identifier " + identifier + " has already been registered!");
	}

	public String getIdentifier() {
		return identifier;
	}
}
