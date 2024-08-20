package net.frozenblock.unforked.util;

import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

// Source: ModsMod
public class IteratorCallbackList<T> implements List<T> {
	private final List<T> containers;
	private final Consumer<List<T>> reset;
	private final Runnable callback;
	boolean modified = false;
	public IteratorCallbackList(List<T> base, Consumer<List<T>> reset, Runnable callback) {
		containers = Collections.synchronizedList(base);
		this.reset = reset;
		this.callback = callback;
	}

	@Override
	public int size() {
		return containers.size();
	}

	@Override
	public boolean isEmpty() {
		return containers.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return containers.contains(o);
	}

	@NotNull
	@Override
	public Iterator<T> iterator() {
		if (!modified) {
			callback.run();
			modified = true;
		}
		reset.accept(containers);
		return containers.iterator();
	}

	@NotNull
	@Override
	public Object[] toArray() {
		return containers.toArray();
	}

	@NotNull
	@Override
	public <T> T[] toArray(@NotNull T[] ts) {
		return containers.toArray(ts);
	}

	@Override
	public boolean add(T modContainer) {
		return containers.add(modContainer);
	}

	@Override
	public boolean remove(Object o) {
		return containers.remove(o);
	}

	@Override
	public boolean containsAll(@NotNull Collection<?> collection) {
		return containers.containsAll(collection);
	}

	@Override
	public boolean addAll(@NotNull Collection<? extends T> collection) {
		return containers.addAll(collection);
	}

	@Override
	public boolean addAll(int i, @NotNull Collection<? extends T> collection) {
		return containers.addAll(i, collection);
	}

	@Override
	public boolean removeAll(@NotNull Collection<?> collection) {
		return containers.removeAll(collection);
	}

	@Override
	public boolean retainAll(@NotNull Collection<?> collection) {
		return containers.retainAll(collection);
	}

	@Override
	public void clear() {
		containers.clear();
	}

	@Override
	public T get(int i) {
		return containers.get(i);
	}

	@Override
	public T set(int i, T modContainer) {
		return containers.set(i, modContainer);
	}

	@Override
	public void add(int i, T modContainer) {
		containers.add(i, modContainer);
	}

	@Override
	public T remove(int i) {
		return containers.remove(i);
	}

	@Override
	public int indexOf(Object o) {
		return containers.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return containers.lastIndexOf(o);
	}

	@NotNull
	@Override
	public ListIterator<T> listIterator() {
		return containers.listIterator();
	}

	@NotNull
	@Override
	public ListIterator<T> listIterator(int i) {
		return containers.listIterator(i);
	}

	@NotNull
	@Override
	public List<T> subList(int i, int i1) {
		return containers.subList(i, i1);
	}
}
