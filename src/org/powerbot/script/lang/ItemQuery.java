package org.powerbot.script.lang;

import org.powerbot.script.methods.ClientFactory;

public abstract class ItemQuery<K extends Identifiable> extends AbstractQuery<ItemQuery<K>, K>
		implements Identifiable.Query<ItemQuery<K>> {
	public ItemQuery(final ClientFactory factory) {
		super(factory);
	}

	@Override
	protected ItemQuery<K> getThis() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemQuery<K> id(int... ids) {
		return select(new Identifiable.Matcher(ids));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ItemQuery<K> id(Identifiable... identifiables) {
		return select(new Identifiable.Matcher(identifiables));
	}
}
