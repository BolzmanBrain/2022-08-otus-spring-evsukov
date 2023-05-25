package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.NutritionalValueComputationStrategyTypes;
import ru.otus.spring.strategy.NutritionalValueComputationStrategy;
import ru.otus.spring.strategy.NutritionalValueComputationStrategyImpl;

@Service
public class NutritionalValueComputationStrategyProviderImpl implements NutritionalValueComputationStrategyProvider{
    @Override
    public NutritionalValueComputationStrategy getStrategy(NutritionalValueComputationStrategyTypes strategyType) {
        return new NutritionalValueComputationStrategyImpl();
    }
    @Override
    public NutritionalValueComputationStrategy getDefaultStrategy() {
        return new NutritionalValueComputationStrategyImpl();
    }
}
