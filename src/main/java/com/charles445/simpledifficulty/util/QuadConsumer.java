package com.charles445.simpledifficulty.util;

@FunctionalInterface
public interface QuadConsumer<A, B, C, D>
{
	void accept(A a, B b, C c, D d);
}
