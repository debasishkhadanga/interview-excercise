package com.acme.mytrader.strategy;

import java.util.Arrays;
import java.util.List;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.execution.TradeExecutionService;
import com.acme.mytrader.price.BuyPriceListener;
import com.acme.mytrader.price.PriceSourceImpl;
import com.acme.mytrader.price.PriceSourceRunnable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
@AllArgsConstructor
@Getter
public class TradingStrategy {

  private final ExecutionService tradeExecutionService;
  private final PriceSourceRunnable priceSource;

  
public TradingStrategy(ExecutionService tradeExecutionService, PriceSourceRunnable priceSource) {
	super();
	this.tradeExecutionService = tradeExecutionService;
	this.priceSource = priceSource;
}

public void autoBuy(List<SecurityDTO> request) throws InterruptedException {

    request.stream().map(
        r -> new BuyPriceListener(r.getSecurity(), r.getPriceThreshold(), r.getVolume(),
            tradeExecutionService, false)).forEach(priceSource::addPriceListener);
    Thread thread = new Thread(priceSource);
    thread.start();
    thread.join();
    request.stream().map(
        r -> new BuyPriceListener(r.getSecurity(), r.getPriceThreshold(), r.getVolume(),
            tradeExecutionService, false)).forEach(priceSource::removePriceListener);
  }
}

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
class SecurityDTO {

	private String security;
	private double priceThreshold;
	private int volume;

	public SecurityDTO(String security, double priceThreshold, int volume) {
		super();
		this.security = security;
		this.priceThreshold = priceThreshold;
		this.volume = volume;

	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public double getPriceThreshold() {
		return priceThreshold;
	}

	public void setPriceThreshold(double priceThreshold) {
		this.priceThreshold = priceThreshold;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
}

