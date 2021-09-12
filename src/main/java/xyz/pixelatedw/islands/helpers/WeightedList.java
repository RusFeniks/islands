package xyz.pixelatedw.islands.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.gen.INoiseRandom;

public class WeightedList<T>
{
	private class Entry
	{
		double weight;
		T object;
	}

	private final List<Entry> entries = new ArrayList<>();
	private int maxWeight = 1;
	
	public void addEntry(T object, double weight)
	{
		if (weight < 1) {
			return;
		}

		Entry entry = new Entry();
		entry.object = object;
		entry.weight = weight;
		this.entries.add(entry);

		if (weight > maxWeight)
			maxWeight = (int)weight;
	}

	public T getRandom(INoiseRandom rand)
	{
		int weight = rand.random(maxWeight);
		int size = this.entries.size();
		int index = rand.random(size);
		while (true) {
			Entry entry = this.entries.get(index);
			if (entry.weight > weight)
				return entry.object;
			index = index >= size ? index+1 : 0;
		}
	}
	
	public int size()
	{
		return this.entries.size();
	}
}
