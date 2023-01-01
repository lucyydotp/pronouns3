package net.lucypoulton.pronouns.api.impl;

import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.PronounSupplier;
import net.lucypoulton.pronouns.api.impl.set.SimplePronounSet;

import java.util.LinkedHashSet;
import java.util.List;

public class PronounParser {
	private final PronounSupplier store;

	public PronounParser(final PronounSupplier store) {
		this.store = store;
	}

	public List<PronounSet> parse(String input) {
		final var split = input.split("[/ ]");
		final var out = new LinkedHashSet<PronounSet>();
		final var predefined = store.get();
		var queuePoint = 0;
		for (int i = 0; i < split.length; i++) {
			final var k = split[i];
			final var matchingSet = predefined.stream().filter(set -> set.includesPronoun(k)).findFirst();
			if (matchingSet.isPresent()) {
				queuePoint = i;
				out.add(matchingSet.get());
				continue;
			}
			if (i - queuePoint == 4){
				final var last = split[queuePoint+4];
				final var isPlural = last.endsWith(":p");
				// TODO - parse
				out.add(new SimplePronounSet(
						split[queuePoint],
						split[queuePoint+1],
						split[queuePoint+2],
						split[queuePoint+3],
						isPlural ? last.substring(0,last.length()-2) : last,
						isPlural
				));
			}
		}
		if (out.isEmpty()) throw new IllegalArgumentException("Failed to parse " + input);
		return List.copyOf(out);
	}
}
