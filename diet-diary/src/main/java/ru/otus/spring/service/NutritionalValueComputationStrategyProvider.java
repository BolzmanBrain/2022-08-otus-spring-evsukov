package ru.otus.spring.service;


import ru.otus.spring.domain.NutritionalValueComputationStrategyTypes;
import ru.otus.spring.strategy.NutritionalValueComputationStrategy;

public interface NutritionalValueComputationStrategyProvider {
    NutritionalValueComputationStrategy getStrategy(NutritionalValueComputationStrategyTypes strategyType);
    NutritionalValueComputationStrategy getDefaultStrategy();
}
