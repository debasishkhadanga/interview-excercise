package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class BuyPriceListener implements PriceListener {

  private  String security;
  private  double triggerLevel;
  private  int quantityToPurchase;
  private  ExecutionService executionService;

  private boolean tradeExecuted;
  
  

  public BuyPriceListener(String security, double triggerLevel, int quantityToPurchase, ExecutionService executionService,
		boolean tradeExecuted) {
	super();
	this.security = security;
	this.triggerLevel = triggerLevel;
	this.quantityToPurchase = quantityToPurchase;
	this.executionService = executionService;
	this.tradeExecuted = tradeExecuted;
}
  
  

public String getSecurity() {
	return security;
}



public void setSecurity(String security) {
	this.security = security;
}



public double getTriggerLevel() {
	return triggerLevel;
}



public void setTriggerLevel(double triggerLevel) {
	this.triggerLevel = triggerLevel;
}



public int getQuantityToPurchase() {
	return quantityToPurchase;
}



public void setQuantityToPurchase(int quantityToPurchase) {
	this.quantityToPurchase = quantityToPurchase;
}



public ExecutionService getExecutionService() {
	return executionService;
}



public void setExecutionService(ExecutionService executionService) {
	this.executionService = executionService;
}



public boolean isTradeExecuted() {
	return tradeExecuted;
}



public void setTradeExecuted(boolean tradeExecuted) {
	this.tradeExecuted = tradeExecuted;
}



@Override
  public void priceUpdate(String security, double price) {
    if (canBuy(security, price)) {
      executionService.buy(security, price, quantityToPurchase);
      tradeExecuted = true;
    }
  }

  private boolean canBuy(String security, double price) {
    return (!tradeExecuted) && this.security.equals(security) && (price < this.triggerLevel);
  }
}
