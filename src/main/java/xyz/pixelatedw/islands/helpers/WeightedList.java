package xyz.pixelatedw.islands.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.gen.INoiseRandom;

public class WeightedList<T extends Object>
{
	private class Entry
	{
		double weight;
		T object;
	}

	private List<Entry> entries = new ArrayList<>();
	private double totalWeight;
	
	public void addEntry(T object, double weight)
	{
		this.totalWeight += weight;
		Entry entry = new Entry();
		entry.object = object;
		entry.weight = this.totalWeight;
		this.entries.add(entry);
	}

	public T getRandom(INoiseRandom rand)
	{
		double i = rand.random(this.entries.size()) / (double)this.entries.size();
		double r = i * this.totalWeight;
		
		for (Entry entry : this.entries)
		{
			if (entry.weight >= r)
			{
				return entry.object;
			}
		}

		return null;
	}
	
	public int size()
	{
		return this.entries.size();
	}
	
	public double getTotalWeight()
	{
		return this.totalWeight;
	}
}
